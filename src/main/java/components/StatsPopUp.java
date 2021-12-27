package components;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.hangman.Game;

import java.util.ArrayList;

public class StatsPopUp {
    private String textStyles = "-fx-font-size: 30px;";

    @FXML
    private Text allWords;
    @FXML
    private Text words6;
    @FXML
    private Text words7to9;
    @FXML
    private Text words10up;
    @FXML
    private Stage popup;
    @FXML
    private VBox vBox;

    private Game game;
    private float count1 = 0f;
    private float count2 = 0f;
    private float count3 = 0f;
    private float count_all = 0f;

    public StatsPopUp(Game game) {
        this.game = game;
        this.count_all = this.game.getWords().size();
        this.count();
        this.display();
    }

    private void count() {
        for (String word: this.game.getWords()) {
            if (word.length()==6) {
                this.count1 ++;
            }
            else if (7<=word.length() && word.length()<=9) {
                this.count2++;
            }
            else {
                this.count3++;
            }
        }
        this.count1 /= this.count_all;
        this.count1 *= 100f;
        this.count2 /= this.count_all;
        this.count2 *= 100f;
        this.count3 /= this.count_all;
        this.count3 *= 100f;
    }

    private float myRound(float x) {
        return Math.round(x * 100f) / 100f;
    }

    private void display() {
        this.popup = new Stage();
        popup.setTitle("Dictionary statistics");
        this.vBox = new VBox();

        if (this.count_all>0) {
            this.allWords = new Text("All words: " + Math.round(this.count_all));
            this.words6 = new Text("Words with 6 letters: " + this.myRound(this.count1) + "%");
            this.words7to9 = new Text("Words with more than 7 and less than 9 letters: " + this.myRound(this.count2) + "%");
            this.words10up = new Text("Words with more than 10 letters: " + this.myRound(this.count3) + "%");


            this.vBox.getChildren().add(this.allWords);
            this.vBox.getChildren().add(this.words6);
            this.vBox.getChildren().add(this.words7to9);
            this.vBox.getChildren().add(this.words10up);

            this.allWords.setStyle(textStyles);
            this.words6.setStyle(textStyles);
            this.words7to9.setStyle(textStyles);
            this.words10up.setStyle(textStyles);
        }
        else {
            this.allWords = new Text("No words loaded, please load a dictionary from the option Application->Load.");
            this.allWords.setStyle(
                    textStyles+
                    "-fx-fill: red;"
            );
            this.vBox.getChildren().add(this.allWords);
        }

        this.vBox.setStyle(
                "-fx-padding: 10px"
        );

        Scene scene = new Scene(vBox, 600, 400);
        popup.setScene(scene);
    }

    public Stage getPopup() {
        return this.popup;
    }
}
