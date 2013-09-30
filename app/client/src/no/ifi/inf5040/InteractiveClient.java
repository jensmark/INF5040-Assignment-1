package no.ifi.inf5040;

import Quiz.*;
import Quiz.QuizServerPackage.*;
import no.ifi.inf5040.gui.InteractiveFormGUI;
import no.ifi.inf5040.gui.NewAnswerDialogGUI;
import no.ifi.inf5040.impl.AlternativeImpl;
import no.ifi.inf5040.impl.CompleteQuestionImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
        String[] alternativeText = new String[question.value.alternatives.length];
        for(int i = 0; i < alternativeText.length; i++){
            alternativeText[i] = question.value.alternatives[i].sentence;
        }
        gui.getAlternativeList().setListData(alternativeText);

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
                ArrayList<JTextField> alt = dialog.getIncorrectAnswerInput();

                if(question == null || question.length() == 0){
                    return;
                }
                if(answer == null || answer.length() == 0){
                    return;
                }
                if(alt == null || alt.size() == 0){
                    return;
                }

                int size = alt.size() + 1;
                int correct = (int)(Math.random() * (size - 1));
                System.out.println(correct);

                CompleteQuestion completeQuestion = new CompleteQuestionImpl();
                completeQuestion.sentence = question;
                completeQuestion.correctAlternatives = new char[]{(char)correct};



                Alternative[] alternatives = new Alternative[size];

                int i2 = 0;
                for(int i = 0; i < size; i++){
                    if(i == correct){
                        alternatives[i] = new AlternativeImpl();
                        alternatives[i].id = (char)i;
                        alternatives[i].sentence = answer;
                    } else {
                        alternatives[i] = new AlternativeImpl();
                        alternatives[i].id = (char)i;
                        alternatives[i].sentence = alt.get(i2).getText();
                        i2++;
                    }
                }

                completeQuestion.alternatives = alternatives;

                try{
                    completeQuestion.id = server.newQuestion(completeQuestion);
                } catch (Exception e){
                    e.printStackTrace();
                }

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
        if(currentQuestion != null){
            try{
                int id = server.removeQuestion(currentQuestion.value.id);
                if(id != -1){
                    currentQuestion = null;
                    updateCurrentQuestion(null);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void onAnswerQuestionButton(ActionEvent event){
        int index = gui.getAlternativeList().getSelectedIndex();
        Alternative alternative;
        if(index >= 0 && index <= gui.getAlternativeList().getMaxSelectionIndex() && currentQuestion != null){
            alternative = currentQuestion.value.alternatives[index];
        } else {
            return;
        }
        try{
            alternativesIdsHolder holder = new alternativesIdsHolder();
            char[] corr = {alternative.id};
            boolean correct = server.answerQuestion(currentQuestion.value.id, corr, holder);
            if(correct){
                JOptionPane.showMessageDialog(frame, "Correct answer!");
            } else {
                JOptionPane.showMessageDialog(frame, "Incorrect answer!\nThe correct answer is: " + holder.value);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void onGetRandomQuestionButton(ActionEvent event){
        try {
            QuestionHolder questionHolder = new QuestionHolder();
            boolean result = server.getRandomQuestion(questionHolder);
            if(questionHolder.value != null){
                updateCurrentQuestion(questionHolder);
            }else{
                updateCurrentQuestion(null);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String [] args){
        String port = "6666";
        String ip = "localhost";

        InteractiveClient client = new InteractiveClient();
        client.initGUI();

        try {

            boolean connected = client.connect(port, ip);

            client.onGetRandomQuestionButton(null);

            //int t = server.removeQuestion(0);
            //alternativesIdsHolder holder = new alternativesIdsHolder();
            //char[] corr = {0};
            //boolean correct = server.answerQuestion(0, corr, holder);

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
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }

}
