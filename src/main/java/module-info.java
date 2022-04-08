module edu.templ.usbfamilytree {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.controlsfx.controls;
    requires org.jgrapht.core;

    opens edu.templ.usbfamilytree to javafx.fxml;
    exports edu.templ.usbfamilytree;
}