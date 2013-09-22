package no.ifi.inf5040;

import no.ifi.inf5040.gui.InteractiveFormGUI;

import javax.swing.*;

public class InteractiveClient extends ClientBase{

    private InteractiveFormGUI gui;

    public InteractiveClient(){
        gui = new InteractiveFormGUI();
    }

    public InteractiveFormGUI getGui() {
        return gui;
    }

    public static void main(String [] args){
        InteractiveClient client = new InteractiveClient();
//        QuizServer server = client.connect("1024", "localhost");

        JFrame frame = new JFrame("Awesome quiz!");
        frame.setContentPane(client.getGui().getMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
