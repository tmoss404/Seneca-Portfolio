CC = g++ #Compiler
CFLAGS = -I
CFLAGS += -Wall -g #Debugging options
FILE = lab2.cpp #Only target file for this program

lab2: $(FILE) #Executable to be named lab2 thanks to $@
	$(CC) $(CFLAGS) $^ -o $@

clean: #Removes lab2 executable on clean
	rm -f lab2

all: lab2 #Executes lab2 rule when all is called
