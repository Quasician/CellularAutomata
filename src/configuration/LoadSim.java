package configuration;

import Model.*;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.HashMap;

public class LoadSim {
    VBox button_box;
    Button load;
    Stage stage;
    File selectedFile;
    private HashMap<String,Double> currentParams;
    private String type_tag;
    private Simulation sim;
    private boolean is_clicked = false;
    private final static int WIDTH = 500;
    private final static int HEIGHT = 500;

    public LoadSim(Stage stage, VBox vbox){
        this.button_box = vbox;
        this.stage = stage;
    }

    public Button create_button(){
        Button new_button = new Button("Load Custom Sim");
        load = new_button;
        button_box.getChildren().add(load);
        load.setOnAction(e -> {
            is_clicked = true;
            FileChooser fileChooser = new FileChooser();
            selectedFile = fileChooser.showOpenDialog(stage);
            currentParams = xml_parser.readSavedFile(selectedFile);
        });
        return load;
    }

    public void button_action(){
        if (xml_parser.getFileType().equals("fire.xml")){
            sim = new FireSim( WIDTH, HEIGHT, currentParams);
        }
        else if (xml_parser.getFileType().equals("game_of_life.xml")){
            sim = new GOLSim( WIDTH, HEIGHT, currentParams);
        }
        else if (xml_parser.getFileType().equals("percolate.xml")){
            sim = new PercSim( WIDTH, HEIGHT, currentParams);
        }
        else if (xml_parser.getFileType().equals("pred_prey.xml")){
            sim = new PredPreySim( WIDTH, HEIGHT, currentParams);
        }
        else if (xml_parser.getFileType().equals("segregation.xml")){
            sim = new SegSim( WIDTH, HEIGHT, currentParams);
        }
        else if (xml_parser.getFileType().equals("sugar.xml")){
            sim = new SugarSim( WIDTH, HEIGHT, currentParams);
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
