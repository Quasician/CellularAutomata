package cellsociety;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application{

    private final static int WIDTH = 500;
    private final static int HEIGHT = 500;
    int currentWidth = WIDTH;
    int currentHeight = HEIGHT;
    @Override
    public void start(Stage primaryStage) throws Exception {

        Group root = new Group();
        primaryStage.setTitle("temp title");
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.show();
        double seconds = .1;
//        GOLSim sim = new GOLSim(50,50, WIDTH, HEIGHT);
//        PercSim sim = new PercSim(50,50, WIDTH, HEIGHT);
//        FireSim sim = new FireSim(50,50, WIDTH, HEIGHT);
//        SegSim sim = new SegSim(50,50, WIDTH, HEIGHT);
        PredPraySim sim = new PredPraySim(50, 50, WIDTH, HEIGHT);
        Visualizer vis = new Visualizer(sim.getGrid().length,sim.getGrid()[0].length,currentWidth, currentHeight, root, sim.getColorMap());
        //System.out.println("X: "+ currentGrid.length);
        //System.out.println("Y: "+ currentGrid[0].length);
        vis.initialize(sim.getGrid());
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(seconds), e -> {
                    // code to execute here...
                    sim.updateGrid();
                    //System.out.println("ROUND");
                    vis.colorGrid(sim.getGrid());
                    //System.out.println("END ROUND");
                })
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public static void main (String[] args) {
        launch(args);
    }
}
