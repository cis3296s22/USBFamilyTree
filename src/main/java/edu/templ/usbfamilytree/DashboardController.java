package edu.templ.usbfamilytree;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class DashboardController {

    public ContextMenu canvasMenu;  //Context menu used for when right clicking on the screen to add a node
    private double xVal, yVal;  //defined variables for saving location of last screen click
    private Label selectedLabel = null; //defined variable for saving currently selected label

    private String graphJson = "";
    private Graph graph;

    @FXML
    public Label rel_output;  //relationship output
    public Label name_label;  //name label reference
    public Label occ_label;   //occupation label reference
    public Label dob_label;   //Date of birth label reference
    public Button mediaButton;  //media button reference
    public Label eye_label;     //eye color label reference
    public Label height_label;  //height label reference
    public ImageView imageView; //image container reference
    public AnchorPane anchorpane;   //reference to anchor pane (area where we are adding nodes)
    public ToggleButton editTButton; //Reference to toggle button in scene

    //constructor (called before initialize)
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
                else
                {
                    graph = new Graph();

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

    //function that populates a ContextMenu
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

    private void drawParentConnection(Label label) {
        // marital only?
        //align parents to left YLayoutlocation
        Node node1 = (Node)selectedLabel.getUserData(); // parent 1
        Node node2 = (Node)label.getUserData(); // parent 2
        graph.addNewEdge(node1.id, node2.id, Edge.Relationship.marital);
        ParentContainer parentContainer = new ParentContainer();
        parentContainer.addParent(node1);
        parentContainer.addParent(node2);

        System.out.println(FileUtils.toJson(graph));
        label.setLayoutY(selectedLabel.getLayoutY());

        //Create new line (marriage/parents line only horizontal)
        double y = selectedLabel.getLayoutY()+Settings.NAME_LABEL_OFFSET+ Settings.LABEL_HEIGHT/2;
        double startX = selectedLabel.getLayoutX()+Settings.LABEL_WIDTH;
        label.setLayoutX(startX + Settings.MARITAL_EDGE_LENGTH);
        double endX = label.getLayoutX();

        Line line = new Line(startX, y, endX, y);
        line.setStrokeWidth(2);
        line.setCursor(Cursor.HAND);
        line.setOnMouseClicked(this::onLineClicked);
        line.setUserData(parentContainer);

        //add it to parent hierarchy
        anchorpane.getChildren().add(line);

        //deselect both
        setUnselected(selectedLabel);
    }

    private void onLineClicked(MouseEvent event) {
        if(editTButton.isSelected()){
            if(selectedLabel != null){ // the child
                //extract current line
                Line selectedLine = (Line)event.getSource();
                //extract parent container from line
                ParentContainer parentContainer = (ParentContainer) selectedLine.getUserData();
                Node child = (Node)selectedLabel.getUserData();

                //iterating through parents in parentContainer and adding an edge between parents and new child
                for (Node parent: parentContainer.parents) {
                    graph.addNewEdge(parent.id, child.id, Edge.Relationship.ancestor);
                }
                //printing out graph
                System.out.println(FileUtils.toJson(graph));
                //current children #
                int childNum = parentContainer.getChildIndex();

                double lineMiddleX = (selectedLine.getEndX() - selectedLine.getStartX())/2;
                double labelCenterX = (lineMiddleX + selectedLine.getStartX() - Settings.LABEL_WIDTH/2);
                //move label to corresponding location
                selectedLabel.setLayoutY(selectedLine.getStartY() + Settings.CHILD_OFFSET);
                if(childNum % 2 == 0){
                    selectedLabel.setLayoutX((labelCenterX + ((Settings.LABEL_WIDTH + Settings.CHILDREN_PADDING) * (childNum/2.0))));
                }
                else if (childNum % 2 == 1){
                    selectedLabel.setLayoutX((labelCenterX - ((Settings.LABEL_WIDTH + Settings.CHILDREN_PADDING) * ((childNum+1)/2.0))));
                }
                //get starting points for the line (top-center of label)
                double startY = selectedLabel.getLayoutY();
                double startX = selectedLabel.getLayoutX()+ Settings.LABEL_WIDTH/2;
                //This is where the line is (end is the line that connects the parents
                double endX = (lineMiddleX + selectedLine.getStartX());
                double endY = selectedLine.getStartY();

                Line line = new Line(startX, startY, endX, endY);
                line.setStrokeWidth(2);
                anchorpane.getChildren().add(line);
                parentContainer.setChildIndex(parentContainer.getChildIndex()+1);
                selectedLine.setUserData(parentContainer);
                setUnselected(selectedLabel);
            }
        }
    }

    private void showNodeCreationStage(){
        try {
            PersonController controller = PersonController.create();
            //returned from creation stage, everything except name can be null
            Person p = controller.Show();
            Node node =  graph.addNode(p);
            //if in the return the name is null (user clicked on X button to leave the screen, don't create a new view)
            if (p.name != null) {anchorpane.getChildren().add(createLabel(node));}
            System.out.println(FileUtils.toJson(node));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setSelected(Label label){
        label.setBackground(new Background(new BackgroundFill(Color.DODGERBLUE, new CornerRadii(0), new Insets(0))));
    }

    private void setUnselected(Label label){
        label.setBackground(new Background(new BackgroundFill(Color.LIGHTSTEELBLUE, new CornerRadii(0), new Insets(0))));
        selectedLabel = null;
    }

    private Label createLabel(Node node){
        //creating label, and setting text inside of label to person's name
        Label label = new Label(node.person.name);

        //Adjustments to the labels look
        label.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
        label.setPrefWidth(Settings.LABEL_WIDTH);
        label.setPrefHeight(Settings.LABEL_HEIGHT);
        label.setFont(Font.font(16));
        label.setTextFill(Color.BLACK);
        label.setBackground(new Background(new BackgroundFill(Color.LIGHTSTEELBLUE, new CornerRadii(0), new Insets(0))));
        label.setAlignment(Pos.BASELINE_CENTER);
        label.setLayoutX(xVal-(Settings.LABEL_WIDTH/2));
        label.setLayoutY(yVal-(Settings.LABEL_HEIGHT/2));

        //onclick function
        label.setOnMouseClicked(this::onLabelClicked);
        //setting data to node passed on from showNodeCreationStage
        label.setUserData(node);
        return label;
    }

    private void onLabelClicked(MouseEvent event) {
        //IN VIEW MODE
        if(!editTButton.isSelected()) {
            if (selectedLabel == null) {
                //pull what was clicked on and set it to currently selected label
                selectedLabel = (Label) event.getSource();
                setSelected(selectedLabel);
            } else {
                Label newLabel = (Label) event.getSource();
                if (selectedLabel == newLabel) {
                    //if the user clicks on the same label, we unselect
                    setUnselected(selectedLabel);
                }
                else{
                    setUnselected(selectedLabel);
                    setSelected(newLabel);
                    selectedLabel = newLabel;
                }
            }
            updateSidePanel();
        }
        //IN EDIT MODE
        else {
            if (selectedLabel == null) {
                //pull what was clicked on and set it to currently selected label
                selectedLabel = (Label) event.getSource();
                setSelected(selectedLabel);
            } else {
                Label newLabel = (Label) event.getSource();
                if (selectedLabel == newLabel) {
                    //if the user clicks on the same label, we unselect
                    setUnselected(selectedLabel);
                }
                else{
                    drawParentConnection(newLabel);
                }
            }
        }
    }

    private void updateSidePanel() {
        if(selectedLabel != null) {
            Node node = (Node)selectedLabel.getUserData();
            Person currentPerson = node.person;
            name_label.setText(currentPerson.name);
            occ_label.setText(currentPerson.occupation);
            dob_label.setText(currentPerson.dateOfBirth);
            eye_label.setText(currentPerson.eyeColor);
            height_label.setText(currentPerson.height);
            imageView.setImage(new Image(currentPerson.filePath));
            mediaButton.setOnMouseClicked(event -> {

            });
        }
        else{
            name_label.setText("");
            occ_label.setText("");
            dob_label.setText("");
            eye_label.setText("");
            height_label.setText("");
            imageView.setImage(new Image("C:\\Users\\Rosal\\Desktop\\SoftDes2022\\USBFamilyTree\\src\\main\\resources\\saved_images\\no_image.jpg"));
            mediaButton.setOnMouseClicked(event -> {
                //do nothing
            });
        }
    }

    public void toggleButtonPressed(ActionEvent event) {
        if(selectedLabel != null){
            setUnselected(selectedLabel);
            updateSidePanel();
        }
    }
}