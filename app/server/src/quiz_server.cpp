	#include "quiz_server.h"
	
	QuizServerImpl::QuizServerImpl(){
	}
	QuizServerImpl::~QuizServerImpl(){
	}
   
	CORBA::Long QuizServerImpl::newQuestion(Quiz::CompleteQuestion* question){
		return 0;
	}
    CORBA::Boolean QuizServerImpl::getRandomQuestion(Quiz::Question_out randomQuestion){
    	return true;
	}
    CORBA::Boolean QuizServerImpl::answerQuestion(CORBA::Long questionId,
    				const Quiz::QuizServer::alternativesIds& answer, 
    				Quiz::QuizServer::alternativesIds_out correct){
    	return true;
	}
    CORBA::Long QuizServerImpl::removeQuestion(CORBA::Long questionId){
    	return 0;
	}
