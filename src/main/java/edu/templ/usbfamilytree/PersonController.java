package edu.templ.usbfamilytree;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PersonController {
    public Person person;
    static Scene scene;
    @FXML
    Button submitButton;
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
        stage.setTitle("Enter Player Information");
        stage.setScene(scene);
        stage.showAndWait();
        return new Person(nameTextField.getText() , birthdayDatePicker.getValue().toString(), occupationTextField.getText());
    }
    @FXML
    private void submit(ActionEvent event){
        Stage stage = (Stage) submitButton.getScene().getWindow();
        stage.close();
    }
}
