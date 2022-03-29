package edu.templ.usbfamilytree;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PersonController {
    @FXML
    Button submitButton;

    @FXML
    private void submit(ActionEvent event){
        Stage stage = (Stage) submitButton.getScene().getWindow();
        stage.close();
    }
}
