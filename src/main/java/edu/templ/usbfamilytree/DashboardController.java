package edu.templ.usbfamilytree;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {

    @FXML
    public Label rel_output;
    public AnchorPane anchorpane;

    @FXML
    public void addPerson(ActionEvent event){
        //event for when the add node button is pressed
        System.out.println("User pressed " + event.getSource());
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("person-details.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Add Node");
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deletePerson(ActionEvent event){
        System.out.println("User pressed " + event.getSource());
    }

    public void findRelation(ActionEvent event){
        System.out.println("User pressed " + event.getSource());
    }

    public void onPaneClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.SECONDARY){
            System.out.println("Right Clicked the canvas");
            anchorpane.getChildren().add(createCircle(mouseEvent.getX(), mouseEvent.getY()));
        }
    }

    //creating a circle node, setting its onclick to a simple function
    //along with properties
    private Circle createCircle(double x, double y){
        Circle circle = new Circle();
        circle.setRadius(25);
        circle.setCenterX(x);
        circle.setCenterY(y);
        circle.setFill(Color.DODGERBLUE);
        circle.setStroke(Color.BLACK);
        circle.setOnMouseClicked(this::onCircleClicked);
//        circle.setUserData(new Person("Loser"));
        return circle;
    }

    private void onCircleClicked(MouseEvent event) {
        //event for when a circle is clicked on
        System.out.println(event.getSource());
    }
}