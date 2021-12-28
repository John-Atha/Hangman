package components.sections;

import components.popups.*;
import components.sections.GameHeader;
import exceptions.NoDictsException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.hangman.App;
import main.hangman.Game;
import read.WordsPicker;

public class TopMenu {
    private MenuBar menuBar;
    private Stage stage;
    private Game game;

    private GameHeader gameHeader;
    private CharactersLeft charactersLeft;
    private ChancesImage chancesImage;
    private WordDisplay wordDisplay;
    private CharacterForm characterForm;

    // private App.ReloadHeader reloadHeader;
    // private App.ReloadCharactersLeft reloadCharactersLeft;
    // private App.ReloadChancesImage reloadChancesImage;
    // private App.ReloadWordDisplay reloadWordDisplay;
    // private App.ReloadCharacterForm reloadCharacterForm;

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

        // this.reloadHeader = new App.ReloadHeader();
        // this.reloadCharactersLeft = new App.ReloadCharactersLeft();
        // this.reloadChancesImage = new App.ReloadChancesImage();
        // this.reloadWordDisplay = new App.ReloadWordDisplay();
        // this.reloadCharacterForm = new App.ReloadCharacterForm();

        Menu application = new Menu("Application");

        MenuItem start = new MenuItem("start");
        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                // reloadHeader.run(game, gameHeader);
                // reloadCharactersLeft.run(game, charsLeft);
                // reloadChancesImage.run(game, chancesImage);
                // reloadWordDisplay.run(game, wordDisplay);
                // reloadCharacterForm.run(game, characterForm);
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
                LoadDictPopUp loadDictPopUp = new LoadDictPopUp(
                        game,
                        gameHeader,    // reloadHeader,
                        charsLeft,     // reloadCharactersLeft,
                        chancesImage,  // reloadChancesImage,
                        wordDisplay,   // reloadWordDisplay,
                        characterForm //, reloadCharacterForm
                );
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
                StatsPopUp statsPopUp = new StatsPopUp(game);
                Stage popup = statsPopUp.getPopup();
                popup.initOwner(stage);
                popup.initModality(Modality.APPLICATION_MODAL);
                popup.showAndWait();
            }
        });

        MenuItem rounds = new MenuItem("rounds");

        rounds.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                RoundsPopUp roundsPopUp = new RoundsPopUp(game.getPrevRounds());
                Stage popup = roundsPopUp.getPopUp();
                popup.initOwner(stage);
                popup.initModality(Modality.APPLICATION_MODAL);
                popup.showAndWait();
            }
        });


        MenuItem solution = new MenuItem("solution");
        details.getItems().addAll(dictionary, rounds, solution);

        this.menuBar.getMenus().addAll(application, details);
    }

}
