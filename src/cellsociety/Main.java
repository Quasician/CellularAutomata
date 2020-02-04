package cellsociety;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application{

    private final static int WIDTH = 500;
    private final static int HEIGHT = 500;
    int currentWidth = WIDTH;
    int currentHeight = HEIGHT;
    Timeline currentTimeline;
    Simulation currentSim;
    Visualizer currentViz;
    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane root = new BorderPane();
        primaryStage.setTitle("Simulation");
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.show();
        double seconds = 1;
//        GOLSim sim = new GOLSim(100,100, WIDTH, HEIGHT);
 //       PercSim sim = new PercSim(100,100, WIDTH, HEIGHT);
        //FireSim sim = new FireSim(100,100, WIDTH, HEIGHT);
//        SegSim sim = new SegSim(30,30, WIDTH, HEIGHT);
 PredPraySim sim = new PredPraySim(50, 50, WIDTH, HEIGHT);


        Visualizer vis = new Visualizer(sim.getGrid().length,sim.getGrid()[0].length,currentWidth, currentHeight, root, sim.getColorMap());
        currentSim = sim;
        currentViz = vis;
        HBox bottomButtons = new HBox();
        HBox bottomButtons2 = new HBox(30);
        HBox bigbox = new HBox();



        Button seg= new Button("Segregation");

        seg.setOnAction(e ->{
            //sim = null;
            SegSim temp = new SegSim(100,100, WIDTH, HEIGHT);
            currentSim = temp;
            Visualizer vis1 = new Visualizer(temp.getGrid().length,temp.getGrid()[0].length,currentWidth, currentHeight, root, temp.getColorMap());
            currentViz = vis1;
            vis1.initialize(temp.getGrid());
            currentTimeline = new Timeline(
                    new KeyFrame(Duration.seconds(seconds), j -> {
                        temp.updateGrid();
                        vis1.colorGrid(temp.getGrid());
                    })
            );
            currentTimeline.setCycleCount(Animation.INDEFINITE);
            currentTimeline.play();

        });

        Button gol= new Button("Game of Life");

        gol.setOnAction(e ->{
            //sim = null;
            GOLSim temp = new GOLSim(100,100, WIDTH, HEIGHT);
            currentSim = temp;
            Visualizer vis1 = new Visualizer(temp.getGrid().length,temp.getGrid()[0].length,currentWidth, currentHeight, root, temp.getColorMap());
            currentViz = vis1;
            vis1.initialize(temp.getGrid());
            currentTimeline = new Timeline(
                    new KeyFrame(Duration.seconds(seconds), j -> {
                        temp.updateGrid();
                        vis1.colorGrid(temp.getGrid());
                    })
            );
            currentTimeline.setCycleCount(Animation.INDEFINITE);
            currentTimeline.play();

        });

        Button perc= new Button("Percolation");

        perc.setOnAction(e ->{
            //sim = null;
            PercSim temp = new PercSim(100,100, WIDTH, HEIGHT);
            currentSim = temp;
            Visualizer vis1 = new Visualizer(temp.getGrid().length,temp.getGrid()[0].length,currentWidth, currentHeight, root, temp.getColorMap());
            currentViz = vis1;
            vis1.initialize(temp.getGrid());
            currentTimeline = new Timeline(
                    new KeyFrame(Duration.seconds(seconds), j -> {
                        temp.updateGrid();
                        vis1.colorGrid(temp.getGrid());
                    })
            );
            currentTimeline.setCycleCount(Animation.INDEFINITE);
            currentTimeline.play();

        });

        Button fire= new Button("Fire");

        fire.setOnAction(e ->{
            //sim = null;
            Simulation temp = new FireSim(100,100, WIDTH, HEIGHT);
            currentSim = temp;
            Visualizer vis1 = new Visualizer(temp.getGrid().length,temp.getGrid()[0].length,currentWidth, currentHeight, root, temp.getColorMap());
            currentViz = vis1;
            vis1.initialize(temp.getGrid());
            currentTimeline = new Timeline(
                    new KeyFrame(Duration.seconds(seconds), j -> {
                        temp.updateGrid();
                        vis1.colorGrid(temp.getGrid());
                    })
            );
            currentTimeline.setCycleCount(Animation.INDEFINITE);
            currentTimeline.play();

        });

        Button pred= new Button("Pred Prey");

        pred.setOnAction(e ->{
            //sim = null;
            PredPraySim temp = new PredPraySim(100,100, WIDTH, HEIGHT);
            currentSim = temp;
            Visualizer vis1 = new Visualizer(temp.getGrid().length,temp.getGrid()[0].length,currentWidth, currentHeight, root, temp.getColorMap());
            currentViz = vis1;
            vis1.initialize(temp.getGrid());
            currentTimeline = new Timeline(
                    new KeyFrame(Duration.seconds(seconds), j -> {
                        temp.updateGrid();
                        vis1.colorGrid(temp.getGrid());
                    })
            );
            currentTimeline.setCycleCount(Animation.INDEFINITE);
            currentTimeline.play();

        });

        Button fast= new Button("Fast");
        fast.setOnAction(e ->{
            currentTimeline.setRate(seconds*10);
        });

        Button slow= new Button("Slow");
        slow.setOnAction(e ->{
            currentTimeline.setRate(seconds*0.5);
        });

        Button normal= new Button("Normal");
        normal.setOnAction(e ->{
            currentTimeline.setRate(seconds);
        });

        Button step= new Button("Step");
        step.setOnAction(e ->{
            currentTimeline.setRate(0);
            currentSim.updateGrid();
            currentViz.colorGrid(currentSim.getGrid());

        });

        root.setBottom(bottomButtons);
        root.getChildren().add(bottomButtons2);
        bottomButtons2.setLayoutX(300);
        bottomButtons2.setLayoutY(450);
        bottomButtons.getChildren().addAll(slow,normal,fast,step,seg,gol);
        bottomButtons2.getChildren().addAll(pred,fire,perc);
        //bigbox.getChildren().addAll(bottomButtons,bottomButtons2);
        //bigbox.setAlignment(Pos.BOTTOM_CENTER);


        vis.initialize(sim.getGrid());
        currentTimeline = new Timeline(
                new KeyFrame(Duration.seconds(seconds), e -> {
                    sim.updateGrid();
                    vis.colorGrid(sim.getGrid());
                })
        );
        currentTimeline.setCycleCount(Animation.INDEFINITE);
        currentTimeline.play();
    }

    public static void main (String[] args) {
        launch(args);
    }
}
