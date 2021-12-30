#include <stdio.h>
#include <iostream>
#include <sys/types.h>
#include <sys/wait.h>
#include <stdlib.h>
#include <unistd.h>
#include <error.h>
#include <string.h>

using namespace std;

int main (int argc, char *argv[]){

	int pfd[2];
    pid_t pid1, pid2;

	const int LEN=32;
	char argument1[LEN];
	char argument2[LEN];

	strcpy(argument1, argv[1]);
	strcpy(argument2, argv[2]);

	char arg1[3][LEN];
	char arg2[3][LEN];

	int len1=0,len2=0;

	//Split the arguments from the command line
	//with a whitespace delimiter
	//track how many individual args there are
	char *token=strtok(argument1, " ");
	while(token!=NULL){
		strcpy(arg1[len1], token);
		token=strtok(NULL, " ");
		++len1;
	}
	token=strtok(argument2, " ");
	while(token!=NULL){
		strcpy(arg2[len2], token);
		token=strtok(NULL, " ");
		++len2;
	}

	//Create a pipe
    if (pipe (pfd) == -1)
       perror ("pipe");

	//Create a child
    pid1 = fork();
    if (pid1 == 0) {//If child
		//Bind standard output to the write end of the pipe
        if (dup2 (pfd[1], STDOUT_FILENO) == -1)
            perror ("dup2");
		//Close the fds now
		for(int i=0; i<2; i++){
			if (close (pfd[i]) == -1)
				perror ("close");
		}
		//Switch based on how many arguments were provided
		//from the command line.
		//I really don't know if this is a good solution for
		//this, but this was the only way I could see dynamically
		//calling an exec with the proper amount of args.
		//Maybe I could find a way to set the missing arguments
		//on a call with only one or two to "" to avoid errors,
		//but I feel like the solution to that would waste resources
		//compared to this.
		switch(len1){
			case 1:
				execlp (arg1[0], arg1[0], (char *) NULL);
					perror ("execlp");
					break;
			case 2:
				execlp (arg1[0], arg1[0], arg1[1], (char *) NULL);
					perror ("execlp");
					break;
			case 3:
				execlp (arg1[0], arg1[0], arg1[1], arg1[2], (char *) NULL);
					perror ("execlp");
					break;
		};
    }

	//Create child
    pid2 = fork();
    if (pid2 == 0) {//If child
		//Bind standard input to the read end of the pipe
        if (dup2 (pfd[0], STDIN_FILENO) == -1)
            perror ("dup2");
		//Close fds now
		for(int i=0; i<2; i++){
			if (close (pfd[i]) == -1)
				perror ("close");
		}
		//Switch based on how many arguments were provided
		//from the command line.
		switch(len2){
			case 1:
				execlp (arg2[0], arg2[0], (char *) NULL);
					perror ("execlp");
					break;
			case 2:
				execlp (arg2[0], arg2[0], arg2[1], (char *) NULL);
					perror ("execlp");
					break;
			case 3:
				execlp (arg2[0], arg2[0], arg2[1], arg2[2], (char *) NULL);
					perror ("execlp");
					break;
		}
    }

	//Close fds
    for(int i=0; i<2; i++){
		if (close (pfd[i]) == -1)
	        perror ("close");
	}


    if (waitpid (pid1, NULL, 0) == -1)
         perror ("waitpid");
    if (waitpid (pid2, NULL, 0) == -1)
         perror ("waitpid");

	return 0;
}
