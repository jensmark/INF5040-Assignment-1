#include <stdlib.h>
#include <assert.h>
#include <string>
#include <iostream>
#include "quiz_server.h"

int main(int argc, char** argv) {
	
	try{
		//------------------------------------------------------------------------
		// 1) Initialize ORB
		// 2) Get reference to root POA
		// 3) Bind to name service
		// 4) Initialize servant object
		//------------------------------------------------------------------------
		                                                                                                                                                          
		CORBA::ORB_var orb = CORBA::ORB_init(argc, argv);
		                                                                            
	   
		CORBA::Object_var obj = orb->resolve_initial_references("RootPOA");
		PortableServer::POA_var _poa = PortableServer::POA::_narrow(obj.in());
		
		QuizServerImpl* server = new QuizServerImpl();
		
		
		PortableServer::ObjectId_var server_id = _poa->activate_object(server);
		
		CORBA::Object_var SA_obj = server->_this();
		
		CORBA::String_var sior(orb->object_to_string(SA_obj.in()));
		//std::cerr << "'" << (char*)sior << "'" << std::endl;
		
		
		CORBA::Object_var obj1=orb->resolve_initial_references("NameService");
		assert(!CORBA::is_nil(obj1.in()));
		
		CosNaming::NamingContext_var nc = CosNaming::NamingContext::_narrow(obj1.in());
		assert(!CORBA::is_nil(nc.in()));
		
		std::cout << "Binding service 'QuizService'" << std::endl;
		
		CosNaming::Name name;
		name.length(1);
		name[0].id=CORBA::string_dup("QuizService");
		nc->rebind (name,SA_obj.in());
		
		server->_remove_ref();
		
		PortableServer::POAManager_var pmgr = _poa->the_POAManager();
		pmgr->activate();
		
		std::cout << "Server is running..." << std::endl;
		orb->run();
		std::cout << "Server is shutting down.." << std::endl;
		
		orb->destroy();
		
		free(name[0].id);
		
		delete server;
	}
		                                                                            
	catch(CORBA::SystemException&) {
		std::cerr << "Caught CORBA::SystemException." << std::endl;
	}
	catch(CORBA::Exception&) {
		std::cerr << "Caught CORBA::Exception." << std::endl;
	}
	catch(omniORB::fatalException& fe) {
		std::cerr << "Caught omniORB::fatalException:" << std::endl;
		std::cerr << "  file: " << fe.file() << std::endl;
		std::cerr << "  line: " << fe.line() << std::endl;
		std::cerr << "  mesg: " << fe.errmsg() << std::endl;
	}
	catch(...) {
		std::cerr << "Caught unknown exception." << std::endl;
	}
                                                                                
  return 0;
}
