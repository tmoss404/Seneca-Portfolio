#include <iostream>
#include <stdio.h>
#include <unistd.h>
#include <sys/socket.h>
#include <sys/un.h>
#include <stdlib.h>

#define SOCK_PATH "/tmp/lab6"
#define BUF_SIZE 100

using namespace std;

int main(int argc, char *argv[]) {

	struct sockaddr_un addr;
	int fd, len, ret;
	char buf[BUF_SIZE];

	//cout << "client: socket()" << endl;
	//Create the socket
	if( (fd = socket(AF_UNIX, SOCK_STREAM, 0)) == -1) {
		cout << "client: " << strerror(errno) << endl;
        exit(-1);
	}

	//Set as UNIX domain address
	addr.sun_family = AF_UNIX;
	//Copy our socket pathname into the sun_path field, leaving a guaranteed null-terminating byte at the end
	strncpy(addr.sun_path, SOCK_PATH, sizeof(addr.sun_path) - 1);

	//cout << "client: connect()" << endl;
    //Connect to the local socket
    if (connect(fd, (struct sockaddr*)&addr, sizeof(addr)) == -1) {
        cout << "client: " << strerror(errno) << endl;
        close(fd);
        exit(-1);
    }

	while(true){
		//Read something from the server
		ret = read(fd,buf,BUF_SIZE);
        if(ret<0) {
            cout<<"client("<<getpid()<<"): error reading the socket"<<endl;
            cout<<strerror(errno)<<endl;
        }

		//Server requests the pid of the client
		if(strcmp(buf, "Pid")==0) {
			cout<<"A request for the client's pid has been received"<<endl;
            len = sprintf(buf, "This client has pid %d", getpid())+1;
            ret = write(fd, buf, len);//The client writes the pid message to the server
            if(ret==-1) {
               cout<<"client("<<getpid()<<"): Write Error"<<endl;
               cout<<strerror(errno)<<endl;
            }
        } else if(strcmp(buf, "Sleep")==0) {//Server requests the client to sleep
            cout<<"This client is going to sleep for 5 seconds"<<endl;
			sleep(5);
			len = sprintf(buf, "Done")+1;
            ret = write(fd, buf, len);//The client writes 'Done' to the server
        } else if(strcmp(buf, "Quit")==0) {//Server requests the client to terminate
            cout<<"This client is quitting"<<endl;
            break;
        }
	}

	//Close socket file descriptor
	close(fd);
	//Exit
	return 0;
}
