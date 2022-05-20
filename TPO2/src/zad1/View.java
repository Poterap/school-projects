package zad1;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.swing.*;
import java.awt.*;

public class View {

    public JFXPanel jfxPanel;
    JFrame frame = new JFrame("TPO2");
    JLabel weatherLabel;
    JLabel currencyRaterLabel;
    JLabel plnRateLabel;
    JButton button;

    public View() {
        frame.setLayout(new BorderLayout());
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout());

        Dimension dimension = new Dimension(250,100);

        weatherLabel = new JLabel("", SwingConstants.CENTER);
        weatherLabel.setBorder(BorderFactory.createTitledBorder("Weather"));
        weatherLabel.setPreferredSize(dimension);
        jPanel.add(weatherLabel);
        currencyRaterLabel = new JLabel("", SwingConstants.CENTER);
        currencyRaterLabel.setBorder(BorderFactory.createTitledBorder("Currency rate"));
        currencyRaterLabel.setPreferredSize(dimension);
        jPanel.add(currencyRaterLabel);
        plnRateLabel = new JLabel("", SwingConstants.CENTER);
        plnRateLabel.setBorder(BorderFactory.createTitledBorder("PLN rate"));
        plnRateLabel.setPreferredSize(dimension);
        jPanel.add(plnRateLabel);
        button = new JButton();
        button.setText("Change data");
        button.setPreferredSize(dimension);
        jPanel.add(button);

        jfxPanel = new JFXPanel();
        frame.add(jfxPanel, BorderLayout.CENTER);

        frame.add(jPanel, BorderLayout.NORTH);
        frame.setSize(1050,550);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Platform.runLater(() -> initFX(jfxPanel, "city"));
    }

    public void initFX(JFXPanel jfxPanel, String city) {
        WebView browser = new WebView();
        WebEngine webEngine = browser.getEngine();
        String www  = "https://en.wikipedia.org/wiki/"+city;
        webEngine.load(www);
        Scene scene = new Scene(browser);

        jfxPanel.setScene(scene);
    }
}
