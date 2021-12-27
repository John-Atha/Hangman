package main.hangman;

import components.sections.ChancesImage;
import components.sections.CharactersLeft;
import components.sections.GameHeader;
import components.sections.TopMenu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class App extends Application {

    private String menuItemStyles = "-fx-font-size: 50px;";

    public static class ReloadHeader {
        public void run(Game game, GameHeader gameHeader) {
            gameHeader.setGame(game, false);
        }
    }

    public static class ReloadCharactersLeft {
        public void run(Game game, CharactersLeft charsLeft) {
            charsLeft.setGame(game);
            // System.out.println("AAAA: " + charsLeft.getContainer().getChildren());
        }
    }

    public static class ReloadChancesImage {
        public void run(Game game, ChancesImage chancesImage) {
            chancesImage.setGame(game);
            System.out.println("AAAA: " + chancesImage.getVBox().getChildren());

        }
    }


    @Override
    public void start(Stage stage) {
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

        CharactersLeft charsLeft = new CharactersLeft(game);
        main.setRight(charsLeft.getVBox());

        ChancesImage chancesImage = new ChancesImage(game);
        main.setLeft(chancesImage.getVBox());

        //border.setLeft(addVBox());
        //border.setCenter(addGridPane());
        //border.setRight(addFlowPane());

        TopMenu topmenu = new TopMenu(stage, game, gameHeader, charsLeft, chancesImage);
        MenuBar menuBar = topmenu.getMenuBar();
        menuBar.setStyle(menuItemStyles);

        page.getChildren().add(menuBar);
        page.getChildren().add(main);

        Scene scene = new Scene(page, 2000, 1800);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}