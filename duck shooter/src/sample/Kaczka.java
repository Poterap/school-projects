package sample;

import javafx.animation.PathTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Kaczka {

    private int zycia = 0;
    private int strzaly;
    private ImageView imageView;
    private final Gra gra;
    boolean dead = false;

    public Kaczka(Gra gra) throws FileNotFoundException {
        this.gra=gra;
        int los = (int) (Math.random()*13);
        String path = null;

        switch (los){
            case 7:
            case 8:
            case 9:
                this.zycia=2;
                path="kaczka1.png";
                break;
            case 10:
            case 11:
                this.zycia=5;
                path="kaczka.png";
                break;
            case 12:
                this.zycia=10;
                path="kaczkaBoss.png";
                break;
            default:
                this.zycia=1;
                path="kaczkabobas.png";
                break;
        }
            FileInputStream inputStream = new FileInputStream(path);
            Image image = new Image(inputStream);
            this.imageView = new ImageView(image);
            imageView.setFitHeight(150);
            imageView.setFitWidth(150);

            PathTransition pathTransition = new PathTransition();
            pathTransition.setNode(imageView);
            pathTransition.setDuration(Duration.seconds(Math.random() * 10 + 5));
//            pathTransition.setDuration(Duration.seconds(1));

            Path line = new Path();
            line.getElements().add(new MoveTo(-400, Math.random() * 400 - 200));
            line.getElements().add(new LineTo(-200, Math.random() * 400 - 200));
            line.getElements().add(new LineTo(0, Math.random() * 400 - 200));
            line.getElements().add(new LineTo(200, Math.random() * 400 - 200));
            line.getElements().add(new LineTo(500, Math.random() * 400 - 200));
            pathTransition.setPath(line);
            pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
            pathTransition.setAutoReverse(true);
            imageView.setOnMouseClicked(e -> {
                strzal();
            });
            pathTransition.setOnFinished(e -> {
                gra.getRoot().getChildren().remove(imageView);
                if (!dead) {
                    gra.plusSkuchy();
                }
            });
            pathTransition.play();
    }

    public void strzal(){
        strzaly++;
        if (strzaly>=zycia){
            dead = true;
            gra.getRoot().getChildren().remove(imageView);
            gra.plusPunkty();
        }
    }

    public ImageView getImageView() {
        return imageView;
    }
}
