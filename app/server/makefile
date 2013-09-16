#------------------------------------------------------------------------------

SOURCE=src/main.cpp
MYPROGRAM=server
MYINCLUDES=/src/

CC=g++

#------------------------------------------------------------------------------



all: $(MYPROGRAM)



$(MYPROGRAM): $(SOURCE)

	$(CC) -I$(MYINCLUDES) $(SOURCE) -o$(MYPROGRAM)

run:
	
	./$(MYPROGRAM)

clean:

	rm -f $(MYPROGRAM)


