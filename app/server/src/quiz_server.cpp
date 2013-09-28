#include "quiz_server.h"
#include <iostream>
#include <stdlib.h>
QuizServerImpl::QuizServerImpl(){
	srand(static_cast<unsigned int>(time(NULL)));
}
QuizServerImpl::~QuizServerImpl(){
}

CORBA::Long QuizServerImpl::newQuestion(Quiz::CompleteQuestion* question){
	std::cout << question->sentence() << std::endl;
	Quiz::CompleteQuestion* q = new OBV_Quiz::CompleteQuestion();
	q->sentence(question->sentence());
	q->correctAlternatives(question->correctAlternatives());
	q->alternatives(question->alternatives());
	
	std::cout << q->sentence() << std::endl;
	
	_completeQuestions[_completeQuestions.size()] = q;
	q->id(_completeQuestions.size()-1);
	
	return q->id();
}

CORBA::Boolean QuizServerImpl::getRandomQuestion(Quiz::Question_out randomQuestion){
	if(_completeQuestions.empty())
		return false;

	std::cout << "getting a random question" << std::endl;
	int id = _randNumber(0, _completeQuestions.size()-1);
	
	Quiz::CompleteQuestion* q = _completeQuestions[id];
	std::cout << q << std::endl;
	std::cout << q->id() << std::endl;
	std::cout << q->sentence() << std::endl;
	
	randomQuestion = q;
	
	return true;
}

CORBA::Boolean QuizServerImpl::answerQuestion(CORBA::Long questionId,
				const Quiz::QuizServer::alternativesIds& answer, 
				Quiz::QuizServer::alternativesIds_out correct){

	if(_completeQuestions.find(questionId) != _completeQuestions.end()){
		Quiz::CompleteQuestion* q = _completeQuestions[questionId];
		Quiz::CompleteQuestion::CharSeq corrCharSeq = q->correctAlternatives();
		//if(corrCharSeq == q->correctAlternatives()){
			//		CORBA::ValueFactoryBase_var vf = new Quiz::Question_init;
			//		orb->register_value_factory("IDL:Quiz/Question:1.0", vf);
			//
			//		vf = new Quiz::Alternative_init;
			//		orb->register_value_factory("IDL:Quiz/Alternative:1.0f", vf);
			//
			//		vf = new Quiz::CompleteQuestion_init;
			//		orb->register_value_factory("IDL:Quiz/CompleteQuestion:1.0f", vf);
		//}

	}
	else{
		return false;
	}
}

CORBA::Long QuizServerImpl::removeQuestion(CORBA::Long questionId){
	std::map<CORBA::Long, Quiz::CompleteQuestion*>::iterator it;
	it = _completeQuestions.find(questionId);
	if(it == _completeQuestions.end()){
		return -1;
	}
	else{
		std::cout << "Deleting ID: " << questionId << std::endl;
		return questionId;
	}
}

int QuizServerImpl::_randNumber(int low, int high){
	int range = (high-low) + 1; 
	return low + int(range * rand() / (RAND_MAX + 1.0f));
}

