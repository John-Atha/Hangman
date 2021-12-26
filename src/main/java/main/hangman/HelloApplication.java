package main.hangman;

import components.CreateDictPopUp;
import components.LoadDictPopUp;
import components.StatsPopUp;
import components.TopMenu;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class HelloApplication extends Application {

    private String menuItemStyles = "-fx-font-size: 50px";

    /*private MenuBar MyMenu(Stage stage, ArrayList<String> words) {
        MenuBar menubar = new MenuBar();

        Menu application = new Menu("Application");

        MenuItem start = new MenuItem("start");
        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                System.out.println("Starting game with:");
                System.out.println(words.toString());
            }
        });

        MenuItem create = new MenuItem("create");
        create.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                CreateDictPopUp createDictPopUp = new CreateDictPopUp();
                Stage popup = createDictPopUp.getPopup();
                popup.initOwner(stage);
                popup.initModality(Modality.APPLICATION_MODAL);
                popup.showAndWait();
            }
        });

        MenuItem load =  new MenuItem("load");
        load.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                LoadDictPopUp loadDictPopUp = new LoadDictPopUp(words);
                Stage popup = loadDictPopUp.getPopup();
                popup.initOwner(stage);
                popup.initModality(Modality.APPLICATION_MODAL);
                popup.showAndWait();
            }
        });

        MenuItem exit = new MenuItem("exit");
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Platform.exit();
            }
        });

        application.getItems().addAll(start, create, load, exit);

        Menu details = new Menu("Details");

        MenuItem dictionary = new MenuItem("dictionary");
        dictionary.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                StatsPopUp statsPopUp = new StatsPopUp(words);
                Stage popup = statsPopUp.getPopup();
                popup.initOwner(stage);
                popup.initModality(Modality.APPLICATION_MODAL);
                popup.showAndWait();
            }
        });

        MenuItem rounds = new MenuItem("rounds");
        MenuItem solution = new MenuItem("solution");
        details.getItems().addAll(dictionary, rounds, solution);

        menubar.getMenus().addAll(application, details);
        return menubar;
    }
    */

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