package edu.templ.usbfamilytree;

import java.io.File;

public class Settings {
    public static final double CIRCLE_RADIUS = 30.0;
    public static String currentDirectory = System.getProperty("user.dir");
    public static String graphPath =  currentDirectory + File.separator + "graph.json";

}
