package no.ifi.inf5040;

import Quiz.QuizServer;
import Quiz.QuizServerHelper;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

public class ClientBase {

    private static QuizServer server = null;

    public QuizServer getServer(){
        return server;
    }

    protected QuizServer connect(String port, String address) {

        // Server connection has already been established
        if(server != null){
            return server;
        }

        String[] args = {
                "-ORBInitialPort", port,
                "-ORBInitialHost", address};

        try{
            ORB orb = ORB.init(args, null);

            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt nameContext = NamingContextExtHelper.narrow(objRef);

            String name = "Server";
            server = QuizServerHelper.narrow(nameContext.resolve_str(name));

        }catch(Exception e){
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            server = null;

        }finally {
            return server;
        }
    }
}
