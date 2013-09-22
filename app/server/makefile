#------------------------------------------------------------------------------
CC            = g++
CPPFLAGS      = -g -c
LDFLAGS       = -g
SOURCE		  = src
OMNI_HOME     = /usr
OMNI_INCLUDES = -I$(OMNI_HOME)/include
OMNI_LIB_DIR  = $(OMNI_HOME)/lib
INCLUDES      = $(OMNI_INCLUDES)
LIBS          = -lomniORB4 -lomnithread -lomniDynamic4
OBJECTS       = quizSK.o quiz_server.o main.o

#------------------------------------------------------------------------------
                                                                                
all Server: $(OBJECTS)
	$(CC) $(LDFLAGS) -o Server -L$(OMNI_HOME)/lib $(OBJECTS) $(LIBPATH) $(LIBS)
                                                                                
quizSK.o: $(SOURCE)/quizSK.cc $(SOURCE)/quiz.hh
	$(CC) $(CPPFLAGS) $(INCLUDES) $(SOURCE)/quizSK.cc
                                                                                
main.o: $(SOURCE)/main.cpp $(SOURCE)/quiz.hh
	$(CC) $(CPPFLAGS) $(INCLUDES) $(SOURCE)/main.cpp
                                                                                
quiz_server.o: $(SOURCE)/quiz_server.cpp $(SOURCE)/quiz_server.h $(SOURCE)/quiz.hh
	$(CC) $(CPPFLAGS) $(INCLUDES) $(SOURCE)/quiz_server.cpp
                                                                                
$(SOURCE)/quizSK.cc: $(SOURCE)/quiz.idl
	omniidl -bcxx $(SOURCE)/quiz.idl
	mv quiz.hh $(SOURCE)
	mv quizSK.cc $(SOURCE)
	
run:
	./Server	
                                                                                
clean clean_all:
	rm -fr *.o
	rm -fr core
	rm -fr $(SOURCE)/*.hh
	rm -fr $(SOURCE)/*SK.cc
	rm -fr Server


