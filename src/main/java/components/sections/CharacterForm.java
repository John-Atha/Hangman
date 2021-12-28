package components.sections;

import components.popups.SolutionPopUp;
import exceptions.GameOverException;
import exceptions.ShownCharException;
import helpers.MyStyles;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import main.hangman.Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class CharacterForm extends UpdatableSection{
    private Game game;

    @FXML
    private Stage parent;
    @FXML
    private VBox vBox;

    @FXML
    private Label title;

    @FXML
    private GridPane grid;

    @FXML
    private Label index_label;
    @FXML
    private TextField index_field;

    @FXML
    private Label char_label;
    @FXML
    private TextField char_field;

    @FXML
    private Button submit_button;

    @FXML
    private VBox messagesContainer;
    @FXML
    private Text message;
    @FXML
    private Text submessage;

    private GameHeader gameheader;
    private CharactersLeft charactersLeft;
    private ChancesImage chancesImage;
    private WordDisplay wordDisplay;

    public CharacterForm(Stage parent, Game game, GameHeader gameHeader, CharactersLeft charsLeft, ChancesImage chancesImage, WordDisplay wordDisplay) {
        this.vBox = new VBox();
        this.gameheader = gameHeader;
        this.charactersLeft = charsLeft;
        this.chancesImage = chancesImage;
        this.wordDisplay = wordDisplay;
        this.parent = parent;
        this.setGame(game);
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
        if (this.game.isPlaying()) {
            this.vBox.getChildren().clear();

            this.title = new Label("Guess a letter!");
            this.title.setStyle(MyStyles.title);

            this.grid = new GridPane();
            this.grid.setAlignment(Pos.CENTER);
            this.index_label = new Label("Letter position:");
            this.index_label.setStyle(MyStyles.label);
            this.index_field = new TextField();
            this.index_field.setStyle(MyStyles.textField);
            this.char_label = new Label("Letter:");
            this.char_label.setStyle(MyStyles.label);
            this.char_field = new TextField();
            this.char_field.setStyle(MyStyles.textField);

            this.grid.add(index_label, 0, 0, 1, 1);
            this.grid.add(index_field, 1, 0, 1, 1);
            this.grid.add(char_label, 0, 1, 1, 1);
            this.grid.add(char_field, 1, 1, 1, 1);

            this.submit_button = new Button("Submit");
            this.submit_button.setStyle(MyStyles.button);

            this.submit_button.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    submit();
                }
            });

            this.messagesContainer = new VBox();
            this.message = new Text();
            this.submessage = new Text();
            this.vBox.setAlignment(Pos.TOP_CENTER);
            if (this.game.isPlaying()) {
                this.vBox.getChildren().add(this.title);
                this.vBox.getChildren().add(this.grid);
                this.vBox.getChildren().add(this.submit_button);
            }
            this.messagesContainer.getChildren().add(this.message);
            this.messagesContainer.getChildren().add(this.submessage);
            this.messagesContainer.setAlignment(Pos.CENTER);
            this.messagesContainer.setPadding(new Insets(20));
            this.messagesContainer.setSpacing(10);
            this.vBox.getChildren().add(this.messagesContainer);
        }

    }

    private void generate_error(String message, String submessage) {
        this.message.setText(message);
        this.submessage.setText(submessage);
        this.message.setStyle(MyStyles.error);
        this.submessage.setStyle(MyStyles.error);
    }

    private void generate_success(String message, String submessage) {
        this.message.setText(message);
        this.submessage.setText(submessage);
        this.message.setStyle(MyStyles.success);
        this.submessage.setStyle(MyStyles.success);
    }

    private void submit() {
        // parse index and char + validate
        int index = 0;
        try {
            index = Integer.parseInt(this.index_field.getText())-1;
        }
        catch (NumberFormatException e) {
            this.generate_error("Position should be a number!", "");
            return;
        }
        if (index<0 || index>=this.game.getWord().length()) {
            this.generate_error("This position is out of word's range!", "");
            return;
        }
        char c;
        String c_ = this.char_field.getText();
        if (c_ == null || c_.length()!=1) {
            this.generate_error("Type exactly one char!", "");
            return;
        }
        c = c_.toUpperCase(Locale.ROOT).charAt(0);

        if (
            !this.game.getShown_indexes().contains(index) &&
            this.game.getWord().charAt(index)!=c &&
            this.game.getChances_remaining()==1
        ) {
            this.game.setPrevWord(this.game.getWord());
            SolutionPopUp solutionPopUp = new SolutionPopUp(
                    game,
                    null
            );
            Stage popup = solutionPopUp.getPopUp();
            popup.initOwner(parent);
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.showAndWait();

        }
        // make the move
        make_the_move(index, c);
    }

    private void make_the_move(int index, char c) {
        System.out.println("\nI am making the move");
        try {
            this.game.move(index, c);
            this.gameheader.update(this.game);
            this.charactersLeft.update(this.game);
            this.chancesImage.update(this.game);
            this.wordDisplay.update(this.game);

            if (this.game.getWord().charAt(index) == c) {
                this.generate_success("Correct guess !!", "");
            }
            else {
                this.generate_error("Sorry, wrong guess...", "");
            }
        }
        catch (ShownCharException e) {
            this.generate_error("The letter of this position is already found!", "");
        }
        catch (GameOverException e) {
            this.onGameOver();
        }
    }

    public void onGameOver() {
        this.vBox.getChildren().remove(this.title);
        this.vBox.getChildren().remove(this.grid);
        this.vBox.getChildren().remove(this.submit_button);

        if (this.game.isWon()) {
            generate_success("Congratulations, YOU WON THE LAST GAME !!", "Go to Application->Start to play again!!");
        }
        else {
            generate_error("Oops, you lost the last game...", "Go to Application->Start to try again!");
        }
        this.gameheader.update(this.game);
        this.charactersLeft.update(this.game);
        this.chancesImage.update(this.game);
        this.wordDisplay.update(this.game);
    }

    public VBox getVBox() {
        return this.vBox;
    }
}
