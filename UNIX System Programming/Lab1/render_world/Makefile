CC = g++ #compiler variable
CFLAGS = -I./Book #path to headers
CFLAGS += -Wall -g #debugging options
FILES = $(wildcard *.cpp) #wildcard instead of shell ls to find all .cpp files
LIBS = -lsfml-graphics -lsfml-window -lsfml-system #libraries required for the specifically sfml program I am trying to compile

render-world: $(FILES) #compiles the cpp code to an executable, to be named 'render-world' based on the name of the rule, thanks to '$@'
	$(CC) $(CFLAGS) $^ -o $@ $(LIBS)

clean: #cleans out (removes) any files created by the makefile, in this case only the executable 'render-world'
	rm -f render-world

all: render-world #rule to compile all targets, only target being 'render-world'