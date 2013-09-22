/**
 * Header lol
**/

#ifndef QuizServerImpl_h
#define QuizServerImpl_h

#include "quiz.hh"
                                                                                
class QuizServerImpl :  public POA_Quiz::QuizServer,
                     public PortableServer::RefCountServantBase
{
public:
	QuizServerImpl();
	virtual ~QuizServerImpl();
   
	virtual CORBA::Long newQuestion(Quiz::CompleteQuestion* question);
    virtual CORBA::Boolean getRandomQuestion(Quiz::Question_out randomQuestion);
    virtual CORBA::Boolean answerQuestion(CORBA::Long questionId,
    				const Quiz::QuizServer::alternativesIds& answer, 
    				Quiz::QuizServer::alternativesIds_out correct);
    virtual CORBA::Long removeQuestion(CORBA::Long questionId);
};

#endif
