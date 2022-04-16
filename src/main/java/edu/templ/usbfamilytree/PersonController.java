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
    public Button chooseImageButton;
    public ImageView imageView;
    private String name, birthday, occupation, eyeColor, height, filePath;
    static Scene scene;
    private FileChooser fileChooser;
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
    @FXML
    TextField eyeColorTextField;
    @FXML
    TextField heightTextField;


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
    }
    @FXML
    private void submit(ActionEvent event){
        if(nameTextField.getText().isBlank()){
            warningLabel.setVisible(true);
        }else {
            name = nameTextField.getText();
            if(birthdayDatePicker.getValue() != null){ birthday = birthdayDatePicker.getValue().toString();}
            occupation = !occupationTextField.getText().isEmpty() ? occupationTextField.getText() : "";
            eyeColor = eyeColorTextField.getText();
            height = heightTextField.getText();
            if(filePath == null) {filePath = "C:\\Users\\Rosal\\Desktop\\SoftDes2022\\USBFamilyTree\\src\\main\\resources\\saved_images\\no_image.jpg";}
            Stage stage = (Stage) submitButton.getScene().getWindow();
            stage.close();
        }
    }

    public void saveImage(MouseEvent mouseEvent) {
        File file = fileChooser.showOpenDialog(new Stage());
        if(file != null){
            System.out.println(file.toString());
            imageView.setImage(new Image(file.toString()));
            filePath = file.toString();
        }
    }
}
