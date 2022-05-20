package zad1;

import javafx.application.Platform;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controler {

    private View view;

    public Controler(View view) {
        this.view = view;
        dialog();

        view.button.addActionListener(e -> dialog());
    }

    public void dialog(){
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(3,2,2,2));

        JTextField countryInput = new JTextField();
        JTextField cityInput = new JTextField();
        JTextField currencyCodeInput = new JTextField();

        jPanel.add(new JLabel("Country:"));
        jPanel.add(countryInput);
        jPanel.add(new JLabel("City:"));
        jPanel.add(cityInput);
        jPanel.add(new JLabel("Currency code:"));
        jPanel.add(currencyCodeInput);

        int option = JOptionPane.showConfirmDialog(new JFrame(),jPanel,"Input parametrs in English",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);

        if (option == JOptionPane.OK_OPTION){

            Service service = new Service(countryInput.getText());
            view.weatherLabel.setText(service.weatherInfo(cityInput.getText()));
            view.currencyRaterLabel.setText(String.valueOf(service.getRateFor(currencyCodeInput.getText())));
            view.plnRateLabel.setText(String.valueOf(service.getNBPRate()));
            Platform.runLater(() -> view.initFX(view.jfxPanel, cityInput.getText()));
        }
//        view.button.setText("Change data");

    }


}
