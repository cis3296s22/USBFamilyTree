package edu.templ.usbfamilytree;

import java.io.File;

/**
 *  public class with global static variables that change the properties of the application
 */
public class Settings {
    /**
     * Get current directory to save files in
     */
    public static String currentDirectory = System.getProperty("user.dir");
    /**
     * File path to graph's json object, based on current directory
     */
    public static String graphPath =  currentDirectory + File.separator + "graph.json";
    /**
     *  Setting for base label width
     */
    public static final double LABEL_WIDTH = 150.0;
    /**
     *  Setting for base label height
     */
    public static final double LABEL_HEIGHT = 50.0;
    /**
     *  Setting for horizontal length of line between two parent labels
     */
    public static final double MARITAL_EDGE_LENGTH = 80;
    /**
     *  Setting for vertical length between two parents labels and a child
     */
    public static final double CHILD_OFFSET = 140;
    /**
     *  Setting for separation between two children
     */
    public static final double CHILDREN_PADDING = 20.0;
    /**
     *  Setting for name of application
     */
    public static final String applicationName = "USB Family Tree";
}
