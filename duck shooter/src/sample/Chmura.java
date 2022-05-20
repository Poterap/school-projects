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

public class Chmura {

    private final ImageView chmura;

    public Chmura(Gra gra) throws FileNotFoundException {
        FileInputStream chmurainputStream = new FileInputStream("chmura.png");
        Image chmuraimage = new Image(chmurainputStream);
        this.chmura = new ImageView(chmuraimage);
        chmura.setFitHeight(180);
        chmura.setFitWidth(250);

        PathTransition pathTransition = new PathTransition();
        pathTransition.setNode(chmura);
        pathTransition.setDuration(Duration.seconds(15));

        Path path = new Path();
        path.getElements().add(new MoveTo(650,Math.random()*400-200));
        path.getElements().add(new LineTo(-650,Math.random()*400-200));

        pathTransition.setPath(path);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setAutoReverse(true);
        pathTransition.setOnFinished(e -> {
            gra.getRoot().getChildren().remove(chmura);
        });
        pathTransition.play();
    }

    public ImageView getChmura() {
        return chmura;
    }
}
