package components;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import main.hangman.Game;

public class GameHeader {

    private String textStyle = "-fx-font-size: 30px;";

    @FXML
    private Text total_words;

    @FXML
    private Text available_words;

    @FXML
    private Text points;

    @FXML
    private Text success_rate;

    @FXML
    private Text word;

    @FXML
    private VBox vBox;

    @FXML
    private Text message;

    private boolean created = false;

    private Game game;

    private BorderPane parent;

    public GameHeader(Game game, BorderPane parent) {
        this.vBox = new VBox();
        this.vBox.setPadding(new Insets(10));
        vBox.setSpacing(8);
        this.parent = parent;
        this.setGame(game);
        this.created = true;
    }

    public void setGame(Game game) {
        this.game = game;

        if (this.created) {
            System.out.println("I am the header, updating my words to:");
            System.out.println(this.game.getWords());
            this.word.setText("Chosen word: " + this.game.getWord());
            this.total_words.setText("Total words: " + this.game.getWords().size());
            this.available_words.setText("Words left:" + this.game.getWords_left().size());
            this.points.setText("Points: " + this.game.getPoints());
            float rate = this.game.getMoves()==0 ? 0f : (this.game.getMoves()-(6-this.game.getChances_remaining()))/((float) this.game.getMoves());
            this.success_rate.setText("Success rate: " + rate);
            // vBox.getChildren().clear();
        }
        else {
            System.out.println("I am the header, first time here!!");
            this.word = new Text("Chosen word: " + this.game.getWord());
            this.message = new Text();
            this.total_words = new Text("Total words: " + this.game.getWords().size());
            this.available_words = new Text("Words left:" + this.game.getWords_left().size());
            this.points = new Text("Points: " + this.game.getPoints());
            float rate = this.game.getMoves()==0 ? 0f : (this.game.getMoves()-(6-this.game.getChances_remaining()))/((float) this.game.getMoves());
            this.success_rate = new Text("Success rate: " + rate);

            vBox.getChildren().add(this.message);
            vBox.getChildren().add(this.total_words);
            vBox.getChildren().add(this.available_words);
            vBox.getChildren().add(this.points);
            vBox.getChildren().add(this.success_rate);
            vBox.getChildren().add(this.word);
        }

        this.message.setStyle(textStyle);
        this.total_words.setStyle(textStyle);
        this.available_words.setStyle(textStyle);
        this.points.setStyle(textStyle);
        this.success_rate.setStyle(textStyle);
        this.word.setStyle(textStyle);

        this.parent.setTop(this.vBox);
    }

    public void setMessage(String message, String style) {
        if (message.length()!=0) {
            this.total_words.setText("");
            this.available_words.setText("");
            this.points.setText("");
            this.success_rate.setText("");
        }
        this.message.setText(message);
        this.message.setStyle(
                textStyle +
                style
        );
    }

    public VBox getVBox() {
        return this.vBox;
    }
}
