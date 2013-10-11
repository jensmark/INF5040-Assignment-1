package no.ifi.inf5040;

import Quiz.Alternative;
import Quiz.QuestionHolder;
import Quiz.*;
import no.ifi.inf5040.impl.*;
import java.util.Scanner;

public class NonInteractiveClient extends ClientBase{

    /**
     * Requests a random question from the server and utilizes the printQuestion to print it out.
     */
    private static void GetRandomQuestion(){
        System.out.println("Attempting to get random question from server..");
        try {
            QuestionHolder responseQuestion = new QuestionHolder();

            //A random question from the server is assigned to the reponseQuestion var
            server.getRandomQuestion(responseQuestion);

            //Prints out the question and its alternatives.
            PrintQuestion(responseQuestion.value);

        } catch (Exception e){
            System.out.println("Question request Exception: " + e.getCause());
        }
    }

    /**
     * Prints out a question and available alternatives.
     * @param question
     *
     */
    private static void PrintQuestion(Question question)
    {
        System.out.println("-------------------");
        System.out.println("Server response question: " + question.sentence);

        //Write out all question alternatives
        for(int i = 0; i < question.alternatives.length; i++)
        {
            System.out.println((i + 1)  + ": " + question.alternatives[i].sentence);
        }
        System.out.println("-------------------");
    }

    /**
     * Function for creating a complete question ready for sending to server.
     *
     * @param qs
     * Question sentence
     * @param alts
     * String array of question answer alternatives.
     * @param ansID
     * int ID of correct alternative.
     * @return
     */
    private static CompleteQuestionImpl populateQuestion(String qs, String[] alts, int ansID){
        CompleteQuestionImpl q = new CompleteQuestionImpl();
        //Set question text
        q.sentence = qs;
        //Set ID of corrent alternative
        q.correctAlternatives = new char[]{(char)ansID};
        q.id = 1;

        //Set size of alternatives to length of alternative array
        Alternative[] alternatives = new Alternative[alts.length];

        //Create alternative instances and assigns all alternatives
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
        boolean running = true;

        while(running){

            NonInteractiveClient client = new NonInteractiveClient(); //Creates a new instance of this class

            CompleteQuestion[] completeQs = new CompleteQuestionImpl[10];

            completeQs[0] = populateQuestion("Which multicast overlay type offers the best dissemination efficiency",
                    new String[]{"Rectangular grid", "Multicast tree", "Regular hypercube"}, 1);

            completeQs[1] = populateQuestion("Which of the following protocols offers order garantee",
                    new String[]{"UDP", "TCP", "pop3"}, 1);

            completeQs[2] = populateQuestion("At most one process can be in a critical section at the same time.",
                    new String[]{"Small world problem", "Mutual exclusion problem ", "Atomic transaction problem"}, 1);

            completeQs[3] = populateQuestion("Which algorithm uses a token that is rotated in a specific direction?",
                    new String[]{"Central server algorithm", "Ring based algorithm", "The bully algorithm"}, 1);

            completeQs[4] = populateQuestion("What protocol does CORBA use to communicate between different programming languages across the internet?",
                    new String[]{"HTTP", "IIOP", "IMAP"}, 1);

            completeQs[5] = populateQuestion("Middleware that allows a program to make Remote procedure calls.",
                    new String[]{"TCP", "ORB", "IDL"}, 1);

            completeQs[6] = populateQuestion("What is the worst case order(Big O) of the Bully algorithm ",
                    new String[]{"n", "n^2", "2^n"}, 1);

            completeQs[7] = populateQuestion("Which algorithm is vulnerable to a central bottleneck?",
                    new String[]{"Ring based algorithm", "Central server algorithm", "Bully algorithm"}, 1);

            completeQs[8] = populateQuestion("Which transport layer protocol has the smallest header size.",
                    new String[]{"TCP", "UDP", "SMB"}, 1);

            completeQs[9] = populateQuestion("The process of transforming an object to a format suited for transmissions is called..",
                    new String[]{"Generalizing", "Marshalling", "Corporaling"}, 1);

            try {
                System.out.println("Attempting to connect to server..");
                boolean connected = client.connect(port, ip);//Connect to server using specified port and Ip adress

                /*if(connected == false) {
                    System.out.println("Could not connect to server.");
                    throw new Exception();
                }*/

                System.out.println("Beginning to send Questions..");
                //Attempt to send questions to server.
                try{
                    for(int i = 0; i < completeQs.length; i++){
                        Thread.sleep(1000); //Pause thread to simulate timed events.
                        System.out.println("Sending question #" + (i + 1));
                        server.newQuestion(completeQs[i]); //Sends a question to the server.
                    }
                } catch(Exception e){
                    System.out.println("Send question error: " + e.getMessage());
                }

                GetRandomQuestion(); //Requests a random question from the server.

                running = false;

            } catch (Exception e){
                if(server == null){ //Problem with connecting to server
                    System.out.println("Failed to connect to server.");
                    Scanner in = new Scanner(System.in);

                    while(server == null){  //Allows user to attempt to connect again.
                        System.out.println("Attempt to connect again?[y/n]");
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
                    System.out.println("\nunknown error(E):\n" + e.getCause());
                    System.exit(-1);
                }
            }
        }

        System.exit(0);
    }
}
