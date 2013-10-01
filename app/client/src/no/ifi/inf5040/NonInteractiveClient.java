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

    private static CompleteQuestionImpl populateQuestion(String qs, String[] alts, int ansID){
        CompleteQuestionImpl q = new CompleteQuestionImpl();
        q.sentence = qs;
        q.correctAlternatives = new char[]{(char)ansID};
        q.id = 1;

        Alternative[] alternatives = new Alternative[alts.length];

        for(int i = 0; i < alts.length; i++){
            Alternative alt = new AlternativeImpl();

            alt.sentence = alts[i];
            alt.id = (char)i;

            alternatives[i] = alt;
        }
        q.alternatives = alternatives;

        return q;
    }

    public static void main(String [] args){

        String port = "6666";
        String ip = "localhost";
        NonInteractiveClient client = new NonInteractiveClient();

        CompleteQuestion[] completeQs = new CompleteQuestionImpl[10];

        completeQs[0] = populateQuestion("q1", new String[]{"1", "2", "3"}, 1);
        completeQs[1] = populateQuestion("q2", new String[]{"1", "2", "3"}, 1);
        completeQs[2] = populateQuestion("q3", new String[]{"1", "2", "3"}, 1);
        completeQs[3] = populateQuestion("q4", new String[]{"1", "2", "3"}, 1);
        completeQs[4] = populateQuestion("q5", new String[]{"1", "2", "3"}, 1);
        completeQs[5] = populateQuestion("q6", new String[]{"1", "2", "3"}, 1);
        completeQs[6] = populateQuestion("q7", new String[]{"1", "2", "3"}, 1);
        completeQs[7] = populateQuestion("q8", new String[]{"1", "2", "3"}, 1);
        completeQs[8] = populateQuestion("q9", new String[]{"1", "2", "3"}, 1);
        completeQs[9] = populateQuestion("q10", new String[]{"1", "2", "3"}, 1);

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

            try{
                //Send the questions.
                for(int i = 0; i < completeQs.length; i++){
                    System.out.println("Sending question #" + (i + 1));
                    server.newQuestion(completeQs[i]);
                }
            } catch(Exception e){
                System.out.println("Send question error: " + e.getMessage() + "\n" + e.getStackTrace());
            }

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
