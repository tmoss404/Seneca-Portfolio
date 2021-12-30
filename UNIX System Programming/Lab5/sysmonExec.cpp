#include <fcntl.h>
#include <fstream>
#include <cstring>
#include <iostream>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>

using namespace std;
const int NUM=2;

int systemMonitor();
bool isRunning=true;
bool isParent = true;//Distinguishes between the parent
                     //process and the child process(es)
pid_t childPid[NUM];

//I used this interface because what I believe to be my eth interface
//was only returning 0, and that was very boring
char *intf[]={"lo", "enp3s0"};

int main()
{
    cout << endl << "parent:main: pid:"<<getpid()<<endl;
    for(int i=0; i<NUM & isParent; ++i) {
        childPid[i] = fork();
        if(childPid[i]==0) {//the child
            cout << "child:main: pid:"<<getpid()<<endl;
            isParent=false;
            execlp("./intfMonitor", "./intfMonitor", intf[i], NULL);
            cout << "child:main: pid:"<<getpid()<<" I should not get here!"<<endl;
	        cout<<strerror(errno)<<endl;
        }
    }
    if(isParent) {
        sleep(10);
        systemMonitor();
    }

    cout << "parent:main("<<getpid()<<"): Finished!" << endl;

    return 0;
}

int systemMonitor()//run by the parent process
{
    int status=-1;
    pid_t pid=0;
    //TODO: Send start signals to the children (SIGUSR1)
	for(int i=0; i<NUM & isParent; ++i) {
		if (!(kill ( childPid[i], SIGUSR1)))
	       	cout<<"SIGUSR1 sent to "<<childPid[i]<<endl;
	   	else if (errno == EPERM)
	       	cout<<"Operation not permitted."<<endl;
	   	else
	       	cout<<childPid[i]<<" doesn't exist"<<endl;
    }
    //TODO: sleep for 30 seconds
	sleep(30);
    //TODO: Send stop signals to the children (SIGUSR2)
	for(int i=0; i<NUM; ++i) {
		if (!(kill ( childPid[i], SIGUSR2)))
	       	cout<<"SIGUSR2 sent to "<<childPid[i]<<endl;
	   	else if (errno == EPERM)
	       	cout<<"Operation not permitted."<<endl;
	   	else
	       	cout<<childPid[i]<<" doesn't exist"<<endl;
    }

    //Wait for children to terminate
    while(pid>=0) {
        pid=wait(&status);//blocking. waitpid() is non-blocking and
                          //waits for a specific pid to terminate
        cout << "parent:systemMonitor: status:"<<status<<". The child pid:"<<pid<<" has finished"<< endl;
    }

    return 0;
}
