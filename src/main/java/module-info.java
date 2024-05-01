module com.exemple.loginpharmagest {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens com.exemple.loginpharmagest to javafx.fxml;
    exports com.exemple.loginpharmagest;
}