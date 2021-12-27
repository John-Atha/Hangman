package components;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RoundsPopUp {

    String textStyling = "-fx-font-size: 30px;";

    private ArrayList<ArrayList<String>> rounds;

    @FXML
    private Stage popup;
    @FXML
    private VBox container;


    @FXML
    private Text message;

    @FXML
    private ArrayList<VBox> round_containers;
    @FXML
    private ArrayList<Text> rounds_texts;


    public RoundsPopUp(ArrayList<ArrayList<String>> rounds) {
        this.container = new VBox();
        this.popup = new Stage();
        popup.setTitle("Rounds");
        this.setRounds(rounds);
    }

    private void setRounds(ArrayList<ArrayList<String>> rounds) {
        if (rounds.size()==0) {
            this.message = new Text("No rounds played till now.");
            this.message.setStyle(
                    textStyling +
                    "-fx-fill: red;"
            );
            this.container.getChildren().add(this.message);
        }
        else {
            ArrayList<ArrayList<String>> last_rounds;
            if (rounds.size()>=5) {
                last_rounds = new ArrayList<ArrayList<String>>(rounds.subList(rounds.size()-5, rounds.size()));
            }
            else {
                last_rounds = new ArrayList<ArrayList<String>>(rounds);
            }

            for (int i=0; i<last_rounds.size(); i++) {
                ArrayList<String> curr_round = last_rounds.get(i);
                VBox curr_container = new VBox();
                for (int j=0; j<curr_round.size(); j++) {
                    Text curr_text = new Text(curr_round.get(j));
                    curr_text.setStyle(textStyling);
                    curr_container.getChildren().add(curr_text);
                }
                this.container.getChildren().add(curr_container);
            }
        }

        this.container.setStyle(
                "-fx-padding: 10px"
        );
        Scene scene = new Scene(this.container, 600, 400);
        popup.setScene(scene);
    }

    public Stage getPopUp() {
        return this.popup;
    }

}
