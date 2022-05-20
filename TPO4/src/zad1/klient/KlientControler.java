package zad1.klient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class KlientControler {

    private KlientModel klientModel;
    private KlientView klientView;

    public KlientControler(KlientModel klientModel, KlientView klientView) {
        this.klientModel = klientModel;
        this.klientView = klientView;

        klientView.subscribeButton.addActionListener(e -> {
            JComboBox comboBox = new JComboBox(klientModel.getTopicsArray());
            String str = (String) JOptionPane.showInputDialog(
                    null,
                    "chose topic",
                    "Subscribe Topic",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    klientModel.getTopiclist(),
                    "ham");
            if (str!=null) {
                klientModel.addNewTopic(str);
                klientView.subscriptions.setText("Subscriptions: " + klientModel.getSubTopics());
            }
        });

        klientView.unSubscribeButton.addActionListener(e -> {
            JComboBox comboBox = new JComboBox(klientModel.getTopicsArray());
            String str = (String) JOptionPane.showInputDialog(
                    null,
                    "chose topic",
                    "Delete Topic",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    klientModel.getTopicsArray(),
                    "ham");
            if (str!=null) {
                klientModel.deleteTopic(str);
                klientView.subscriptions.setText("Subscriptions: " + klientModel.getSubTopics());
            }

        });

        klientView.jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("zamykam");
                klientModel.endconnection();
                System.exit(0);
            }
        });
        klientView.jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    }
}
