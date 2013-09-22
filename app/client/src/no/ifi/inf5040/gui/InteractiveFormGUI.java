package no.ifi.inf5040.gui;

import javax.swing.*;


public class InteractiveFormGUI {
    private JList questionList;
    private JLabel questionLabel;
    private JButton newQuestionButton;
    private JTextArea questionInput;
    private JButton answerQuestionButton;
    private JPanel mainPanel;

    public void createUIComponents(){

    }

    public JList getQuestionList() {
        return questionList;
    }

    public JLabel getQuestionLabel() {
        return questionLabel;
    }

    public JButton getNewQuestionButton() {
        return newQuestionButton;
    }

    public JTextArea getQuestionInput() {
        return questionInput;
    }

    public JButton getAnswerQuestionButton() {
        return answerQuestionButton;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}

