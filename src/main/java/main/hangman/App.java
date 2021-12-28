package main.hangman;

import components.sections.*;
import helpers.MyStyles;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class App extends Application {

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

        WordDisplay wordDisplay = new WordDisplay(game);
        main.setCenter(wordDisplay.getVBox());

        CharacterForm characterForm = new CharacterForm(game, gameHeader, charsLeft, chancesImage, wordDisplay);
        main.setBottom(characterForm.getVBox());

        TopMenu topmenu = new TopMenu(stage, game, gameHeader, charsLeft, chancesImage, wordDisplay, characterForm);
        MenuBar menuBar = topmenu.getMenuBar();
        menuBar.setStyle(MyStyles.menuItem);

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