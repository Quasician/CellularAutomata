package View;

import Model.*;
import configuration.GetPropertyValues;
import configuration.xml_parser;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.IOException;
import java.util.HashMap;

public class Main extends Application{

    private final static int WIDTH = 500;
    private final static int HEIGHT = 500;
    private Timeline currentTimeline;
    private HashMap<String,Double> currentParams;
    private Simulation currentSim;
    private Visualizer currentViz;
    private double seconds = 1;
    private BorderPane root = new BorderPane();
    private HBox bottomButtons = new HBox();
    private VBox rightButtons = new VBox();
    private GetPropertyValues properties;



    @Override
    public void start(Stage primaryStage) throws Exception {

        currentParams = xml_parser.readFile("pred_prey.xml");
        properties = new GetPropertyValues();
        primaryStage.setTitle(properties.getPropValues("title"));
        primaryStage.setScene(new Scene(root, WIDTH + 100, HEIGHT));
        primaryStage.show();
//        GOLSim sim = new GOLSim(100,100, WIDTH, HEIGHT);
//        PercSim sim = new PercSim(100,100, WIDTH, HEIGHT);
//        FireSim sim = new FireSim(100,100, WIDTH, HEIGHT);
//        SegSim sim = new SegSim(30,30, WIDTH, HEIGHT);
//        SugarSim sim = new SugarSim(30,30, WIDTH, HEIGHT, currentParams);
        RPSSim sim = new RPSSim(100,100, WIDTH, HEIGHT, currentParams);
//        PredPreySim sim = new PredPreySim(currentParams.get("grid_height"),currentParams.get("grid_width"), WIDTH, HEIGHT, currentParams);


        Visualizer vis = new Visualizer(root, sim);
        currentSim = sim;
        currentViz = vis;

        simbutton_setup("buttonSeg", "segregation.xml");
        simbutton_setup("buttonGol", "game_of_life.xml");
        simbutton_setup("buttonPerc", "percolate.xml");
        simbutton_setup("buttonFire", "fire.xml");
        simbutton_setup("buttonPP", "pred_prey.xml");


        Button fast = makeSpeedButton(properties.getPropValues("buttonFast"), seconds*10);
        Button normal = makeSpeedButton(properties.getPropValues("buttonNormal"), seconds);
        Button slow = makeSpeedButton(properties.getPropValues("buttonSlow"), seconds*0.5);
        Button play = makeSpeedButton(properties.getPropValues("buttonPlay"), seconds);
        Button pause = makeSpeedButton(properties.getPropValues("buttonPause"), 0.0);

        Button step= new Button(properties.getPropValues("buttonStep"));
        step.setOnAction(e ->{
            currentTimeline.setRate(0);
            currentSim.updateGrid();
            currentViz.colorGrid();

        });

        root.setBottom(bottomButtons);
        root.setRight(rightButtons);
        bottomButtons.getChildren().addAll(slow,normal,fast,step, play, pause);


        currentTimeline = new Timeline(
                new KeyFrame(Duration.seconds(seconds), e -> {
                    sim.updateGrid();
                    vis.colorGrid();
                })
        );
        currentTimeline.setCycleCount(Animation.INDEFINITE);
        currentTimeline.play();
    }

    public void sim_helper(Simulation sim){
        currentSim = sim;
        Visualizer vis1 = new Visualizer(root, sim);
        currentViz = vis1;
        currentTimeline = new Timeline(
                new KeyFrame(Duration.seconds(seconds), j -> {
                    sim.updateGrid();
                    vis1.colorGrid();
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

    private void simbutton_setup(String buttonName, String filename) throws IOException {
        properties = new GetPropertyValues();
        Button button= new Button(properties.getPropValues(buttonName));
        rightButtons.getChildren().add(button);

        button.setOnAction(e ->{
            //sim = null;
            Simulation sim;
            currentParams = xml_parser.readFile(filename);
            if(filename.equals("segregation.xml"))
            {
                sim = new SegSim(currentParams.get("grid_height"),currentParams.get("grid_width"), WIDTH, HEIGHT, currentParams);

            }else if(filename.equals("fire.xml"))
            {
                sim = new FireSim(currentParams.get("grid_height"),currentParams.get("grid_width"), WIDTH, HEIGHT, currentParams);

            }else if(filename.equals("game_of_life.xml"))
            {
                sim = new GOLSim(currentParams.get("grid_height"),currentParams.get("grid_width"), WIDTH, HEIGHT, currentParams);

            }else if(filename.equals("pred_prey.xml"))
            {
                sim = new PredPreySim(currentParams.get("grid_height"),currentParams.get("grid_width"), WIDTH, HEIGHT, currentParams);

            }else if(filename.equals("percolate.xml"))
            {
                sim = new PercSim(currentParams.get("grid_height"),currentParams.get("grid_width"), WIDTH, HEIGHT, currentParams);

            }else
            {
                sim = new SegSim(currentParams.get("grid_height"),currentParams.get("grid_width"), WIDTH, HEIGHT, currentParams);
            }
            currentSim = sim;
            sim_helper(sim);
        });


    }


    public static void main (String[] args) {
        launch(args);
    }
}
