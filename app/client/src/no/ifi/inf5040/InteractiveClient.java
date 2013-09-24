package no.ifi.inf5040;

import Quiz.*;
import no.ifi.inf5040.gui.InteractiveFormGUI;
import no.ifi.inf5040.gui.NewAnswerDialogGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InteractiveClient extends ClientBase{

    private InteractiveFormGUI gui;
    private JFrame frame;
    private QuestionHolder currentQuestion;

    public InteractiveClient()  {
        gui = new InteractiveFormGUI();
    }

    public void initGUI(){
        gui.getNewQuestionButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                onNewQuestionButton(event);
            }
        });
        gui.getDelQuestionButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                onDelQuestionButton(event);
            }
        });
        gui.getAnswerQuestionButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                onAnswerQuestionButton(event);
            }
        });
        gui.getGetQuestionButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                onGetRandomQuestionButton(event);
            }
        });

        frame = new JFrame("Awesome quiz!");
        frame.setPreferredSize(new Dimension(800, 600));
        frame.setContentPane(gui.getMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void updateCurrentQuestion(QuestionHolder question){
        if(question == null){
            gui.getQuestionLabel().setText("No Question...");
            gui.getAlternativeList().setListData(new String[] {"No alternatives..."});

            return;
        }

        gui.getQuestionLabel().setText(question.value.sentence);
        gui.getAlternativeList().setListData(question.value.alternatives);

        currentQuestion = question;
    }

    public JFrame getFrame() {
        return frame;
    }

    private void onNewQuestionButton(ActionEvent event){
        final NewAnswerDialogGUI dialog = new NewAnswerDialogGUI(frame);
        dialog.setLambdaOK(new Runnable() {
            @Override
            public void run() {
                String question = dialog.getQuestionInput().getText();
                String answer = dialog.getCorrectAnswerInput().getText();

                System.out.println("Add Question: " + question + " with answer: " + answer);
            }
        });
        dialog.setLambdaCancel(new Runnable() {
            @Override
            public void run() {
                System.out.println("Adding question was canceled");
            }
        });

        dialog.present();
    }

    private void onDelQuestionButton(ActionEvent event){

    }

    private void onAnswerQuestionButton(ActionEvent event){
        Alternative alternative =
                currentQuestion.value.alternatives[gui.getAlternativeList().getSelectedIndex()];

    }

    private void onGetRandomQuestionButton(ActionEvent event){
        try {
            QuestionHolder questionHolder = new QuestionHolder();
            boolean result = server.getRandomQuestion(questionHolder);
            if(questionHolder == null){
                updateCurrentQuestion(questionHolder);
            }else{
                updateCurrentQuestion(null);
            }
        } catch (Exception e){}
    }

    public static void main(String [] args){
        String port = "6666";
        String ip = "localhost";

        InteractiveClient client = new InteractiveClient();
        client.initGUI();

        try {

            boolean connected = client.connect(port, ip);

            client.onGetRandomQuestionButton(null);

        } catch (Exception e){
            if(server == null){
                int option = JOptionPane.YES_OPTION;
                while(server == null && option == JOptionPane.YES_OPTION){
                    option = JOptionPane.showConfirmDialog(client.getFrame(), "Failed to connect to server, \nDo you want to retry?",
                            "Connection failed", JOptionPane.YES_NO_OPTION);
                    if(option == JOptionPane.NO_OPTION){
                        System.exit(-1);
                    }
                    try{
                        client.connect(port, ip);
                    }catch (Exception ee){}
                }

            } else {
                JOptionPane.showMessageDialog(client.getFrame(), "An error!!\n" + e.getCause());
                System.exit(-1);
            }
        }
    }

}
