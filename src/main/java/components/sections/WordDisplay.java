package components.sections;

import helpers.MyStyles;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import main.hangman.Game;

import java.util.HashSet;
import java.util.Set;

public class WordDisplay extends UpdatableSection {
    private Game game;
    @FXML
    private VBox vBox;
    @FXML
    private Label title;
    @FXML
    private FlowPane flow;

    @FXML
    private Text intro_message;

    public WordDisplay(Game game) {
        this.vBox = new VBox();
        this.setGame(game);
    }

    public Game getGame() {
        return game;
    }
    public void setGame(Game game) {
        this.game = game;
        if (this.game.isPlaying()) {
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
                char_container.setSpacing(5);

                Label char_display = new Label(Character.toString(c));
                char_display.setStyle(MyStyles.letter);

                Label char_index = new Label(Integer.toString(i+1));
                char_index.setStyle(MyStyles.index);

                char_container.getChildren().add(char_display);
                if (!this.game.getShown_indexes().contains(i)) {
                    char_container.getChildren().add(char_index);
                }

                this.flow.getChildren().add(char_container);
            }
            this.vBox.setAlignment(Pos.TOP_CENTER);
            this.vBox.getChildren().add(this.title);
            this.vBox.getChildren().add(this.flow);
        }
        else {
            this.vBox.getChildren().clear();
            this.title = new Label("Welcome to the hangman game!!");
            this.title.setStyle(MyStyles.title);
            if (this.game.getWords()==null || this.game.getWords().size()==0) {
                this.intro_message = new Text("Load a dictionary to begin!");
            }
            else if (this.game.getPrevRounds()==null || this.game.getPrevRounds().size()==0){
                this.intro_message = new Text("Dictionary loaded, go to Application->Start to play !!");
            }
            else {
                this.intro_message = new Text();
            }
            this.intro_message.setStyle(MyStyles.success);
            this.vBox.setAlignment(Pos.CENTER);
            this.vBox.setSpacing(100);
            this.vBox.getChildren().add(this.title);
            this.vBox.getChildren().add(this.intro_message);
        }
    }

    public VBox getVBox() {
        return this.vBox;
    }

}
