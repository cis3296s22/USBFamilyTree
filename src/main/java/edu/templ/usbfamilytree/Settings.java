package edu.templ.usbfamilytree;

import java.io.File;

public class Settings {
    public static final double LABEL_WIDTH = 150.0;
    public static final double LABEL_HEIGHT = 50.0;
    public static String currentDirectory = System.getProperty("user.dir");
    public static String graphPath =  currentDirectory + File.separator + "graph.json";
    public static final double NAME_LABEL_OFFSET = 10.0;
    public static final double MARITAL_EDGE_LENGTH = 100;
}
