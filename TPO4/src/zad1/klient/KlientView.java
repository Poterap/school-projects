package zad1.klient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class KlientView {
    protected JFrame jFrame = new JFrame("Klient");
    protected JButton subscribeButton = new JButton("Subscribe");
    protected JButton unSubscribeButton = new JButton("Unsubscribe");
    protected JTextArea jTextArea = new JTextArea();
    private JScrollPane jscrollPane = new JScrollPane(jTextArea);
    protected JLabel subscriptions = new JLabel("Subscriptions:", SwingConstants.CENTER);

    public KlientView() {
        jFrame.setLayout(new BorderLayout());

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());
        subscribeButton.setPreferredSize(new Dimension(115,40));
        unSubscribeButton.setPreferredSize(new Dimension(115,40));
        buttonsPanel.add(subscribeButton);
        buttonsPanel.add(unSubscribeButton);

        jFrame.add(jscrollPane, BorderLayout.CENTER);
        jFrame.add(buttonsPanel, BorderLayout.NORTH);
        jFrame.add(subscriptions,BorderLayout.SOUTH);
        jFrame.setSize(500,300);
        jFrame.setVisible(true);
//        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
