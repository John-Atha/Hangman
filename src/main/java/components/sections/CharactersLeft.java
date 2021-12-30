package components.sections;

import helpers.MyStyles;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import main.hangman.Game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CharactersLeft extends UpdatableSection {
    @FXML
    private VBox vBox;
    @FXML
    private Label title;
    @FXML
    private FlowPane container;
    @FXML
    private Text message;

    private Game game;

    public CharactersLeft(Game game) {
        this.vBox = new VBox();
        this.container = new FlowPane();
        this.container.setPadding(new Insets(10));
        this.container.setVgap(5);
        this.container.setHgap(5);
        this.setGame(game);
    }

    public Game getGame() {
        return this.game;
    }
    public void setGame(Game game) {
        this.game = game;

        this.vBox.getChildren().clear();
        this.container.getChildren().clear();

        this.title = new Label("Available characters");
        this.title.setStyle(MyStyles.title);

        // System.out.println("My game has been updated to chars:" + this.game.getAvailable_chars());
        // Set<Character> chars = new HashSet<>(game.getAvailable_chars()!=null ? game.getAvailable_chars() : new ArrayList<>());
        if (this.game.isPlaying()) {
            for (int index=0; index<this.game.getWord().length(); index++) {
                if (!this.game.getShown_indexes().contains(index)) {
                    VBox chars_column = new VBox();
                    Label index_ = new Label(Integer.toString(index+1));
                    index_.setStyle(MyStyles.label);
                    index_.setPadding(new Insets(30));
                    chars_column.getChildren().add(index_);
                    chars_column.setStyle(MyStyles.border);
                    ArrayList<Character> av_chars = this.game.getAvailable_chars().get(index);
                    int length = Math.min(av_chars.size(), 5);
                    for (char c : av_chars.subList(0, length)) {
                        Label char_ = new Label(Character.toString(c));
                        char_.setStyle(MyStyles.label);
                        char_.setPadding(new Insets(30));
                        chars_column.getChildren().add(char_);
                    }
                    if (av_chars.size()>=5) {
                        Label more = new Label("...");
                        more.setStyle((MyStyles.label));
                        more.setPadding(new Insets(10));
                        chars_column.getChildren().add(more);
                    }
                    if (this.game.isPlaying() && (av_chars==null || av_chars.isEmpty())) {
                        this.message = new Text("-");
                        this.message.setStyle(MyStyles.error);
                        this.vBox.getChildren().add(this.message);
                    }

                    this.container.getChildren().add(chars_column);
                }
            }

        }

        // for (Character c : chars) {
        //     Label char_display = new Label(c.toString());
        //     char_display.setStyle(MyStyles.label);
        //     char_display.setPadding(new Insets(30));
        //     this.container.getChildren().add(char_display);
        // }
        if (this.game.isPlaying()) {
            this.vBox.getChildren().add(this.title);
            this.vBox.getChildren().add(this.container);
            // if (chars.size() == 0) {
            //     this.message = new Text("No available characters.");
            //     this.message.setStyle(MyStyles.error);
            //     this.vBox.getChildren().add(this.message);
            // }
        }
    }

    public VBox getVBox() {
        return this.vBox;
    }
}
