package components;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

public class TopMenu {
    private MenuBar menuBar;
    private Stage stage;
    private ArrayList<String> words;


    public MenuBar getMenuBar() {
        return this.menuBar;
    }

    public TopMenu(Stage stage, ArrayList<String> words) {
        this.menuBar = new MenuBar();
        this.stage = stage;
        this.words = words;

        Menu application = new Menu("Application");

        MenuItem start = new MenuItem("start");
        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                System.out.println("Starting game with:");
                System.out.println(words);
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

        this.menuBar.getMenus().addAll(application, details);
    }

}
