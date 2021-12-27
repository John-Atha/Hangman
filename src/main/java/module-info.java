module main.hangman {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires org.json;
    requires java.net.http;

    opens main.hangman to javafx.fxml;
    exports main.hangman;

    opens components to javafx.fxml;
    exports components;

    exports helpers;
    opens helpers to javafx.fxml;
}