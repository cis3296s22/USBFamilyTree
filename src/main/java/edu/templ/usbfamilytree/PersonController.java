package edu.templ.usbfamilytree;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.time.format.DateTimeFormatter;

public class PersonController {
    public Button chooseImageButton;        //reference to image button in FXML
    public ImageView imageView;             //reference to ImageView in FXML
    public Button submitButton;             //reference to submit button in FXML
    public Label warningLabel;              //reference to label that displayed error in FXML
    public TextField nameTextField;         //reference to name TextField in FXML
    public DatePicker birthdayDatePicker;   //reference to DatePicker object in FXML
    public TextField occupationTextField;   //reference to occupied TextField in FXML
    public TextField eyeColorTextField;     //reference to eye color TextField in FXML
    public TextField heightTextField;       //reference to height TextField in FXML

    //Strings that will contain information passed into
    private String name, birthday, occupation, eyeColor, height, filePath;
    static Scene scene;
    private FileChooser fileChooser;
    private File file;


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
        return new Person(name, birthday, occupation, eyeColor, height, filePath);
    }

    @FXML
    public void initialize(){
        fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("src/main/resources/"));
        file = new File("src/main/resources/saved_images/no_image.jpg");
    }
    @FXML
    private void submit(ActionEvent event){
        if(nameTextField.getText().isBlank()){
            warningLabel.setVisible(true);
        }else {
            name = nameTextField.getText();
            if(birthdayDatePicker.getEditor().getText() != null){ birthday = birthdayDatePicker.getEditor().getText();}
            occupation = !occupationTextField.getText().isEmpty() ? occupationTextField.getText() : "";
            eyeColor = eyeColorTextField.getText();
            height = heightTextField.getText();

            filePath = file.getAbsolutePath();

            Stage stage = (Stage) submitButton.getScene().getWindow();
            stage.close();
        }
    }

    public void saveImage(MouseEvent mouseEvent) {
        File temp = fileChooser.showOpenDialog(new Stage());
        if(temp != null){
            file = temp;
            imageView.setImage(new Image(file.toString()));
            filePath = file.toString();
        }
    }
}
