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