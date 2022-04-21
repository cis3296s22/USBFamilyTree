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
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import java.io.File;

/**
 *  Main Controller class that controls all actions performed in the primary stage
 */

public class DashboardController {
    /**
     *  menu that will be displayed to the user after a performed action on the clickable area
     */
    private ContextMenu canvasMenu;
    /**
     *  Graph object used for backbone of application. Allows association between graph and controller class
     */
    private Graph graph;

    private final File file;        //stores reference to default image in project directory
    private Label primarySelected;    //storing reference to a label after it is clicked
    private Label secondLabel;      //storing reference to a label after the first is stored

    //FXML related fields
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
    public Label relative_output; // reference to the closest relative label

    boolean connection = false;
    boolean marriage = false;
    boolean parent = false;
    boolean descendant = false;

    public String[] menuOptions = {"ADD: Child Relationship", "ADD: Parent Relationship", "ADD: Marriage Relationship"};

    /**
     *  Constructor - initializes graph, and file to default image in project directory
     */
    public DashboardController(){
//        System.out.println("Graph file path: " + Settings.graphPath);
        File file1 = new File(Settings.graphPath);
        if(file1.exists()) {
            String graphJson = FileUtils.ReadFile(file1.getPath());
            if(!graphJson.isEmpty()) {
                graph = FileUtils.fromJson(graphJson, Graph.class); // if it fails
                drawGraph(graph);
            }
            else {
                graph = new Graph();
            }
        }
        file = new File("src/main/resources/saved_images/no_image.jpg");
    }

    /**
     *  Called after constructor. Initializes JavaFX components before allowing user to interact with them.
     *  Prevents runtime null errors.
     */
    @FXML
    public void initialize() {
        //populates a menu
        initializeMenu();
        if(graph != null) drawGraph(graph);
    }


    /**
     * Populates the screen with graph object read from a file.
     *
     * @param graph used for redrawing all objects from previously saved graph
     */
    private void drawGraph(Graph graph) {
        // draw based off graph
        System.out.println("Pretend drawing the graph");
    }

    /**
     *  Initializes the context menu defined for the class, and adds menu items to the menu.
     */
    private void initializeMenu() {
        //initiate a new contextMenu
        canvasMenu = new ContextMenu();

        //set its onclick event
        for (String menuOption : menuOptions) {
            MenuItem item = new MenuItem(menuOption);
            item.setId(menuOption);
            item.setOnAction(actionEvent -> menuOptionClicked(actionEvent, item.getId()));
            canvasMenu.getItems().add(item);
        }
    }

    private void menuOptionClicked(ActionEvent event, String id) {
        System.out.println(((MenuItem)event.getSource()).getParentPopup().getOwnerNode());
        //person label that item menu was triggered on
        Label currentLabel = (Label) ((MenuItem)event.getSource()).getParentPopup().getOwnerNode();
        Node node = (Node) currentLabel.getUserData();
        //All of the following methods can only be called on nodes
        if(id.equals(menuOptions[0])){
            //Setup child connection
            System.out.println(menuOptions[0]);
            System.out.println("Setting up child connection");
            descendant = true;
            setSelected(currentLabel);
            primarySelected = currentLabel;
            connection = true;
        }
        else if(id.equals(menuOptions[1])){
            //Setup parent connection
            System.out.println(menuOptions[1]);

        }
        else if(id.equals(menuOptions[2])){
            //setup marriage connection
            if(node.isMarried()){
                System.out.println("cannot add a second marriage connection");
            }
            else {
                System.out.println("Setting up marriage connection");
                marriage = true;
                setSelected(currentLabel);
                primarySelected = currentLabel;
                connection = true;
            }
        }

    }

