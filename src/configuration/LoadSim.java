package configuration;

/**
 * Purpose: To load in a custom simulation that has been saved by the user
 * Assumptions: that the user has not added any new tags or changed the formatting of the XML file
 * Dependencies: it is dependent on the simulation class and the xml parser class.
 * an example of how to use it
 *  LoadSim load = new LoadSim(stage,Vbox);
 *  This creates a new loadSim object that adds the button the the given Vbox, and uses the stage passed as a parameter
 *  for actions such as opening the file explorer.
 */


import Model.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;


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


    /**
     * Creates button that saves parameters of selected simulation configuration file
     * @return Button that opens file explorer and saves file
     * @throws IOException
     * @throws XMLException
     */
    public Button create_button() throws IOException,XMLException {
        Button new_button = new Button(properties.getPropValues("buttonLoadSim"));
        load = new_button;
        button_box.getChildren().add(load);
        load.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            selectedFile = fileChooser.showOpenDialog(stage);
            xml_parser parser = new xml_parser();
            try {
                currentParams = parser.readSavedFile(selectedFile);
                dummyGrid = parser.getSim();
                System.out.println(parser.getFileType());
                is_clicked = true;
                button_action(parser);
            }catch(NullPointerException j)
            {
                showError("File not selected");
            }
        });
        return load;
    }

    private void button_action(xml_parser parser) throws XMLException{
        try {
            if (parser.getFileType().equals("fire.xml")) {
                sim = new FireSim(WIDTH, HEIGHT, currentParams, dummyGrid);
                return;
            } else if (parser.getFileType().equals("game_of_life.xml")) {
                sim = new GOLSim(WIDTH, HEIGHT, currentParams, dummyGrid);
                return;
            } else if (parser.getFileType().equals("percolate.xml")) {
                sim = new PercSim(WIDTH, HEIGHT, currentParams, dummyGrid);
                return;
            } else if (parser.getFileType().equals("pred_prey.xml")) {
                sim = new PredPreySim(WIDTH, HEIGHT, currentParams, dummyGrid);
                return;
            } else if (parser.getFileType().equals("segregation.xml")) {
                sim = new SegSim(WIDTH, HEIGHT, currentParams, dummyGrid);
                return;
            } else if (parser.getFileType().equals("sugar.xml")) {
                sim = new SugarSim(WIDTH, HEIGHT, currentParams, dummyGrid);
                return;
            } else if (parser.getFileType().equals("ant.xml")) {
                sim = new AntSim(WIDTH, HEIGHT, currentParams);
                return;
            } else if (parser.getFileType().equals("rps.xml")) {
                sim = new RPSSim(WIDTH, HEIGHT, currentParams, dummyGrid);
                return;
            }
            throw new XMLException("Wrong File", parser.getFileType());
        }catch (XMLException e)
        {
            showError(e.getMessage());
            is_clicked = false;
        }
    }


    /**
     * Getter method
     * @return If the button is clicked or not
     */
    public boolean is_clicked() {
        return is_clicked;
    }

    /**
     * Setter method
     */
    public void set_clicked(boolean is_clicked) {
        this.is_clicked = is_clicked;
    }

    /**
     * Getter method
     * @return Simulation created by loading custom configuration
     */

    public Simulation getSim() {
        return sim;
    }

    private void showError(String mes)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(mes);
        alert.showAndWait();
    }
}
