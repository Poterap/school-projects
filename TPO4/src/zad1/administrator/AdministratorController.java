package zad1.administrator;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

public class AdministratorController {
    public AdministratorController(AdministratorView administratorView, AdministratorModel administratorModel) {

        administratorView.addTopicButton.addActionListener(e -> {
            String str = JOptionPane.showInputDialog(null, "Enter topic name : ","Subscribe new topic", 1);
            if (str!=null){
                administratorModel.addNewTopic(str);
                administratorView.jComboBox.setModel(new DefaultComboBoxModel(administratorModel.getTopicsArray()));
            }
        });

        administratorView.deleteTopicButton.addActionListener(e -> {
            JComboBox comboBox = new JComboBox(administratorModel.getTopicsArray());
            String str = (String) JOptionPane.showInputDialog(
                    null,
                    "chose topic",
                    "Delete Topic",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    administratorModel.getTopicsArray(),
                    "ham");
            administratorModel.deleteTopic(str);
            administratorView.jComboBox.setModel(new DefaultComboBoxModel(administratorModel.getTopicsArray()));

        });

        administratorView.sendButton.addActionListener(e -> administratorModel.sendMessage(administratorView.jComboBox.getSelectedItem(),administratorView.jTextArea.getText()));

        administratorView.jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("zamykam");
                administratorModel.endconnection();
                System.exit(0);
            }
        });
        administratorView.jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
}