    /**
     * Event handling method that is executed whenever a user clicks on the main screen.
     * Reference by the FXML file and set to the anchor-pane that is visible to the user.
     *
     * @param mouseEvent is the event that is passed on from user input. Can extract what type of mouseevent was triggered from this.
     */
    @FXML
    private void onMainScreenClicked(MouseEvent mouseEvent) {
        //whenever the screen(anchor-pane) is clicked on this event is run
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                if (canvasMenu.isShowing()) {
                    canvasMenu.hide();
                }

                if(editTButton.isSelected() && mouseEvent.isControlDown()) {
                    //if the canvas is showing remove it from the screen
                    double x = mouseEvent.getX();
                    double y = mouseEvent.getY();
                    showNodeCreationStage(x, y);
                }
            }
    }

    /**
     *  Retrieves data from selected label and label from parameter and adds a new
     *  marital edge relationship in graph. Adds a line connecting both labels to main UI.
     *
     * @param label is a reference to a label that will be moved on the screen.
     */
    private void addMaritalRelationship(Label label) {
        // marital only
        //align parents to left YLayout-location
        Node node1 = (Node) primarySelected.getUserData(); // parent 1
        Node node2 = (Node)label.getUserData(); // parent 2
        graph.addNewEdge(node1.id, node2.id, Edge.Relationship.marital);
        ParentContainer parentContainer = new ParentContainer();
        parentContainer.addParent(node1);
        parentContainer.addParent(node2);

        System.out.println(FileUtils.toJson(graph));
        label.setLayoutY(primarySelected.getLayoutY());

        //Create new line (marriage/parents line only horizontal)
        double y = primarySelected.getLayoutY()+ Settings.LABEL_HEIGHT/2;
        double startX = primarySelected.getLayoutX()+Settings.LABEL_WIDTH;
        label.setLayoutX(startX + Settings.MARITAL_EDGE_LENGTH);
        double endX = label.getLayoutX();

        Line line = new Line(startX, y, endX, y);
        line.setStrokeWidth(4);
        line.setCursor(Cursor.HAND);
        line.setOnMouseClicked(this::onLineClicked);
        line.setUserData(parentContainer);

        //add it to parent hierarchy
        anchorpane.getChildren().add(line);

        //deselect both
        setUnselected(primarySelected);
    }

    /**
     *  Retrieves data from selected label and line from the mouse event parameter and adds new
     *  ancestor edge relationships between parents and child.
     *
     * @param mouseEvent is the event that is passed on from user input. Can extract what type of mouse event was triggered from this.
     */
    private void onLineClicked(MouseEvent mouseEvent) {
        Line selectedLine = (Line)mouseEvent.getSource();
        ParentContainer parentContainer = (ParentContainer) selectedLine.getUserData();
        Node child = (Node) primarySelected.getUserData();


        if(editTButton.isSelected()){
            if(descendant && connection){
                addChildRelationship(child, parentContainer, selectedLine);
                descendant = false;
                connection = false;
            }
        }
    }

    private void addChildRelationship(Node child, ParentContainer parentContainer, Line selectedLine) {
        //iterating through parents in parentContainer and adding an edge between parents and new child
        for (Node parent: parentContainer.getParents()) {
            graph.addNewEdge(parent.id, child.id, Edge.Relationship.ancestor);
        }
        //printing out graph
        System.out.println(FileUtils.toJson(graph));
        //current children #
        int childNum = parentContainer.getChildIndex();

        double lineMiddleX = (selectedLine.getEndX() - selectedLine.getStartX())/2;
        double labelCenterX = (lineMiddleX + selectedLine.getStartX() - Settings.LABEL_WIDTH/2);
        //move label to corresponding location
        primarySelected.setLayoutY(selectedLine.getStartY() + Settings.CHILD_OFFSET);
        if(childNum % 2 == 0){
            primarySelected.setLayoutX((labelCenterX + ((Settings.LABEL_WIDTH + Settings.CHILDREN_PADDING) * (childNum/2.0))));
        }
        else if (childNum % 2 == 1){
            primarySelected.setLayoutX((labelCenterX - ((Settings.LABEL_WIDTH + Settings.CHILDREN_PADDING) * ((childNum+1)/2.0))));
        }
        //get starting points for the line (top-center of label)
        double startY = primarySelected.getLayoutY();
        double startX = primarySelected.getLayoutX()+ Settings.LABEL_WIDTH/2;
        //This is where the line is (end is the line that connects the parents
        double endX = (lineMiddleX + selectedLine.getStartX());
        double endY = selectedLine.getStartY();

        Line line = new Line(startX, startY, endX, endY);
        line.setStrokeWidth(2);
        anchorpane.getChildren().add(line);
        parentContainer.setChildIndex(parentContainer.getChildIndex()+1);
        selectedLine.setUserData(parentContainer);
        setUnselected(primarySelected);
    }

    /**
     *  Opens a new stage for user input of a new family member and adds the new member to a node, then to the graph object defined in this class.
     *
     */
    private void showNodeCreationStage(double x, double y){
        try {
            PersonController controller = new PersonController().create();
            //returned from creation stage, everything except name can be null
            Person p = controller.Show();
            if(p.name != null){
                Node node =  graph.addNode(p);
                anchorpane.getChildren().add(createLabel(node, x, y));
                System.out.println(FileUtils.toJson(node));
            }
            //if in the return the name is null (user clicked on X button to leave the screen, don't create a new view)
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  Changes the background color of the label passed in the parameter
     * @param label reference to a label
     */
    private void setSelected(Label label){
        label.setBackground(new Background(new BackgroundFill(Color.DODGERBLUE, new CornerRadii(0), new Insets(0))));
    }

    /**
     *  Changes the background color of the label passed in the parameter
     * @param label reference to a label
     */
    private void setUnselected(Label label){
        label.setBackground(new Background(new BackgroundFill(Color.LIGHTSTEELBLUE, new CornerRadii(0), new Insets(0))));
        if (label == primarySelected) {
            primarySelected = null;}
        else secondLabel = null;
    }

    /**
     * Creates a new clickable label using the information from the passed parameter node
     *
     * @param node contains information retrieved from person controller
     * @return returns a Label that has its own custom properties and sets its user data to the information passed from node
     */
    private Label createLabel(Node node, double x, double y){
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
        label.setLayoutX(x-(Settings.LABEL_WIDTH/2));
        label.setLayoutY(y-(Settings.LABEL_HEIGHT/2));

        //onclick function
        label.setOnMouseClicked(this::onLabelClicked);
        //setting data to node passed on from showNodeCreationStage
        label.setUserData(node);
        return label;
    }

    /**
     * Event that is run when a label is clicked, controls the currently selected label and the second label selected.
     *
     * @param mouseEvent is the event that is passed on from user input. Can extract what type of mouse event was triggered from this.
     */
    private void onLabelClicked(MouseEvent mouseEvent) {
        Label currentLabel = (Label) mouseEvent.getSource();
        Node node = (Node)currentLabel.getUserData();
        //IN VIEW MODE
        if(!editTButton.isSelected()) {

            if(mouseEvent.getButton() == MouseButton.PRIMARY){
                if (primarySelected == null) {
                    //pull what was clicked on and set it to currently selected label
                    primarySelected = currentLabel;
                    setSelected(currentLabel);
                }
            }
            updateSidePanel();
        }
        //IN EDIT MODE
        else {
            if(mouseEvent.getButton() == MouseButton.SECONDARY){
                canvasMenu.show(currentLabel, mouseEvent.getScreenX(), mouseEvent.getScreenY());
            }
            if(mouseEvent.getButton() == MouseButton.PRIMARY && connection){
                if(marriage) {
                    if (!node.isMarried()) {
                        addMaritalRelationship(currentLabel);
                        marriage = false;
                        connection = false;
                    } else {
                        System.out.println("cannot marry, second person is alread in a marital relationship");
                        marriage = false;
                        connection = false;
                        setUnselected(primarySelected);
                    }
                }
            }
        }
    }

    /**
     *  Updates GUI relation labels depending on the current status of the selected labels
     */
    private void updateRelationship() {
        if(primarySelected != null && secondLabel != null){
            Node personOne = (Node) primarySelected.getUserData();
            Node personTwo = (Node)secondLabel.getUserData();

            String relationship = graph.findRelationship(personOne.id, personTwo.id);
            String output = "The Relationship from " + personOne.person.name + " to " + personTwo.person.name + " is:\n" + relationship;
            rel_output.setText(output);

            int closetRelative = graph.closestRelative(personOne.id, personTwo.id);
            relative_output.setText("Closet Relative: " + graph.nodes.get(closetRelative).person.name);
        }
        else{
            rel_output.setText("");
            relative_output.setText("");
        }
    }

    /**
     *  Updates GUI side panel on currently selected label
     */
    private void updateSidePanel() {
        if(primarySelected != null) {
            Node node = (Node) primarySelected.getUserData();
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
            imageView.setImage(new Image(file.getAbsolutePath()));
            mediaButton.setOnMouseClicked(event -> {
                //do nothing
            });
        }
    }

    /**
     *  Depending on the status of the toggle button, sets the mode to edit or to view for the application
     *  and sets text of toggle button depending on its currents state
     */
    @FXML
    private void toggleButtonPressed() {
        //if label is not null (something is selected)
        if(primarySelected != null){
            //unselect it
            setUnselected(primarySelected);
            //update side panel
            updateSidePanel();
        }
        //sets text of toggle button depending on its currents state
        if (editTButton.isSelected()){
            editTButton.setText("VIEW MODE");
        }
        else{
            editTButton.setText("EDIT MODE");
        }
    }
}