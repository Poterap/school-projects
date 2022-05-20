package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.Mnemonic;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Gra {

    private StackPane root;
    private int skuchy = 0;
    private int punkty = 0;
    private final int zycia = 10;
    private List<Kaczka> kaczkas = new ArrayList<>();
    int poziomTrudnosci=2;
    private Timeline timeline;
    private Text text;
    private Text textz;
    private Thread zegar;
    private Main main;
    private Zegar czas;
    boolean raz;

    public Gra(Main main) {
        this.raz = true;
        this.main= main;
        root= new StackPane();

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), actionEvent -> {
            int los = (int) (Math.random()*100);
            if (los<10*poziomTrudnosci){
            try {
                root.getChildren().add(new Chmura(this).getChmura());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            }
            try {
                root.getChildren().add(new Kaczka(this).getImageView());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }));
        timeline.setCycleCount(-1);

        text = new Text();
        czas = new Zegar(text,this);
        zegar= new Thread(czas);

//        main.getChildren().add(kaczkaBaton);
//        Thread thread = new Thread(() -> {
//            for (int i = 0; i < 3; i++) {
//                try {
//                    main.getChildren().add(new Kaczka(10, "kaczkaBoss.png", main).getImageView());
//                } catch (FileNotFoundException e) {
//                    System.out.println("sdasfasfa");
//                }
//            }
//            main.getChildren().add(imageView);
//        });
//        thread.start();
//        main.getChildren().add(chmura);
    }

    void startgame() {
        main.primaryStage.setResizable(false);
        List<String> nazwyPoziomow = new ArrayList<>();
        nazwyPoziomow.add("latwy");
        nazwyPoziomow.add("normalny");
        nazwyPoziomow.add("trudny");

        ChoiceDialog<String> dialog = new ChoiceDialog<>("normalny", nazwyPoziomow);
        dialog.setTitle("Wybor trudnosci");
        dialog.setHeaderText("");
        dialog.setContentText("Wybierz poziom trudnosci");

        Optional<String> wybor = dialog.showAndWait();
        if (wybor.isPresent()){
            if (wybor.get().equals("latwy")){
                poziomTrudnosci=1;
            }
            else if (wybor.get().equals("trudny")){
                poziomTrudnosci=10;
            }
        }

        Scene scene = new Scene(root,800,400);

        KeyCombination kc = new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_ANY, KeyCombination.SHIFT_ANY);
        Runnable runnable = () -> {
            stopgame();
            main.menu.startMenu();
        };
        scene.getAccelerators().put(kc,runnable);

        root.getChildren().add(text);
        main.primaryStage.setScene(scene);
        timeline.play();
        zegar.start();
    }

    public StackPane getRoot() {
        return root;
    }

    public void plusSkuchy(){
        skuchy++;
        if (skuchy>=10)
            grameOver();
    }

    private void stopgame(){
        timeline.stop();
        zegar.interrupt();
    }

    private void grameOver() {
        timeline.stop();
        zegar.interrupt();
        text.setText("GAMEOVER");

        if (raz){
        Stage okno = new Stage();
        TextField textArea1 = new TextField();
        Button button = new Button("continue");
        button.setOnAction(actionEvent -> {
            try {
                Ranking ranking = new Ranking();
                ranking.add(new Gracz(textArea1.getText(),czas.forScore()));
                ranking.save();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            okno.close();
        });
        FlowPane pane = new FlowPane();
        Text text = new Text("koniec gry twój wynik to "+ czas.forScore() +" podaj nazwe");
        pane.getChildren().add(text);
        pane.getChildren().add(textArea1);
        pane.getChildren().add(button);
        Scene rankingScena = new Scene(pane,250,100);
        okno.setTitle("GAME OVER");
        okno.setScene(rankingScena);
        okno.show();
        raz=false;
    }
    }
    public int getSkuchy() {
        return skuchy;
    }

    public int getPunkty() {
        return punkty;
    }
    public void plusPunkty() {
        punkty++;
    }
}
