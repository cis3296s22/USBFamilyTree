package edu.templ.usbfamilytree;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class MainApplication extends Application {
    private final String applicationName = "USB Family Tree";


    @Override
    public void start(Stage stage){
        try{
            System.out.println("Graph file path: " + Settings.graphPath);
            File graph = new File(Settings.graphPath);
            String graphJson = "";
            if(!graph.exists()) {
                graph.createNewFile();
            }
            else {
                graphJson = FileUtils.ReadFile(graph.getPath());
            }
            if(!graphJson.isEmpty() && graphJson != null)
            {
                /** TODO
                 * 1) Deserialize from Json string to Graph object
                 * 2) Redraw the entire graph
                 * Graph graph = FileUtils.<Graph>fromJson(graphJson, Graph.class);
                 * graph.draw();
                 */
            }
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("dashboard-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle(applicationName);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
        launch();
    }
}