package edu.templ.usbfamilytree;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class DashboardController {

    @FXML
    public Label rel_output;
    public AnchorPane anchorpane;

    @FXML
    public void addPerson(ActionEvent event){
        //event for when the add node button is pressed
        System.out.println("User pressed " + event.getSource());
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
        return circle;
    }

    private void onCircleClicked(MouseEvent event) {
        System.out.println(event.getSource());
    }
}