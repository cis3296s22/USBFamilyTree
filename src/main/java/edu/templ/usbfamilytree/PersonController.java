package edu.templ.usbfamilytree;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;

public class PersonController {
    private Person person;
    private String name, birthday, occupation;

    static Scene scene;
    @FXML
    Button submitButton;
    @FXML
    Label warningLabel;
    @FXML
    TextField nameTextField;
    @FXML
    DatePicker birthdayDatePicker;
    @FXML
    TextField occupationTextField;



    public static PersonController create() throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("person-details.fxml"));
        scene = new Scene(fxmlLoader.load());
        return fxmlLoader.getController();
    }
    public Person Show() {
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("Enter Person Information");
        stage.setScene(scene);
        stage.showAndWait();
        return new Person(name, birthday, occupation);
    }
    @FXML
    private void submit(ActionEvent event){
        if(nameTextField.getText().isBlank()){
            warningLabel.setVisible(true);
        }else {
            name = nameTextField.getText();
            if(birthdayDatePicker.getValue() != null){ birthday = birthdayDatePicker.getValue().toString();}
            if(!occupationTextField.getText().isBlank()) {occupation = occupationTextField.getText();}
            Stage stage = (Stage) submitButton.getScene().getWindow();
            stage.close();
        }
    }
}
