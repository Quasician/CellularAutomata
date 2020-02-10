package configuration;

import Model.Simulation;
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

    public LoadSim(Stage stage, VBox vbox){
        this.button_box = vbox;
        this.stage = stage;
        currentParams = xml_parser.readFile(selectedFile.toString());
        type_tag = xml_parser.getFileType();
    }

    public Button create_button(){
        Button new_button = new Button("Load Custom Sim");
        load = new_button;
        button_box.getChildren().add(load);
        load.setOnAction(e -> {
            is_clicked = true;
            FileChooser fileChooser = new FileChooser();
            selectedFile = fileChooser.showOpenDialog(stage);
        });
        return load;
    }


    public void button_action(){




    }

    public boolean is_clicked() {
        return is_clicked;
    }

    public void set_clicked(boolean is_clicked) {
        this.is_clicked = is_clicked;
    }
}
