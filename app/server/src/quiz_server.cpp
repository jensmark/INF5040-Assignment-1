#include "quiz_server.h"
#include <iostream>
#include <stdlib.h>

int QuizServerImpl::newQuestionID = 0;

QuizServerImpl::QuizServerImpl(){
	srand(static_cast<unsigned int>(time(NULL)));
}
QuizServerImpl::~QuizServerImpl(){
}

CORBA::Long QuizServerImpl::newQuestion(Quiz::CompleteQuestion* question){
	std::cout << "adding new question: " << question->sentence() << std::endl;
	std::cout << "correct answer: " << question->alternatives()[0]->sentence() << std::endl;
	for(std::size_t i = 1; i < question->alternatives().length(); i++){
		std::cout << "alternative "<< i << ": " << question->alternatives()[i]->sentence() << std::endl;
	}

	Quiz::CompleteQuestion* q = new OBV_Quiz::CompleteQuestion();
	q->sentence(question->sentence());
	q->correctAlternatives(question->correctAlternatives());
	q->alternatives(question->alternatives());
	
	std::cout << q->sentence() << std::endl;
	newQuestionID++;

	_completeQuestions[newQuestionID] = q;
	q->id(newQuestionID);
	
	return q->id();
}

CORBA::Boolean QuizServerImpl::getRandomQuestion(Quiz::Question_out randomQuestion){
	if(_completeQuestions.empty())
		return false;
	int id = _randNumber(0, _completeQuestions.size());

	std::map<CORBA::Long, Quiz::CompleteQuestion*>::iterator iter;
	iter = _completeQuestions.begin();
	for(int i = 0; i < id; i++){
		iter++;
	}
	Quiz::CompleteQuestion* q = iter->second;

	Quiz::Question* qc = new OBV_Quiz::Question();
	qc->sentence(q->sentence());
	qc->alternatives(q->alternatives());
	qc->id(q->id());

	randomQuestion = qc;
	
	return true;
}

CORBA::Boolean QuizServerImpl::answerQuestion(CORBA::Long questionId,
				const Quiz::QuizServer::alternativesIds& answer, 
				Quiz::QuizServer::alternativesIds_out correct){

	if(_completeQuestions.find(questionId) != _completeQuestions.end()){
		Quiz::CompleteQuestion* q = _completeQuestions[questionId];
		Quiz::CompleteQuestion::CharSeq corrCharSeq = q->correctAlternatives();
		if(corrCharSeq.length() == answer.length()){
			for(CORBA::ULong i = 0; i < answer.length(); i++){
				if(corrCharSeq[i] != answer[i]){
					return false;
				}
			}
		}
		//correct.alternativesIds_out(corrCharSeq);

		//corrCharSeq[0]
		//std::cout << corrCharSeq[0];
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
	return (int)((double(rand()) / double(RAND_MAX)) * (high - low)) + low;
}

