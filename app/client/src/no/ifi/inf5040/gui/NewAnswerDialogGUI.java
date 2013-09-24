package no.ifi.inf5040.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class NewAnswerDialogGUI extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextArea questionInput;
    private JTextField correctAnswerInput;
    private JButton addAlternativeButton;
    private JPanel alternativePanel;

    private ArrayList<JTextField> incorrectAnswerInput;

    private Runnable lambdaOK;
    private Runnable lambdaCancel;

    public NewAnswerDialogGUI(JFrame parent) {
        super(parent);
        setLocationRelativeTo(parent);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        incorrectAnswerInput = new ArrayList<JTextField>();

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        addAlternativeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField field = new JTextField();

                //field.setPreferredSize(new Dimension(alternativePanel.getSize().width, 15));
                alternativePanel.add(field);
                alternativePanel.validate();

                incorrectAnswerInput.add(field);
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public JTextArea getQuestionInput() {
        return questionInput;
    }

    public ArrayList<JTextField> getIncorrectAnswerInput() {
        return incorrectAnswerInput;
    }

    public JTextField getCorrectAnswerInput() {
        return correctAnswerInput;
    }

    public void setLambdaOK(Runnable lambdaOK) {
        this.lambdaOK = lambdaOK;
    }

    public void setLambdaCancel(Runnable lambdaCancel) {
        this.lambdaCancel = lambdaCancel;
    }


    public void present(){
        pack();
        setVisible(true);
    }

    private void onOK() {
        if(lambdaOK != null){
            lambdaOK.run();
        }
        dispose();
    }

    private void onCancel() {
        if(lambdaCancel != null){
            lambdaCancel.run();
        }
        dispose();
    }

    private void createUIComponents() {
        alternativePanel = new JPanel();
        alternativePanel.setLayout(new BoxLayout(alternativePanel, BoxLayout.PAGE_AXIS));
    }
}
