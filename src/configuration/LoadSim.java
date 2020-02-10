package configuration;

import Model.*;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.PropertyPermission;


public class LoadSim {
    VBox button_box;
    Button load;
    Stage stage;
    File selectedFile;
    private HashMap<String,Double> currentParams;
    private GetPropertyValues properties = new GetPropertyValues();
    private Simulation sim;
    private boolean is_clicked = false;
    private final static int WIDTH = 500;
    private final static int HEIGHT = 500;
    private Simulation dummyGrid;

    public LoadSim(Stage stage, VBox vbox){
        this.button_box = vbox;
        this.stage = stage;
    }

    public Button create_button() throws Exception {
        Button new_button = new Button(properties.getPropValues("buttonLoadSim"));
        load = new_button;
        button_box.getChildren().add(load);
        load.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            selectedFile = fileChooser.showOpenDialog(stage);
            xml_parser parser = new xml_parser();
            currentParams = parser.readSavedFile(selectedFile);
            dummyGrid = parser.getSim();
            System.out.println(parser.getFileType());
            button_action(parser);
            is_clicked = true;
        });
        return load;
    }

    public void button_action(xml_parser parser){
        if (parser.getFileType().equals("fire.xml")){
            sim = new FireSim( WIDTH, HEIGHT, currentParams, dummyGrid);
        }
        else if (parser.getFileType().equals("game_of_life.xml")){
            sim = new GOLSim( WIDTH, HEIGHT, currentParams, dummyGrid);
        }
        else if (parser.getFileType().equals("percolate.xml")){
            sim = new PercSim( WIDTH, HEIGHT, currentParams, dummyGrid);
        }
        else if (parser.getFileType().equals("pred_prey.xml")){
            sim = new PredPreySim( WIDTH, HEIGHT, currentParams, dummyGrid);
        }
        else if (parser.getFileType().equals("segregation.xml")){
            sim = new SegSim( WIDTH, HEIGHT, currentParams, dummyGrid);
        }
        else if (parser.getFileType().equals("sugar.xml")){
            sim = new SugarSim( WIDTH, HEIGHT, currentParams, dummyGrid);
        }
        else if (parser.getFileType().equals("ant.xml")){
            sim = new AntSim(WIDTH, HEIGHT, currentParams);
        }
        else if (parser.getFileType().equals("rps.xml")){
            sim = new RPSSim(WIDTH, HEIGHT, currentParams);
        }
    }

    public boolean is_clicked() {
        return is_clicked;
    }

    public void set_clicked(boolean is_clicked) {
        this.is_clicked = is_clicked;
    }

    public Simulation getSim() {
        return sim;
    }
}
