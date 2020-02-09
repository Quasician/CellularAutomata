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
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main extends Application{

    private final static int WIDTH = 500;
    private final static int HEIGHT = 500;
    private Timeline currentTimeline;
    private HashMap<String,Double> currentParams;
    private Simulation currentSim;
    private Visualizer currentViz;
    private double seconds = 1;
    private BorderPane start_root = new BorderPane();
    private BorderPane curr_root = new BorderPane();
    private HBox bottomButtons = new HBox();
    private VBox rightButtons = new VBox();
    private VBox rightButtons2 = new VBox();
    private VBox leftButtons = new VBox();
    private GetPropertyValues properties;
    Stage myStage;
    Scene start_scene;
    Scene curr_scene;



    @Override
    public void start(Stage primaryStage) throws Exception {
        myStage = primaryStage;
        currentParams = xml_parser.readFile("pred_prey.xml");
        properties = new GetPropertyValues();
        myStage.setTitle(properties.getPropValues("title"));
        start_scene = new Scene(start_root, WIDTH + 100, HEIGHT);
        curr_scene = new Scene(curr_root, WIDTH + 100, HEIGHT);
        myStage.setScene(start_scene);
        myStage.show();

        simButtonSetup("buttonSeg", "segregation.xml");
        simButtonSetup("buttonGol", "game_of_life.xml");
        simButtonSetup("buttonPerc", "percolate.xml");
        simButtonSetup("buttonFire", "fire.xml");
        simButtonSetup("buttonPP", "pred_prey.xml");
        simButtonSetup("buttonSugar", "sugar.xml");


        Button fast = makeSpeedButton(properties.getPropValues("buttonFast"), seconds*5);
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

        curr_root.setBottom(bottomButtons);
        start_root.setRight(rightButtons);
        start_root.setLeft(leftButtons);
        bottomButtons.getChildren().addAll(slow,normal,fast,step, play, pause);

    }




    private void simButtonSetup(String buttonName, String filename) throws IOException {
        properties = new GetPropertyValues();
        Button button= new Button(properties.getPropValues(buttonName));
        rightButtons.getChildren().add(button);
        TextField firstValue = new TextField();
        Button enter = new Button("Enter");

        button.setOnAction(e ->{
            Simulation sim;
            currentParams = xml_parser.readFile(filename);
            if(filename.equals("segregation.xml"))
            {
                leftButtons.getChildren().clear();
                leftButtons.getChildren().addAll(firstValue,enter);
                enter.setOnAction(j ->{
                    segSimSetup(firstValue);
                });


            }else if(filename.equals("fire.xml"))
            {
                leftButtons.getChildren().clear();
                leftButtons.getChildren().addAll(firstValue,enter);
                enter.setOnAction(j ->{
                    fireSimSetup(firstValue);
                });

            }else if(filename.equals("game_of_life.xml"))
            {
                leftButtons.getChildren().clear();
                leftButtons.getChildren().addAll(firstValue,enter);
                enter.setOnAction(j ->{
                    golSimSetup(firstValue);
                });

            }else if(filename.equals("pred_prey.xml"))
            {
                leftButtons.getChildren().clear();
                leftButtons.getChildren().addAll(firstValue,enter);
                enter.setOnAction(j ->{
                    predpreySimSetup(firstValue);
                });

            }else if(filename.equals("percolate.xml"))
            {
                leftButtons.getChildren().clear();
                leftButtons.getChildren().addAll(firstValue,enter);
                enter.setOnAction(j ->{
                    percSimSetup(firstValue);
                });

            }else if(filename.equals("sugar.xml"))
            {
                leftButtons.getChildren().clear();
                leftButtons.getChildren().addAll(firstValue,enter);
                enter.setOnAction(j ->{
                    sugarSimSetup(firstValue);
                });

            }

        });
    }

    public void sim_helper(Simulation temp){
        currentSim = temp;
        Button back = new Button("Back");
        rightButtons2.getChildren().clear();
        rightButtons2.getChildren().add(back);
        curr_root.setRight(rightButtons2);
        curr_root.setBottom(bottomButtons);
        back.setOnAction(e -> {
            currentTimeline.stop();
            myStage.setScene(start_scene);
            curr_root = new BorderPane();
            return;
        });

        myStage.setScene(curr_scene);
        Visualizer vis1 = new Visualizer(curr_root, currentSim);
        currentViz = vis1;
        currentTimeline = new Timeline(
                new KeyFrame(Duration.seconds(seconds), j -> {
                    currentSim.updateGrid();
                    vis1.colorGrid();
                    for(Map.Entry<String,Double> entry : currentSim.getAgentNumberMap().entrySet())
                    {
                        System.out.format("key: %s, value: %.0f%n", entry.getKey(), entry.getValue());
                    }
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

    public void percSimSetup(TextField firstValue){
        Simulation sim2;
        if (firstValue.getText().equals("random")){
            sim2 = new PercSim(currentParams.get("grid_height"),currentParams.get("grid_width"), WIDTH, HEIGHT, currentParams);
            sim_helper(sim2);
        }
        else {
            String[] parameters = firstValue.getText().split(",");
            currentParams.put("percentEmpty", Double.parseDouble(parameters[0]));
            currentParams.put("percentBlocked", Double.parseDouble(parameters[1]));
            sim2 = new PercSim(currentParams.get("grid_height"),currentParams.get("grid_width"), WIDTH, HEIGHT, currentParams);
            sim_helper(sim2);
        }
    }

    public void segSimSetup(TextField firstValue){
        Simulation sim2;
        if (firstValue.getText().equals("random")){
            sim2 = new SegSim(currentParams.get("grid_height"),currentParams.get("grid_width"), WIDTH, HEIGHT, currentParams);
            sim_helper(sim2);
        }
        else {
            String[] parameters = firstValue.getText().split(",");
            currentParams.put("probSatisfy", Double.parseDouble(parameters[0]));
            currentParams.put("Percent0", Double.parseDouble(parameters[1]));
            currentParams.put("PercentX", Double.parseDouble(parameters[2]));
            sim2 = new SegSim(currentParams.get("grid_height"),currentParams.get("grid_width"), WIDTH, HEIGHT, currentParams);
            sim_helper(sim2);
        }
    }

    public void fireSimSetup(TextField firstValue){
        Simulation sim2;
        if (firstValue.getText().equals("random")){
            sim2 = new FireSim(currentParams.get("grid_height"),currentParams.get("grid_width"), WIDTH, HEIGHT, currentParams);
            sim_helper(sim2);
        }
        else {
            String[] parameters = firstValue.getText().split(",");
            currentParams.put("probCatch", Double.parseDouble(parameters[0]));
            currentParams.put("probBurning", Double.parseDouble(parameters[1]));
            sim2 = new FireSim(currentParams.get("grid_height"),currentParams.get("grid_width"), WIDTH, HEIGHT, currentParams);
            sim_helper(sim2);
        }
    }

    public void golSimSetup(TextField firstValue){
        Simulation sim2;
        if (firstValue.getText().equals("random")){
            sim2 = new GOLSim(currentParams.get("grid_height"),currentParams.get("grid_width"), WIDTH, HEIGHT, currentParams);
            sim_helper(sim2);
        }
        else {
            String[] parameters = firstValue.getText().split(",");
            currentParams.put("percentAlive", Double.parseDouble(parameters[0]));
            sim2 = new GOLSim(currentParams.get("grid_height"),currentParams.get("grid_width"), WIDTH, HEIGHT, currentParams);
            sim_helper(sim2);
        }
    }

    public void predpreySimSetup(TextField firstValue){
        Simulation sim2;
        if (firstValue.getText().equals("random")){
            sim2 = new PredPreySim(currentParams.get("grid_height"),currentParams.get("grid_width"), WIDTH, HEIGHT, currentParams);
            sim_helper(sim2);
        }
        else {
            String[] parameters = firstValue.getText().split(",");
            currentParams.put("percentFish", Double.parseDouble(parameters[0]));
            currentParams.put("percentSharks", Double.parseDouble(parameters[1]));
            sim2 = new PredPreySim(currentParams.get("grid_height"),currentParams.get("grid_width"), WIDTH, HEIGHT, currentParams);
            sim_helper(sim2);
        }
    }

    public void sugarSimSetup(TextField firstValue){
        Simulation sim2;
        if (firstValue.getText().equals("random")){
            sim2 = new SugarSim(currentParams.get("grid_height"),currentParams.get("grid_width"), WIDTH, HEIGHT, currentParams);
            sim_helper(sim2);
        }
        else {
            String[] parameters = firstValue.getText().split(",");
            currentParams.put("percentAgent", Double.parseDouble(parameters[0]));
            currentParams.put("percentSugarFull", Double.parseDouble(parameters[1]));
            currentParams.put("percentSugarHalf", Double.parseDouble(parameters[2]));
            currentParams.put("percentSugarZero", Double.parseDouble(parameters[3]));
            sim2 = new SugarSim(currentParams.get("grid_height"),currentParams.get("grid_width"), WIDTH, HEIGHT, currentParams);
            sim_helper(sim2);
        }
    }




    public static void main (String[] args) {
        launch(args);
    }
}
