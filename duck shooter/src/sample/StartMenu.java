package sample;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;

import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.awt.*;
import java.io.FileNotFoundException;
import java.util.Comparator;

public class StartMenu {

    Main app;
    Scene scene;

    public StartMenu(Main app) {
        app.primaryStage.setResizable(true);
        this.app=app;
        FlowPane flowPane = new FlowPane();
        flowPane.setVgap(10);
        flowPane.setHgap(10);
        flowPane.setPrefWrapLength(250);

        Button newGame = new Button("New Game");
        newGame.setOnAction(actionEvent -> app.gra.startgame());
        Button highScores = new Button("High Scores");
        highScores.setOnAction(actionEvent -> {
            ScrollPane scrollPane = new ScrollPane();
            StackPane rankingpane = new StackPane();
            Stage rankingOkno = new Stage();
            Text textArea = new Text();
//            TextArea textArea = new TextArea();
//            textArea.setPrefSize(200,400);
//            textArea.setEditable(false);
            Ranking ranking = null;
            try {
                ranking = new Ranking();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            ranking.sort(Comparator.comparingInt(o -> o.wynik));
            textArea.setText(ranking.toString());
            scrollPane.setPrefSize(200,400);
            scrollPane.setContent(textArea);
            rankingpane.getChildren().add(scrollPane);
            Scene rankingScena = new Scene(rankingpane,200,400);
            rankingOkno.setTitle("ranking");
            rankingOkno.setScene(rankingScena);
            rankingOkno.show();
        });
        Button exit = new Button("Exit");
        exit.setOnAction(actionEvent -> javafx.application.Platform.exit());

        flowPane.getChildren().add(newGame);
        flowPane.getChildren().add(highScores);
        flowPane.getChildren().add(exit);

        scene = new Scene(flowPane, 300,50);
    }

    public void startMenu(){
        app.primaryStage.setScene(scene);
    }
}
