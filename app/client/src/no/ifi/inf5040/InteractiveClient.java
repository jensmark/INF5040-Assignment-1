package no.ifi.inf5040;

import Quiz.QuizServer;
import no.ifi.inf5040.gui.InteractiveFormGUI;

import javax.swing.*;
import java.awt.*;

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
        QuizServer server = client.connect("2809", "localhost");

        JFrame frame = new JFrame("Awesome quiz!");
        frame.setPreferredSize(new Dimension(800, 600));
        frame.setContentPane(client.getGui().getMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        if(server == null){
            int option = JOptionPane.YES_OPTION;
            while(server == null && option == JOptionPane.YES_OPTION){
                option = JOptionPane.showConfirmDialog(frame, "Failed to connect to server, \nDo you want to retry?",
                        "Connection failed", JOptionPane.YES_NO_OPTION);
                if(option == JOptionPane.NO_OPTION){
                    System.exit(0);
                }

                server = client.connect("2809", "localhost");
            }

        }
    }

}
