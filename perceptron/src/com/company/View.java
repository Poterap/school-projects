package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;

public class View {

    protected JFrame frame = new JFrame("mpp3");
    protected JButton button;
    protected JButton cbutton;
    protected JLabel jLabel;
    protected JTextArea ta;

    public View() {

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        cbutton = new JButton("clear");
        button = new JButton("start");
        jLabel = new JLabel("...",SwingConstants.CENTER);

        frame.setLayout(new BorderLayout());
        ta = new JTextArea();
        JScrollPane sp = new JScrollPane(ta);

        panel.add(button);
        panel.add(cbutton);
        frame.add(panel, BorderLayout.NORTH);
        frame.add(sp, BorderLayout.CENTER);
        frame.add(jLabel, BorderLayout.SOUTH);

        frame.setSize(500,500);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
