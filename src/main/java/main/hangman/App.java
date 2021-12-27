package main.hangman;

import components.GameHeader;
import components.TopMenu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class App extends Application {

    private String menuItemStyles = "-fx-font-size: 50px;";

    public static class ReloadHeader implements Runnable {
        public void run(Game game, GameHeader gameHeader) {
            System.out.println("Starting game with:");
            System.out.println(game.getWords());
            gameHeader.setGame(game);
            if (game.getWords().size() != 0) {
                game.newRound();
                gameHeader.setMessage("", "");
            } else {
                gameHeader.setMessage("Load a dictionary to begin.", "-fx-fill: red;");
            }
        }

        @Override
        public void run() {
            ;
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Medialab Hangman");

        VBox page = new VBox();
        BorderPane main = new BorderPane();

        Game game = new Game(new ArrayList<String>());

        GameHeader gameHeader = new GameHeader(game, main);
        if (game.isPlaying()) {
            main.setTop(gameHeader.getVBox());
        }
        else {
            main.setTop(null);
        }

        //border.setLeft(addVBox());
        //border.setCenter(addGridPane());
        //border.setRight(addFlowPane());

        TopMenu topmenu = new TopMenu(stage, game, gameHeader);
        MenuBar menuBar = topmenu.getMenuBar();
        menuBar.setStyle(menuItemStyles);

        page.getChildren().add(menuBar);
        page.getChildren().add(main);

        Scene scene = new Scene(page, 2000, 1800);
        stage.setScene(scene);
        // stage.setHeight(2000);
        // stage.setWidth(1800);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}