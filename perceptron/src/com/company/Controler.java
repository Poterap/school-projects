package com.company;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class Controler {

    public Controler(View view, ArrayList<Perceptron> perseptrons) {

        view.cbutton.addActionListener(e -> view.ta.setText(""));

        view.button.addActionListener(new ActionListener() {
            Perceptron win=null;
            double net;
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Perceptron perseptron : perseptrons) {
                    try {
                        double iloczyn = perseptron.use(view.ta.getText());
                        if (iloczyn >= perseptron.getProg()){
                            if (win==null){
                                win=perseptron;
                                net= iloczyn- perseptron.getProg();
                            }
                            else if (net<iloczyn- perseptron.getProg()){
                                win=perseptron;
                                net=iloczyn- perseptron.getProg();
                            }
                        }

                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
                if (win==null){

                    System.out.println("nie rozpoznano");
                    view.jLabel.setText("nie rozpoznano");
                }
                else {
                    System.out.println(win.getLanguage());
                    view.jLabel.setText(win.getLanguage());
                }
                win = null;
            }
        });
    }
}
