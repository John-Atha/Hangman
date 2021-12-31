package components.sections;

import components.popups.*;
import exceptions.GameOverException;
import exceptions.NoDictsException;
import helpers.MyStyles;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.hangman.Game;

public class TopMenu {
    private MenuBar menuBar;
    private Stage stage;
    private Game game;

    private GameHeader gameHeader;
    private CharactersLeft charactersLeft;
    private ChancesImage chancesImage;
    private WordDisplay wordDisplay;
    private CharacterForm characterForm;

    public MenuBar getMenuBar() {
        return this.menuBar;
    }

    public TopMenu(
            Stage stage, Game game,
            GameHeader gameHeader,
            CharactersLeft charsLeft,
            ChancesImage chancesImage,
            WordDisplay wordDisplay,
            CharacterForm characterForm) {
        this.menuBar = new MenuBar();
        this.stage = stage;
        this.game = game;
        this.gameHeader = gameHeader;
        this.charactersLeft = charsLeft;
        this.chancesImage = chancesImage;
        this.wordDisplay = wordDisplay;
        this.characterForm = characterForm;

        Menu application = new Menu("Application");

        MenuItem start = new MenuItem("Start");
        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                if (game.isPlaying()) {
                    try {
                        System.out.println("RESETTING THE GAME !!");
                        game.giveUp();
                    } catch (GameOverException ex) {
                        characterForm.onGameOver();
                    }
                }
                try {
                    game.newRound(); // the exception will be thrown here if no words are available...
                    gameHeader.update(game);
                    charsLeft.update(game);
                    chancesImage.update(game);
                    wordDisplay.update(game);
                    characterForm.update(game);
                }
                catch (NoDictsException ex) {
                    ErrorPopUp errorPopUp = new ErrorPopUp(ex.getMessage());
                    Stage popup = errorPopUp.getPopUp();
                    popup.initOwner(stage);
                    popup.initModality(Modality.APPLICATION_MODAL);
                    popup.showAndWait();
                }
            }
        });

        MenuItem create = new MenuItem("Create");
        create.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                CreateDictPopUp createDictPopUp = new CreateDictPopUp();
                Stage popup = createDictPopUp.getPopup();
                popup.initOwner(stage);
                popup.initModality(Modality.APPLICATION_MODAL);
                popup.showAndWait();
            }
        });

        MenuItem load =  new MenuItem("Load");
        load.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                LoadDictPopUp loadDictPopUp = new LoadDictPopUp(
                        game,
                        gameHeader,
                        charsLeft,
                        chancesImage,
                        wordDisplay,
                        characterForm
                );
                Stage popup = loadDictPopUp.getPopup();
                popup.initOwner(stage);
                popup.initModality(Modality.APPLICATION_MODAL);
                popup.showAndWait();
            }
        });

        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Platform.exit();
            }
        });

        application.getItems().addAll(start, create, load, exit);

        Menu details = new Menu("Details");

        MenuItem dictionary = new MenuItem("Dictionary");
        dictionary.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                StatsPopUp statsPopUp = new StatsPopUp(game);
                Stage popup = statsPopUp.getPopup();
                popup.initOwner(stage);
                popup.initModality(Modality.APPLICATION_MODAL);
                popup.showAndWait();
            }
        });

        MenuItem rounds = new MenuItem("Rounds");

        rounds.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                RoundsPopUp roundsPopUp = new RoundsPopUp(game.getPrevRounds());
                Stage popup = roundsPopUp.getPopUp();
                popup.initOwner(stage);
                popup.initModality(Modality.APPLICATION_MODAL);
                popup.showAndWait();
            }
        });


        MenuItem solution = new MenuItem("Solution");

        solution.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                SolutionPopUp solutionPopUp = new SolutionPopUp(
                        game,
                        characterForm
                );
                Stage popup = solutionPopUp.getPopUp();
                popup.initOwner(stage);
                popup.initModality(Modality.APPLICATION_MODAL);
                popup.showAndWait();
            }
        });

        application.setStyle(MyStyles.menu);
        details.setStyle(MyStyles.menu);

        start.setStyle(MyStyles.menuItem);
        create.setStyle(MyStyles.menuItem);
        load.setStyle(MyStyles.menuItem);
        exit.setStyle(MyStyles.menuItem);
        dictionary.setStyle(MyStyles.menuItem);
        solution.setStyle(MyStyles.menuItem);
        rounds.setStyle(MyStyles.menuItem);


        details.getItems().addAll(dictionary, rounds, solution);

        this.menuBar.getMenus().addAll(application, details);
    }

}
