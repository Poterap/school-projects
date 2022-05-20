package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    StackPane root = new StackPane();
    Gra gra;
    StartMenu menu;
    Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        root = new StackPane();
        primaryStage.setTitle("Gra ”Duck shooter”");
        Scene scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
        menu = new StartMenu(this);
        menu.startMenu();
        gra = new Gra(this);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
