package no.ifi.inf5040;

import Quiz.QuizServer;
import Quiz.QuizServerHelper;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

public class ClientBase {

    protected static QuizServer server = null;

    public QuizServer getServer(){
        return server;
    }

    protected boolean connect(String port, String address) throws Exception{

        // Server connection has already been established
        if(server != null){
            return true;
        }

        /*String[] args = {
                "-ORBInitialPort", port,
                "-ORBInitialHost", address
        }; */
        String[] args = {
                "-ORBInitRef", String.format("NameService=corbaloc:iiop:%s:%s",  address, port), "/NameService"
        };

        try{
            ORB orb = ORB.init(args, null);
            //ORB orb = ORB.init();

            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt nameContext = NamingContextExtHelper.narrow(objRef);

            String name = "QuizService";
            server = QuizServerHelper.narrow(nameContext.resolve_str(name));
            return true;

        }catch(Exception e){
            System.out.println("Clientbase_ERROR: " + e.getMessage());
            e.printStackTrace();
            server = null;
            throw new Exception();

        }
    }
}
