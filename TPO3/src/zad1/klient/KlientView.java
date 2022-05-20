package zad1.klient;

import javax.swing.*;
import java.awt.*;

public class KlientView {

    private JFrame jFrame = new JFrame("TPO3");
    protected JButton button = new JButton("translate");
    protected JTextArea jTextAreaSlowo =new JTextArea();
    protected JTextArea jTextAreaKod =new JTextArea();
    protected JLabel jLabel = new JLabel("...");

    public KlientView() {
        jFrame.setLayout(new FlowLayout());
        JPanel panel = new JPanel(new FlowLayout());
        jTextAreaSlowo.setColumns(25);
        panel.add(jTextAreaSlowo);
        jTextAreaKod.setColumns(2);
        panel.add(jTextAreaKod);
        panel.add(button);
        jFrame.add(panel);
        jFrame.add(jLabel);


//        jFrame.pack();
        jFrame.setSize(490,90);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}

