package cellsociety;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Visualizer {

    private int visRow, visCol;

    public Visualizer(int row, int col) {
        this.visCol = col;
        this.visRow = row;

        VBox temp = new VBox();
        Scene scene = new Scene(temp);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }


}
