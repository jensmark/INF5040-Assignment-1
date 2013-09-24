package no.ifi.inf5040.gui;

import javax.swing.*;


public class InteractiveFormGUI {
    private JLabel questionLabel;
    private JButton newQuestionButton;
    private JButton answerQuestionButton;
    private JPanel mainPanel;
    private JButton delQuestionButton;
    private JButton getQuestionButton;
    private JList alternativeList;

    public void createUIComponents(){

    }

    public JButton getDelQuestionButton() {
        return delQuestionButton;
    }

    public JLabel getQuestionLabel() {
        return questionLabel;
    }

    public JButton getNewQuestionButton() {
        return newQuestionButton;
    }


    public JButton getAnswerQuestionButton() {
        return answerQuestionButton;
    }

    public JButton getGetQuestionButton() {
        return getQuestionButton;
    }

    public JList getAlternativeList() {
        return alternativeList;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}

