package components.popups;

import components.sections.*;
import exceptions.GameOverException;
import exceptions.NoDictsException;
import helpers.MyStyles;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.hangman.Game;

public class SolutionPopUp {

    @FXML
    private Stage popup;
    @FXML
    private VBox vBox;

    @FXML
    private Label title;
    @FXML
    private Text message;
    @FXML
    private Button button;
    @FXML
    private Text word;

    private Game game;
    private CharacterForm characterForm;

    public SolutionPopUp(
            Game game,
            CharacterForm characterForm
    ) {
        this.vBox = new VBox();
        this.vBox.setPadding(new Insets(10));
        this.vBox.setSpacing(10);
        this.characterForm = characterForm;
        this.setGame(game);
    }

    public void setGame(Game game) {
        this.game = game;
        this.vBox.getChildren().clear();

        if (this.game.isPlaying()) {
            this.title = new Label("Are you sure you want to see the solution?");
            this.title.setStyle(MyStyles.title);

            this.message = new Text("The current round will count as a loss.");
            this.message.setStyle(MyStyles.error);

            this.word = new Text();
            this.word.setStyle(MyStyles.success);

            this.button = new Button("See solution");
            this.button.setStyle(MyStyles.button);

            this.button.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    show();
                }
            });

            this.vBox.getChildren().add(this.title);
            this.vBox.getChildren().add(this.message);
            this.vBox.getChildren().add(this.word);
            this.vBox.getChildren().add(this.button);
        }
        else {
            this.message = new Text("There are no rounds (and therefore solutions) running.");
            this.message.setStyle(MyStyles.error);
            this.vBox.getChildren().add(this.message);
        }

        this.vBox.setAlignment(Pos.CENTER);

        this.popup = new Stage();
        this.popup.setTitle("Solution");
        Scene scene = new Scene(vBox, 1800, 1000);
        popup.setScene(scene);
    }

    public void show() {
        this.vBox.getChildren().remove(this.message);
        this.title.setText("Hidden word:");
        this.vBox.getChildren().remove(this.button);
        this.word.setText(this.game.getWord());
        if (game.isPlaying()) {
            try {
                System.out.println("RESETTING THE GAME !!");
                game.giveUp();
            } catch (GameOverException ex) {
                characterForm.onGameOver();
            }
        }
    }

    public Stage getPopUp() {
        return this.popup;
    }
}
