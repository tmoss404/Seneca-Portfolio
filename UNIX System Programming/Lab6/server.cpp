#include <iostream>
#include <stdio.h>
#include <unistd.h>
#include <sys/socket.h>
#include <sys/un.h>
#include <stdlib.h>

#define SOCK_PATH "/tmp/lab6"
#define BUF_SIZE 100
#define BACKLOG 5

using namespace std;

int main(int argc, char *argv[]) {

	struct sockaddr_un addr;
	int sv_fd, cl_fd, len, ret;
	char buf[BUF_SIZE];

	//Zero out the entire suckaddr_un struct with memset
	memset(&addr, 0, sizeof(addr));

	//cout << "server: socket()" << endl;
	//Create the socket
	if( (sv_fd = socket(AF_UNIX, SOCK_STREAM, 0)) == -1) {
		cout << "server: " << strerror(errno) << endl;
        exit(-1);
	}

	//Set as UNIX domain address
	addr.sun_family = AF_UNIX;
	//Copy our socket pathname into the sun_path field, leaving a guaranteed null-terminating byte at the end
	strncpy(addr.sun_path, SOCK_PATH, sizeof(addr.sun_path) - 1);
	//Unlink system call using our socket path to ensure it is free to use before bind() is called
	unlink(SOCK_PATH);

	//cout << "server: bind()" << endl;
	//Bind the socket to our local socket file
	if(bind(sv_fd, (struct sockaddr *)&addr, sizeof(addr)) == -1) {
        cout << "server: " << strerror(errno) << endl;
        close(sv_fd);
        exit(-1);
    }

	//cout << "server: listen()" << endl;
    //Listen for a client
    if (listen(sv_fd, 5) == -1) {
        cout << "server: " << strerror(errno) << endl;
        unlink(SOCK_PATH);
        close(sv_fd);
        exit(-1);
    }

	cout << "Waiting for client..." << endl;
    //Accept the client's connection
    if ( (cl_fd = accept(sv_fd, NULL, NULL)) == -1) {
        cout << "server: " << strerror(errno) << endl;
        unlink(SOCK_PATH);
        close(sv_fd);
        exit(-1);
    }
	cout << "Client connected to the server" << endl;
	cout << "server: accept()" << endl;

	//Server sends 'Pid' command to client
	cout<<"The server requests the client's pid"<<endl;
	len = sprintf(buf, "Pid")+1;
    ret = write(cl_fd, buf, len);
    if(ret==-1) {
        cout<<"server: Write Error"<<endl;
        cout<<strerror(errno)<<endl;
    }
	//Server reads response from client, containing pid message
	ret = read(cl_fd, buf, BUF_SIZE);
	if(ret==-1) {
        cout<<"server: Read Error"<<endl;
        cout<<strerror(errno)<<endl;
    }
	cout<<"server: "<<buf<<endl;

	//Server sends 'Sleep' command to client
	cout<<"The server requests the client to sleep"<<endl;
	len = sprintf(buf, "Sleep")+1;
    ret = write(cl_fd, buf, len);
    if(ret==-1) {
        cout<<"server: Write Error"<<endl;
        cout<<strerror(errno)<<endl;
    }
	//Server reads response from client - 'Done'
	ret = read(cl_fd, buf, BUF_SIZE);
	if(ret==-1) {
        cout<<"server: Read Error"<<endl;
        cout<<strerror(errno)<<endl;
    }

	//Server sends 'Quit' command to the client
	cout<<"The server requests the client to quit"<<endl;
	len = sprintf(buf, "Quit")+1;
    ret = write(cl_fd, buf, len);
    if(ret==-1) {
        cout<<"server: Write Error"<<endl;
        cout<<strerror(errno)<<endl;
    }

	//Unlink the socket file, close file descriptors for the server and peer sockets
	unlink(SOCK_PATH);
	close(sv_fd);
	close(cl_fd);
	//Exit
	return 0;
}
