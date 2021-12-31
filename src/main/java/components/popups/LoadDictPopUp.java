package components.popups;

import components.sections.*;
import exceptions.GameOverException;
import exceptions.LoadedDictionaryException;
import helpers.MyStyles;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.hangman.Game;
import read.DictReader;

import java.io.FileNotFoundException;

public class LoadDictPopUp {

    @FXML
    private Label title;

    @FXML
    private Text note;

    @FXML
    private HBox hBox1;
    @FXML
    private Label label1;
    @FXML
    private TextField dictField;

    @FXML
    private Button submit_button;

    @FXML
    private Text message;

    @FXML
    private Stage popup;
    @FXML
    private VBox vBox;

    private Game game;
    private GameHeader gameHeader;
    private CharactersLeft charsLeft;
    private ChancesImage chancesImage;
    private WordDisplay wordDisplay;
    private CharacterForm characterForm;

    public LoadDictPopUp(
            Game game,
            GameHeader gameHeader,
            CharactersLeft charsLeft,
            ChancesImage chancesImage,
            WordDisplay wordDisplay,
            CharacterForm characterForm
            ) {
        this.game = game;
        this.gameHeader = gameHeader;
        this.charsLeft = charsLeft;
        this.chancesImage = chancesImage;
        this.wordDisplay = wordDisplay;
        this.characterForm = characterForm;

        this.popup = new Stage();
        popup.setTitle("Load dictionary");

        this.vBox = new VBox();
        this.hBox1 = new HBox();

        this.title = new Label("Load");

        this.note = new Text(this.game.isPlaying() ? "Warning: Loading a new dictionary will terminate this round and count as a loss." : "");
        this.note.setStyle(MyStyles.error);

        this.label1 = new Label("Insert the id of the dictionary you would like to load.");
        this.dictField = new TextField();
        this.hBox1.getChildren().add(this.label1);
        this.hBox1.getChildren().add(this.dictField);

        this.submit_button = new Button("Submit");
        this.message = new Text();

        this.vBox.getChildren().add(this.title);
        this.vBox.getChildren().add(this.note);
        this.vBox.getChildren().add(this.hBox1);

        this.vBox.getChildren().add(this.submit_button);
        this.vBox.getChildren().add(this.message);

        this.vBox.setStyle(
                "-fx-padding: 100px 10px 0px 10px"
        );
        this.vBox.setAlignment(Pos.TOP_CENTER);
        this.hBox1.setAlignment(Pos.CENTER);

        this.title.setPadding(new Insets(40));
        this.title.setStyle(MyStyles.title);
        this.label1.setStyle(MyStyles.label);

        this.dictField.setStyle(MyStyles.textField);

        this.submit_button.setStyle(MyStyles.button);

        this.submit_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                submit();
            }
        });

        Scene scene = new Scene(vBox, 1800, 1000);
        popup.setScene(scene);
    }

    public Stage getPopup() {
        return this.popup;
    }

    private void submit() {
        String ID_dict = dictField.getText();

        // read the words from the dictionaries and pick one
        DictReader reader = new DictReader(ID_dict);

        try {
            reader.read();
            this.game.addDict(ID_dict, reader.getWords());
            this.message.setText("Read dictionary with id " + ID_dict + " successfully" +  (this.game.isPlaying() ? ", running round was abandoned" : "") + ".");
            this.message.setStyle(MyStyles.success);
            if (this.game.isPlaying()) {
                try {
                    System.out.println("RESETTING THE GAME !!");
                    this.game.giveUp();
                }
                catch (GameOverException e) {
                    this.characterForm.onGameOver();
                }
            }

            else {
                this.gameHeader.update(game);
                this.charsLeft.update(game);
                this.chancesImage.update(game);
                this.wordDisplay.update(game);
                this.characterForm.update(game);
            }
        }
        catch (FileNotFoundException e) {
            this.message.setText("Dictionary " +  reader.getName() + " not found, please try another ID");
            this.message.setStyle(MyStyles.error);
        }
        catch (LoadedDictionaryException e) {
            this.message.setText("Dictionary " +  reader.getName() + " already loaded, please try another ID");
            this.message.setStyle(MyStyles.error);
        }

    }
}
