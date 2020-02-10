package View;

import Model.*;
import configuration.*;
import configuration.GetPropertyValues;
import configuration.LoadSim;
import configuration.xml_creator;
import configuration.xml_parser;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private Stage myStage;
    private Scene start_scene;
    private Scene curr_scene;
    private Button load;
    private LoadSim load_sim = new LoadSim(myStage,rightButtons);
    private CategoryAxis xAxis = new CategoryAxis();
    private NumberAxis yAxis = new NumberAxis();
    private LineChart<String,Number> lineChart = new LineChart<String,Number>(xAxis,yAxis);

    @Override
    public void start(Stage primaryStage) throws Exception {
        myStage = primaryStage;
        properties = new GetPropertyValues();
        myStage.setTitle(properties.getPropValues("title"));
        start_scene = new Scene(start_root, WIDTH + 500, HEIGHT);
        curr_scene = new Scene(curr_root, WIDTH + 500, HEIGHT);
        myStage.setScene(start_scene);
        myStage.show();

        simButtonSetup("buttonSeg", "segregation.xml", "standard");
        simButtonSetup("buttonGol", "game_of_life.xml", "standard");
        simButtonSetup("buttonPerc", "percolate.xml", "standard");
        simButtonSetup("buttonFire", "fire.xml", "standard");
        simButtonSetup("buttonPP", "pred_prey.xml", "standard");
        simButtonSetup("buttonSugar", "sugar.xml", "standard");

        Button fast = makeSpeedButton(properties.getPropValues("buttonFast"), seconds*5);
        Button normal = makeSpeedButton(properties.getPropValues("buttonNormal"), seconds);
        Button slow = makeSpeedButton(properties.getPropValues("buttonSlow"), seconds*0.5);
        Button play = makeSpeedButton(properties.getPropValues("buttonPlay"), seconds);
        Button pause = makeSpeedButton(properties.getPropValues("buttonPause"), 0.0);

        load = load_sim.create_button();

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

    private void simButtonSetup(String buttonName, String filename, String file_type) throws IOException {
        properties = new GetPropertyValues();
        Button button= new Button(properties.getPropValues(buttonName));
        rightButtons.getChildren().add(button);
        TextField firstValue = new TextField();
        Button enter = new Button("Enter");

        button.setOnAction(e ->{
            Simulation sim;
            currentParams = xml_parser.readFile(filename);
            //String type_tag = xml_parser.get_type();
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
        Button save = new Button("Save");
        rightButtons2.getChildren().clear();
        rightButtons2.getChildren().add(back);
        rightButtons2.getChildren().add(save);
        rightButtons2.getChildren().add(lineChart);
        lineChart.setPrefSize(500,500);
        curr_root.setBottom(bottomButtons);
        curr_root.setRight(rightButtons2);

        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss:SS");

        back.setOnAction(e -> {
            currentTimeline.stop();
            myStage.setScene(start_scene);
            lineChart.getData().clear();
            curr_root.getChildren().clear();
            return;
        });

        save.setOnAction(e -> {
            xml_creator write = new xml_creator(currentSim);
            return;
        });

        myStage.setScene(curr_scene);
        Visualizer vis1 = new Visualizer(curr_root, currentSim);
        currentViz = vis1;
        xAxis.setAnimated(false);
        yAxis.setAnimated(false);
        XYChart.Series[] data_array = chartArray(currentSim.getAgentNumberMap().entrySet().size());

        currentTimeline = new Timeline(
                new KeyFrame(Duration.seconds(seconds), j -> {
                    currentSim.updateGrid();
                    checkCustomSim();
                    vis1.colorGrid();
                    int count = 0;
                    Date now = new Date();
                    for(Map.Entry<String,Double> entry : currentSim.getAgentNumberMap().entrySet())
                    {
                        System.out.format("key: %s, value: %.0f%n", entry.getKey(), entry.getValue());
                        data_array[count].getData().add(new XYChart.Data(simpleDateFormat.format(now), entry.getValue()));
                        data_array[count].setName(entry.getKey());
                        count += 1;
                    }
                    count = 0;
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
            sim2 = new PercSim( WIDTH, HEIGHT, currentParams);
            sim_helper(sim2);
        }
        else {
            String[] parameters = firstValue.getText().split(",");
            currentParams.put("percentEmpty", Double.parseDouble(parameters[0]));
            currentParams.put("percentBlocked", Double.parseDouble(parameters[1]));
            sim2 = new PercSim( WIDTH, HEIGHT, currentParams);
            sim_helper(sim2);
        }
    }

    public void segSimSetup(TextField firstValue){
        Simulation sim2;
        if (firstValue.getText().equals("random")){
            sim2 = new SegSim( WIDTH, HEIGHT, currentParams);
            sim_helper(sim2);
        }
        else {
            String[] parameters = firstValue.getText().split(",");
            currentParams.put("probSatisfy", Double.parseDouble(parameters[0]));
            currentParams.put("Percent0", Double.parseDouble(parameters[1]));
            currentParams.put("PercentX", Double.parseDouble(parameters[2]));
            sim2 = new SegSim( WIDTH, HEIGHT, currentParams);
            sim_helper(sim2);
        }
    }

    public void fireSimSetup(TextField firstValue){
        Simulation sim2;
        if (firstValue.getText().equals("random")){
            sim2 = new FireSim( WIDTH, HEIGHT, currentParams);
            sim_helper(sim2);
        }
        else {
            String[] parameters = firstValue.getText().split(",");
            currentParams.put("probCatch", Double.parseDouble(parameters[0]));
            currentParams.put("probBurning", Double.parseDouble(parameters[1]));
            sim2 = new FireSim( WIDTH, HEIGHT, currentParams);
            sim_helper(sim2);
        }
    }

    public void golSimSetup(TextField firstValue){
        Simulation sim2;
        if (firstValue.getText().equals("random")){
            sim2 = new GOLSim( WIDTH, HEIGHT, currentParams);
            sim_helper(sim2);
        }
        else {
            String[] parameters = firstValue.getText().split(",");
            currentParams.put("percentAlive", Double.parseDouble(parameters[0]));
            sim2 = new GOLSim( WIDTH, HEIGHT, currentParams);
            sim_helper(sim2);
        }
    }

    public void predpreySimSetup(TextField firstValue){
        Simulation sim2;
        if (firstValue.getText().equals("random")){
            sim2 = new PredPreySim(WIDTH, HEIGHT, currentParams);
            sim_helper(sim2);
        }
        else {
            String[] parameters = firstValue.getText().split(",");
            currentParams.put("percentFish", Double.parseDouble(parameters[0]));
            currentParams.put("percentSharks", Double.parseDouble(parameters[1]));
            sim2 = new PredPreySim(WIDTH, HEIGHT, currentParams);
            sim_helper(sim2);
        }
    }

    public void sugarSimSetup(TextField firstValue){
        Simulation sim2;
        if (firstValue.getText().equals("random")){
            sim2 = new SugarSim( WIDTH, HEIGHT, currentParams);
            sim_helper(sim2);
        }
        else {
            String[] parameters = firstValue.getText().split(",");
            currentParams.put("percentAgent", Double.parseDouble(parameters[0]));
            currentParams.put("percentSugarFull", Double.parseDouble(parameters[1]));
            currentParams.put("percentSugarHalf", Double.parseDouble(parameters[2]));
            currentParams.put("percentSugarZero", Double.parseDouble(parameters[3]));
            sim2 = new SugarSim(WIDTH, HEIGHT, currentParams);
            sim_helper(sim2);
        }
    }

    public XYChart.Series[] chartArray(int size){
        XYChart.Series[] chart_data = new XYChart.Series[size];
        for (int i = 0; i<size; i++){
            XYChart.Series temp = new XYChart.Series();
            chart_data[i] = temp;
            lineChart.getData().add(temp);
        }
        return chart_data;
    }

    private void checkCustomSim(){
        if (load_sim.is_clicked() == true){
            sim_helper(load_sim.getSim());
        }
        load_sim.set_clicked(false);
    }

    public static void main (String[] args) {
        launch(args);
    }
}
