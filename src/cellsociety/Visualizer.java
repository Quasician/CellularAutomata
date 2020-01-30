package cellsociety;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Visualizer {

    private int visRow, visCol;

    public Visualizer(int row, int col) {
        this.visCol = col;
        this.visRow = row;
    }

    public void initialize() {
        /**
         * Display the current states of the 2D grid and
         * animate the simulation from its initial state
         * indefinitely until the user stops it.
         **/
    }

    public void newConfig(/** some action **/) {
        Parent root;
        Stage stage;

        if(/** some action triggered **/) {
            stage = /** new stage **/;
            root = /** new root **/;

            Scene scene = new Scene(root);
            stage.setTitle("temp title");
            stage.setScene(scene);
            stage.show();
        }
    }
}
