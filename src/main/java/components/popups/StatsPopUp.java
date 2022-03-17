package components.popups;

import helpers.MyStyles;
import helpers.Row;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.hangman.Game;


public class StatsPopUp {
    private String textStyles = "-fx-font-size: 30px;";

    @FXML
    private Text allWords;
    @FXML
    private TableView table;

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

        if (this.count_all>0) {

            this.table = new TableView();

            TableColumn<Row, String> column1 = new TableColumn<>("Category");
            column1.setCellValueFactory(new PropertyValueFactory<>("description"));
            TableColumn<Row, String> column2 = new TableColumn<>("Value");
            column2.setCellValueFactory(new PropertyValueFactory<>("value"));

            this.table.getColumns().add(column1);
            this.table.getColumns().add(column2);
            this.table.getItems().add(new Row("All words", Math.round(this.count_all)+""));
            this.table.getItems().add(new Row("Words with 6 letters", this.myRound(this.count1) + "%"));
            this.table.getItems().add(new Row("Words with more than 7 and less than 9 letters", this.myRound(this.count2) + "%"));
            this.table.getItems().add(new Row("Words with more than 10 letters", this.myRound(this.count3) + "%"));

            column1.setStyle(MyStyles.tableColumn);
            column2.setStyle(MyStyles.tableColumn);
            column1.prefWidthProperty().bind(table.widthProperty().multiply(0.7));
            column2.prefWidthProperty().bind(table.widthProperty().multiply(0.3));

            this.vBox = new VBox(this.table);
        }
        else {
            this.vBox = new VBox();
            this.allWords = new Text("No words loaded, please load a dictionary from the option Application->Load.");
            this.allWords.setStyle(MyStyles.error);
            this.vBox.getChildren().add(this.allWords);
        }

        this.vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox, 800, 600);
        popup.setScene(scene);
    }

    public Stage getPopup() {
        return this.popup;
    }
}
