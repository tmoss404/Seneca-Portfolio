#include <errno.h>
#include <iostream>
#include <queue>
#include <signal.h>
#include <string.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <unistd.h>
#include "client.h"

using namespace std;

key_t key;
int msgid;
bool is_running;
queue<Message> message;

void *recv_func(void *arg);

// mutex for receive thread and main
pthread_mutex_t lock_x;

static void shutdownHandler(int sig)
{
    switch(sig) {
        case SIGINT:
            is_running=false;
            break;
    }
}

int main()
{
	int ret;
    pthread_t tid;

    struct sigaction action;

	//Register the shutdownHandler to handle sigint
    action.sa_handler = shutdownHandler;
    sigemptyset(&action.sa_mask);
    action.sa_flags = 0;
    sigaction(SIGINT, &action, NULL);

    // ftok to generate unique key
    key = ftok(pathname, 65);
	cout << "server generates this key: " << key << endl;
	//Get message queue with the generated key
	msgid = msgget(key, 0666 | IPC_CREAT);

    pthread_mutex_init(&lock_x, NULL);
    is_running=true;
    ret = pthread_create(&tid, NULL, recv_func, NULL);
    if(ret!=0) {
        is_running = false;
        cout<<strerror(errno)<<endl;
        return -1;
    }

	while(is_running) {
        while(message.size()>0) {
			pthread_mutex_lock(&lock_x);
				Message recvMsg=message.front();
				message.pop();
			pthread_mutex_unlock(&lock_x);
			//Set the mtype to the specified destination, send the message out
			recvMsg.mtype = recvMsg.msgBuf.dest;
			msgsnd(msgid, &recvMsg, sizeof(recvMsg), 0);
		}
		sleep(1);
	}

	//Create and initialize a quit message to send to each client
	Message quitMsg;
	quitMsg.msgBuf.source = 4;
	sprintf(quitMsg.msgBuf.buf, "Quit");
	cout<<"Server Quitting..."<<endl;
	//Loop for 3 clients, sending quit message to each
	for(int i = 1; i<=3; i++){
		//Set the mtype for the client number in the loop
		quitMsg.mtype=i;
		//Set the destination for the client number in the loop
		quitMsg.msgBuf.dest = i;
		cout<<"Server telling client "<<i<<" to quit..."<<endl;
		msgsnd(msgid, &quitMsg, sizeof(quitMsg), 0);
	}

	pthread_join(tid, NULL);
}

void *recv_func(void *arg){
	while(is_running) {
        Message msg;
		//extract messages of mtype 4 for the server
        msgrcv(msgid, &msg, sizeof(msg), 4, 0);
		pthread_mutex_lock(&lock_x);
			message.push(msg);
		pthread_mutex_unlock(&lock_x);
    }
    pthread_exit(NULL);
}
