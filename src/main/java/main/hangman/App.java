package main.hangman;

import components.TopMenu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class App extends Application {

    private String menuItemStyles = "-fx-font-size: 50px";

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Medialab Hangman");

        ArrayList<String> words = new ArrayList<String>();

        VBox vBox = new VBox();

        TopMenu topmenu = new TopMenu(stage, words);
        MenuBar menuBar = topmenu.getMenuBar();

        menuBar.setStyle(menuItemStyles);

        vBox.getChildren().add(menuBar);

        Scene scene = new Scene(vBox, 1000, 800);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}