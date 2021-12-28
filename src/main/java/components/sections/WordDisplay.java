package components.sections;

import helpers.MyStyles;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.hangman.Game;

import java.util.HashSet;
import java.util.Set;

public class WordDisplay {
    private Game game;
    @FXML
    private VBox vBox;
    @FXML
    private Label title;
    @FXML
    private FlowPane flow;

    public WordDisplay(Game game) {
        this.setGame(game);
        this.vBox = new VBox();
    }

    public Game getGame() {
        return game;
    }
    public void setGame(Game game) {
        this.game = game;
        if (this.game.getWord() != null) {
            this.vBox.getChildren().clear();
            this.title = new Label("Find the hidden word!");
            this.title.setStyle(MyStyles.title);
            this.flow = new FlowPane();
            this.flow.setPadding(new Insets(30));
            this.flow.setVgap(5);
            this.flow.setHgap(25);
            this.flow.setAlignment(Pos.CENTER);
            Set<Integer> shown = new HashSet<Integer>(this.game.getShown_indexes());
            for (int i=0; i<this.game.getWord().length(); i++) {
                char c = shown.contains(i) ? this.game.getWord().charAt(i) : '_';

                VBox char_container = new VBox();
                char_container.setSpacing(15);

                Label char_display = new Label(Character.toString(c));
                char_display.setStyle(MyStyles.letter);

                Label char_index = new Label(Integer.toString(i+1));
                char_index.setStyle(MyStyles.index);

                char_container.getChildren().add(char_display);
                char_container.getChildren().add(char_index);

                this.flow.getChildren().add(char_container);
            }
            this.vBox.setAlignment(Pos.TOP_CENTER);
            this.vBox.getChildren().add(this.title);
            this.vBox.getChildren().add(this.flow);
        }
    }

    public VBox getVBox() {
        return this.vBox;
    }

}