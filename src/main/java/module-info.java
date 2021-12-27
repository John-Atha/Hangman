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

    exports helpers;
    opens helpers to javafx.fxml;

    exports components.popups;
    opens components.popups to javafx.fxml;

    exports components.sections;
    opens components.sections to javafx.fxml;
}