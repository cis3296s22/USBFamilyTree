module edu.templ.usbfamilytree {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.controlsfx.controls;

    opens edu.templ.usbfamilytree to javafx.fxml;
    exports edu.templ.usbfamilytree;
}