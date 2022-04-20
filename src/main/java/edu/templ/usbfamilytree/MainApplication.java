package edu.templ.usbfamilytree;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *  Class that contains main method. Starts JavaFX application by extending application and overriding the start method.
 */
public class MainApplication extends Application {
    /**
     * Overridden function that starts the JavaFX GUI
     *
     * @param stage function provided stage that will be shown at launch
     */
    @Override
    public void start(Stage stage){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("dashboard-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle(Settings.applicationName);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * main function used to run the program. Only has launch to start GUI.
     *
     * @param args arguments provided to program if any.
     */
    public static void main(String[] args) {
        launch();
    }
}