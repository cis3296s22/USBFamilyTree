package edu.templ.usbfamilytree;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PersonController {
    public Person person;
    @FXML
    Button submitButton;
    @FXML
    TextField nameTextField;
    @FXML
    DatePicker birthdayDatePicker;
    @FXML
    TextField occupationTextField;
    @FXML
    private void submit(ActionEvent event){
        String name = nameTextField.getText();
        String birthday = birthdayDatePicker.getValue().toString();
        String occupation = occupationTextField.getText();
        person = new Person(name, birthday, occupation);
        Stage stage = (Stage) submitButton.getScene().getWindow();
        stage.close();
    }
}
