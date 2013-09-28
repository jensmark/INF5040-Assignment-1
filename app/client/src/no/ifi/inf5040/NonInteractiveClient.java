package no.ifi.inf5040;

import Quiz.Alternative;
import Quiz.QuestionHolder;
import Quiz.QuizServer;
import no.ifi.inf5040.impl.*;
import java.util.Scanner;

public class NonInteractiveClient extends ClientBase{

    public final String q1 = new String("Quetion 1");

    private static void GetRandomQuestion(){
        try {
            QuestionHolder responseQuestion = new QuestionHolder();
            boolean result = server.getRandomQuestion(responseQuestion);

            if(responseQuestion != null){
                System.out.println("Server response question: " + responseQuestion.value.sentence);
            } else {System.out.println("Failed to get question from server");}

        } catch (Exception e){
            System.out.println("Get question Exception: " + e.getCause());
        }
    }

    public static void main(String [] args){

        String port = "6666";
        String ip = "localhost";
        NonInteractiveClient client = new NonInteractiveClient();
        //QuizServer server = client.connect("1024", "localhost");

        Scanner in = new Scanner(System.in);

        String[] strArr = {"q1", "q2", "q3","q4", "q5", "q6","q7", "q8", "q9", "q10"};
        int[] ids = new int[10];

        //InteractiveClient client = new InteractiveClient();
        //client.initGUI();
        try {

            boolean connected = client.connect(port, ip);

            for(int i = 0; i < strArr.length; i++){
                CompleteQuestionImpl q = new CompleteQuestionImpl();
                q.sentence = "question: " + i;

                AlternativeImpl qal = new AlternativeImpl();
                qal.sentence = "alt1";
                qal.id = 0;

                AlternativeImpl qal2 = new AlternativeImpl();
                qal2.sentence = "alt2";
                qal2.id = 1;

                q.alternatives = new Alternative[2];

                q.alternatives[0] = qal;
                q.alternatives[1] = qal2;

                q.correctAlternatives = qal2.sentence.toCharArray();

               // q.id =
                //ids[i] = server.newQuestion(q);
                System.out.print(ids[i]);
            }

            GetRandomQuestion();


        } catch (Exception e){
        if(server == null){

            while(server == null){
                System.out.println("Failed to connect to server. Try again? [y/n]");
                String option = in.next().toLowerCase();

                if(option.contains("y")) {
                    try{
                        client.connect(port, ip);
                    }catch (Exception ee){
                        System.out.println("unknown error (EE):\n" + ee.fillInStackTrace());
                    }
                }
                else if(option.contains("n")){
                    System.out.println("Exiting..");
                    System.exit(-1);
                }
                else{
                    System.out.println("Invalid input");
                }
            }

        } else {

            System.out.println("\nunknown error(E):\n" + e.getCause() + "\nserver:" + (server == null ? "null" : "!null"));
            System.exit(-1);
        }
    }
    }
}
