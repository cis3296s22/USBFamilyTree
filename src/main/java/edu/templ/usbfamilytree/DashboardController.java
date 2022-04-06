package edu.templ.usbfamilytree;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class DashboardController {


    public double xVal, yVal;
    @FXML
    public Label rel_output;
    public AnchorPane anchorpane;
    public ContextMenu canvasMenu;
    private String graphJson = "";
    private Graph graph;
    public DashboardController(){
        try {
            System.out.println("Graph file path: " + Settings.graphPath);
            File file = new File(Settings.graphPath);
            if(!file.exists()) {
                file.createNewFile();

            }
            else {
                graphJson = FileUtils.ReadFile(file.getPath());
                if(!graphJson.isEmpty() && graphJson != null)
                {
                    graph = FileUtils.<Graph>fromJson(graphJson, Graph.class); // if it fails
                    drawGraph(graph);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML //called when scene is first created in memory
    public void initialize() {
        //populates a menu
        populateCanvasMenu();
        if(graph != null) drawGraph(graph);
        rel_output.setText("Welcome to USBFamilyTree");
    }
    private void drawGraph(Graph graph) {
        // draw based off graph
        System.out.println("Pretend drawing the graph");
    }

    //function that populates a menu
    private void populateCanvasMenu() {
        //initiate a new contextMenu
        canvasMenu = new ContextMenu();
        //initiate a new menu item
        MenuItem item1 = new MenuItem();
        //fill in its text
        item1.setText("Add a new person");
        //set its onclick event
        item1.setOnAction(actionEvent -> {
            showNodeCreationStage();
        });

        MenuItem item2 = new MenuItem();
        item2.setText("Add a new edge");
        item2.setOnAction(actionEvent -> {
            System.out.println("Item 2 was pressed");
        });

        canvasMenu.getItems().addAll(item1, item2);
    }

    public void onMainScreenClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.SECONDARY){

            System.out.println("Right Clicked the canvas");
            xVal = mouseEvent.getX();
            yVal = mouseEvent.getY();
            canvasMenu.show(anchorpane, mouseEvent.getScreenX(), mouseEvent.getScreenY());
        }

        if (mouseEvent.getButton() == MouseButton.PRIMARY){
            if(canvasMenu.isShowing()){
                canvasMenu.hide();
            }
        }
    }

    private void showNodeCreationStage(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("person-details.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Add Node");
            stage.setOnCloseRequest(windowEvent ->{
                System.out.println("in onclose: " + windowEvent.getEventType());
            });
            stage.setOnHidden(windowEvent -> {
                System.out.println("The stage was closed");
                //will add a circle no matter what @_@
                System.out.println("in onhidden: " + windowEvent.getEventType());
                anchorpane.getChildren().add(createCircle());
            });
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    //creating a circle node, setting its onclick to a simple function
    //along with properties
    private Circle createCircle(){
        Circle circle = new Circle();
        circle.setRadius(30);
        circle.setCenterX(xVal);
        circle.setCenterY(yVal);
        circle.setFill(Color.SILVER);
        circle.setStroke(Color.BLACK);
        circle.setOnMouseClicked(this::onCircleClicked);
        return circle;
    }

    private void onCircleClicked(MouseEvent event) {
        //event for when a circle is clicked on
        System.out.println(event.getSource());
    }
}