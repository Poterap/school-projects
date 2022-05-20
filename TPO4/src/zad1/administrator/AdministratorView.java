package zad1.administrator;

import javax.swing.*;
import java.awt.*;

public class AdministratorView {
    protected JFrame jFrame = new JFrame("Administrator");
    protected JButton addTopicButton = new JButton("add topic");
    protected JButton deleteTopicButton = new JButton("delete topic");
    protected JButton sendButton = new JButton("Send");
    protected JTextArea jTextArea = new JTextArea();
    private JScrollPane jscrollPane = new JScrollPane(jTextArea);
    protected JComboBox jComboBox = new JComboBox();

    public AdministratorView() {

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(1,2,30,30));
        jPanel.add(jComboBox);
        jPanel.add(sendButton);

        jFrame.setLayout(new BorderLayout());

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());
        addTopicButton.setPreferredSize(new Dimension(115, 40));
        deleteTopicButton.setPreferredSize(new Dimension(115, 40));
        buttonsPanel.add(addTopicButton);
        buttonsPanel.add(deleteTopicButton);

        jFrame.add(jscrollPane, BorderLayout.CENTER);
        jFrame.add(buttonsPanel, BorderLayout.NORTH);
        jFrame.add(jPanel, BorderLayout.SOUTH);
        jFrame.setSize(500, 300);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
