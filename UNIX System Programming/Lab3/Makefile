CC = g++ #Compiler
CFLAGS = -I 
CFLAGS += -Wall -g #Debugging options
FILE = etherCtrl.cpp #Only target file for this program

lab3: $(FILE) #Executable to be named lab3 thanks to $@
	$(CC) $(CFLAGS) $^ -o $@

clean: #Removes lab3 executable on clean
	rm -f lab3

all: lab3 #Executes lab3 rule when all is called