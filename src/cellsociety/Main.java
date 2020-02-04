package cellsociety;

import configuration.GetPropertyValues;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.event.ActionEvent;
import java.beans.EventHandler;
import java.io.IOException;
import java.util.HashMap;

public class Main extends Application{

    private final static int WIDTH = 500;
    private final static int HEIGHT = 500;
    private final static int ROW = 125;
    private final static int COLS = 125;
    Timeline currentTimeline;
    HashMap<String,Double> currentParams;
    Simulation currentSim;
    Visualizer currentViz;
    double seconds = 1;
    BorderPane root = new BorderPane();
    HBox bottomButtons = new HBox();
    VBox rightButtons = new VBox();
    GetPropertyValues properties;


    @Override
    public void start(Stage primaryStage) throws Exception {
        properties = new GetPropertyValues();
        currentParams = xml_parser.readPredPreyFile();
        primaryStage.setTitle(properties.getPropValues("title"));
        primaryStage.setScene(new Scene(root, WIDTH + 100, HEIGHT));
        primaryStage.show();

        PredPreySim sim = new PredPreySim(ROW, COLS, WIDTH, HEIGHT, currentParams);

        Visualizer vis = new Visualizer(sim.getGrid().length,sim.getGrid()[0].length,WIDTH, HEIGHT, root, sim.getColorMap());
        currentSim = sim;
        currentViz = vis;

        simbutton_setup();

        Button fast = makeSpeedButton(properties.getPropValues("buttonFast"), seconds*10);
        Button normal = makeSpeedButton(properties.getPropValues("buttonNormal"), seconds);
        Button slow = makeSpeedButton(properties.getPropValues("buttonSlow"), seconds*0.5);

        Button step= new Button(properties.getPropValues("buttonStep"));
        step.setOnAction(e ->{
            currentTimeline.setRate(0);
            currentSim.updateGrid();
            currentViz.colorGrid(currentSim.getGrid());

        });

        root.setBottom(bottomButtons);
        root.setRight(rightButtons);
        bottomButtons.getChildren().addAll(slow,normal,fast,step);

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

    public void sim_helper(Simulation temp){
        currentSim = temp;
        Visualizer vis1 = new Visualizer(temp.getGrid().length,temp.getGrid()[0].length,WIDTH, HEIGHT, root, temp.getColorMap());
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
    }

    private Button makeSpeedButton(String name, Double speed) {
        Button button = new Button(name);
        button.setOnAction(e -> currentTimeline.setRate(speed));
        return button;
    }

    private void simbutton_setup() throws IOException {
        properties = new GetPropertyValues();
        Button seg= new Button(properties.getPropValues("buttonSeg"));
        rightButtons.getChildren().add(seg);
        seg.setOnAction(e ->{
            //sim = null;
            SegSim temp = new SegSim(ROW,COLS, WIDTH, HEIGHT, currentParams);
            currentSim = temp;
            sim_helper(temp);
        });

        Button gol= new Button(properties.getPropValues("buttonGol"));
        rightButtons.getChildren().add(gol);
        gol.setOnAction(e ->{
            //sim = null;
            GOLSim temp = new GOLSim(ROW,COLS, WIDTH, HEIGHT, currentParams);
            currentSim = temp;
            sim_helper(temp);
        });

        Button perc= new Button(properties.getPropValues("buttonPerc"));
        rightButtons.getChildren().add(perc);
        perc.setOnAction(e ->{
            //sim = null;
            PercSim temp = new PercSim(ROW,COLS, WIDTH, HEIGHT, currentParams);
            currentSim = temp;
            sim_helper(temp);
        });

        Button fire= new Button(properties.getPropValues("buttonFire"));
        rightButtons.getChildren().add(fire);
        fire.setOnAction(e ->{
            //sim = null;
            Simulation temp = new FireSim(ROW,COLS, WIDTH, HEIGHT, currentParams);
            sim_helper(temp);
        });

        Button pred= new Button(properties.getPropValues("buttonPP"));
        rightButtons.getChildren().add(pred);
        pred.setOnAction(e ->{
            //sim = null;
            PredPreySim temp = new PredPreySim(ROW,COLS, WIDTH, HEIGHT ,currentParams);
            sim_helper(temp);
        });
    }


    public static void main (String[] args) {
        launch(args);
    }
}
