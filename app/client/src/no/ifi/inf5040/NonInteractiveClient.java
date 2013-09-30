package no.ifi.inf5040;

import Quiz.Alternative;
import Quiz.QuestionHolder;
import Quiz.*;
import no.ifi.inf5040.impl.*;
import java.util.Scanner;

public class NonInteractiveClient extends ClientBase{

    private static void GetRandomQuestion(){
        System.out.println("Attempting to get question..");
        try {
            QuestionHolder responseQuestion = new QuestionHolder();
            boolean result = server.getRandomQuestion(responseQuestion);

            PrintQuestion(responseQuestion.value);

        } catch (Exception e){
            System.out.println("Question request Exception: " + e.getCause());
        }
    }

    private static void PrintQuestion(Question question)
    {
        System.out.println("-------------------");
        System.out.println("Server response question: " + question.sentence);

        for(int i = 0; i < question.alternatives.length; i++)
        {
            System.out.println((i + 1)  + ": " + question.alternatives[i].sentence);
        }
        System.out.println("-------------------");
    }

    public static void main(String [] args){

        String port = "6666";
        String ip = "localhost";
        NonInteractiveClient client = new NonInteractiveClient();

        String[] strArr = {"q1", "q2", "q3","q4", "q5", "q6","q7", "q8", "q9", "q10"};
        String[] altArr = {"alternative1", "alternative2", "alternative3" };

        int[] ids = new int[10];

        try {
            System.out.println("Attempting to connect to server..");
            boolean connected = client.connect(port, ip);
            System.out.println((connected == true ? "Connected to server." : "Connection failed."));

            if(server == null)
            {
                System.out.println("Quitting..");
                System.exit(-1);
            }

            System.out.println("Beginning to send Questions..");

            //Send the 10 questions.
            for(int i = 0; i < strArr.length; i++){

                CompleteQuestion send_question = new CompleteQuestionImpl();

                send_question.sentence = "question";//strArr[i];
                send_question.id = i;

                Alternative[] alternatives = new Alternative[1];

                Alternative alt = new AlternativeImpl();
                alt.sentence = "alt1";
                alt.id = 1;

                alternatives[0] = alt;

               /* for(int j = 1; j < alternatives.length; j++){
                    alternatives[j] = new AlternativeImpl();
                    alternatives[j].sentence = ("alt-" + j);
                }*/

                send_question.alternatives = alternatives;

                try{
                    //Send question to server
                    send_question.id = server.newQuestion(send_question);
                } catch (Exception e){

                    //e.printStackTrace();
                    System.out.println("Send question error: " + e.getMessage() + "\n" + e.getStackTrace());
                    e.getStackTrace();
                }
            }

            //System.out.println("All questions sent successfully.");
            GetRandomQuestion();


        } catch (Exception e){
        if(server == null){
            Scanner in = new Scanner(System.in);

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
