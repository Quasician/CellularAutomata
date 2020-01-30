package cellsociety;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

    private final static int WIDTH = 500;
    private final static int HEIGHT = 500;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root;
        primaryStage.setTitle("temp title");
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.show();
    }

    public static void main (String[] args) {
        launch(args);
    }
}
