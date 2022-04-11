package edu.templ.usbfamilytree;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;

import java.io.File;
import java.io.IOException;

public class DashboardController {


    private double xVal, yVal;

    private Label firstPerson = null, secondPerson = null;

    @FXML
    public Label rel_output;
    @FXML
    public Label name_label;
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
                    graph = FileUtils.fromJson(graphJson, Graph.class); // if it fails
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

        canvasMenu.getItems().addAll(item1);
    }

    public void onMainScreenClicked(MouseEvent mouseEvent) {
        //whenever the screen(anchor-pane) is clicked on this event is run

        if (mouseEvent.getButton() == MouseButton.PRIMARY){
            //if the canvas is showing remove it from the screen
            if(canvasMenu.isShowing()){
                canvasMenu.hide();
            }
        }else if(mouseEvent.getButton() == MouseButton.SECONDARY){
            //save the location of where the user clicked on the anchor pane
            xVal = mouseEvent.getX();
            yVal = mouseEvent.getY();
            //show the menu on the location clicked of the screen
            canvasMenu.show(anchorpane, mouseEvent.getScreenX(), mouseEvent.getScreenY());
        }
    }

    private void drawParentConnection() {
        //align parents to left YLayoutlocation
        secondPerson.setLayoutY(firstPerson.getLayoutY());

        //Create new line (marriage/parents line only horizontal)
        Line line = new Line();
        double yCoord = firstPerson.getLayoutY()+Settings.NAME_LABEL_OFFSET+ Settings.LABEL_HEIGHT/2;
        double startXCoord = firstPerson.getLayoutX()+Settings.LABEL_WIDTH;
        double endXCoord = secondPerson.getLayoutX();

        line.setStartX(startXCoord);
        line.setStartY(yCoord);
        line.setEndX(endXCoord);
        line.setEndY(yCoord);
        line.setStrokeWidth(2);
        //add it to parent hierarchy
        anchorpane.getChildren().add(line);

        //deselect both
        setUnselected(firstPerson);
        setUnselected(secondPerson);
        //reset selected nodes
        firstPerson = null;
        secondPerson = null;
    }

    private void showNodeCreationStage(){
        try {
            PersonController controller = PersonController.create();
            //returned from creation stage, everything except name can be null
            Person p = controller.Show();
            Node node = new Node(p,1);
            //if in the return the name is null (user clicked on X button to leave the screen, don't create a new view)
            if(p.name != null) anchorpane.getChildren().add(createLabel(p.name));
            System.out.println(FileUtils.toJson(p));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setSelected(Label label){
        label.setBackground(new Background(new BackgroundFill(Color.DODGERBLUE, new CornerRadii(0), new Insets(0))));
    }

    private void setUnselected(Label label){
        label.setBackground(new Background(new BackgroundFill(Color.SILVER, new CornerRadii(0), new Insets(0))));
    }

    private Label createLabel(String name){
        Label label = new Label();
        label.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
        label.setPrefWidth(Settings.LABEL_WIDTH);
        label.setPrefHeight(Settings.LABEL_HEIGHT);
        label.setFont(Font.font(16));
        label.setTextFill(Color.BLACK);
        label.setBackground(new Background(new BackgroundFill(Color.SILVER, new CornerRadii(0), new Insets(0))));
        label.setAlignment(Pos.BASELINE_CENTER);

        label.setOnMouseClicked(this::onLabelClicked);
        label.setText(name);
        label.setLayoutX(xVal-(Settings.LABEL_WIDTH/2));
        label.setLayoutY(yVal-(Settings.LABEL_HEIGHT/2));
        return label;
    }

    private void onLabelClicked(MouseEvent event) {
        //clicking on a label selects it for viewing & enables connection to another node
        if(firstPerson == null) {
            //pull what was clicked on and set it to currently selected label
            firstPerson = (Label) event.getSource();
            setSelected(firstPerson);
        }else{
            secondPerson = (Label) event.getSource();
            if(firstPerson == secondPerson){
                setUnselected(firstPerson);
                firstPerson = null;
                secondPerson = null;
            }
            else{
                setSelected(secondPerson);
                drawParentConnection();
            }
        }

        //we can start changing the views on the right by looking up a node with the name and displaying the information there!
//        if(firstPerson!= null) {
//            name_label.setText(firstPerson.getText());
//        }
//        else{
//            name_label.setText("");
//        }
    }
}