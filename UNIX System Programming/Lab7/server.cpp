#include <arpa/inet.h>
#include <iostream>
#include <queue>
#include <net/if.h>
#include <netinet/in.h>
#include <signal.h>
#include <string.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <sys/un.h>
#include <unistd.h>
#include <fcntl.h>

using namespace std;

const char IP_ADDR[]="127.0.0.1";//Address of the server
const int BUF_LEN=4096;
const int MAX_CLIENTS=3;
bool is_running;
queue<string> message;
pthread_t tid[MAX_CLIENTS];
pthread_mutex_t lock_x;

void *recv_func(void *arg);

static void sigHandler(int sig){
	switch(sig) {
		case SIGINT:
			is_running=false;
			break;
	}
}

int main(int argc, char *argv[])
{
    int master_fd, cl[MAX_CLIENTS], cl_count = 0, ret, len;
    struct sockaddr_in servaddr;
    socklen_t addrlen = sizeof(servaddr);
    char buf[BUF_LEN];

	//Set up the signal handler to terminate the program properly
    struct sigaction action;
    action.sa_handler = sigHandler;
    sigemptyset(&action.sa_mask);
    action.sa_flags = 0;
    sigaction(SIGINT, &action, NULL);

	//Check for the proper number of args, print proper usage info
	//if incorrect
	if(argc!=2) {
        cout<<"usage: server <port number>"<<endl;
		return -1;
    }
	if( (master_fd = socket(AF_INET, SOCK_STREAM | O_NONBLOCK, 0)) < 0) {
        cout << "server: " << strerror(errno) << endl;
        exit(-1);
    }

    memset(&servaddr, 0, sizeof(servaddr));
    servaddr.sin_family = AF_INET;

	//Convert IP_ADDR to binary and store in servaddr.sin_addr struct
    ret = inet_pton(AF_INET, IP_ADDR, &servaddr.sin_addr);
    if(ret==0) {
        cout<<"No such address"<<endl;
		cout<<strerror(errno)<<endl;
        close(master_fd);
        return -1;
    }

	//Convert the port number from host byte order to network byte order, store in servaddr.sin_port
    servaddr.sin_port = htons(atoi(argv[1]));

	ret = bind(master_fd, (struct sockaddr *)&servaddr, sizeof(servaddr));
    if(ret<0) {
        cout<<"Cannot bind the socket to the local address"<<endl;
		cout<<strerror(errno)<<endl;
		close(master_fd);
		return -1;
    }

	//Listen for clients
	cout << "server: listening..." << endl;
	if (listen(master_fd, MAX_CLIENTS) == -1) {
        cout << "server: " << strerror(errno) << endl;
        close(master_fd);
        exit(-1);
    }

	is_running = true;
	while(is_running){
		//Check if we can accept more clients
		if(cl_count < MAX_CLIENTS){
			//Nonblocking accept, accept client if one is trying to connect
			if( (cl[cl_count] = accept(master_fd, NULL, NULL)) == -1) {
				if(errno != EAGAIN)
		        	cout << "server: " << strerror(errno) << endl;
		    }else{
				//Creat a receive thread for the accepted client, increment the number of clients accepted
				pthread_create(&tid[cl_count], NULL, recv_func, &cl[cl_count]);
				++cl_count;
			}
		}
		while(message.size()>0) {
			//If there is a message in the queue, lock the mutex, pop the message out and print to screen
            pthread_mutex_lock(&lock_x);
            string msg=message.front();
	    	message.pop();
            pthread_mutex_unlock(&lock_x);
            cout<<msg<<endl;
		}
		sleep(1);
	}
	//Loop through for each connected client, sending "Quit", closing the connections, and joining the threads
	for(int i = 0;i<cl_count;i++){
		len=sprintf(buf,"Quit")+1;
		if(write(cl[i],buf,len)<0)
			cout<<"Error writing to: "<<cl[i]<<endl;
		close(cl[i]);
		pthread_join(tid[i], NULL);
	}
	//Close server socket
	close(master_fd);
}

void *recv_func(void *arg)
{
	//Save client fd from thread call
    int cl = *(int *)arg;
    char buf[BUF_LEN];

	//Set up a timeval for the 5s timeout
	struct timeval tv;
    tv.tv_sec = 5;
    tv.tv_usec = 0;

	//Set the timeout on the client socket
	setsockopt(cl, SOL_SOCKET, SO_RCVTIMEO, &tv, sizeof(tv));

	//Read with 5s timeout, if something is read, lock the mutex and push it into the queue
    while(is_running) {
		memset(buf,0,BUF_LEN);
        int rc=read(cl,buf,sizeof(buf));
		if(rc>0){
			pthread_mutex_lock(&lock_x);
	        message.push(buf);
	        pthread_mutex_unlock(&lock_x);
		}
    }
    pthread_exit(NULL);

}
