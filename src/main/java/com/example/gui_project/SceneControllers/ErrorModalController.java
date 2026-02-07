package org.example.teleporti.SceneControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ErrorModalController {

    @FXML
    protected Label label = new Label("");

    @FXML
    public void initialize() {
    }

    @FXML
    public void setMessage(String message) {
        label.setText(message);
    }
}
