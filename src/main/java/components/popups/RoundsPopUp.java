package components.popups;

import helpers.HeaderState;
import helpers.MyStyles;
import helpers.Round;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RoundsPopUp {

    String textStyling = "-fx-font-size: 30px;";

    @FXML
    private TableView table;

    @FXML
    private Label title;

    @FXML
    private Stage popup;

    @FXML
    private VBox container;

    @FXML
    private Text message;

    private ArrayList<Round> rounds;

    public RoundsPopUp(ArrayList<ArrayList<String>> rounds) {
        this.container = new VBox();
        this.container.setPadding(new Insets(10));
        this.container.setSpacing(8);
        this.popup = new Stage();
        this.table = new TableView();
        popup.setTitle("Rounds");
        this.setRounds(rounds);
    }

    private void setRounds(ArrayList<ArrayList<String>> rounds) {
        if (rounds.size()==0) {
            this.message = new Text("No rounds played till now.");
            this.message.setStyle(MyStyles.error);
            this.container.getChildren().add(this.message);
        }
        else {

            TableColumn<Round, String> column1 = new TableColumn<>("Hidden Word");
            column1.setCellValueFactory(new PropertyValueFactory<>("word"));
            TableColumn<Round, Integer> column2 = new TableColumn<>("Number of guesses");
            column2.setCellValueFactory(new PropertyValueFactory<>("tries"));
            TableColumn<Round, String> column3 = new TableColumn<>("Winner");
            column3.setCellValueFactory(new PropertyValueFactory<>("winner"));

            this.table.getColumns().clear();
            this.table.getColumns().add(column1);
            this.table.getColumns().add(column2);
            this.table.getColumns().add(column3);

            ArrayList<ArrayList<String>> last_rounds;
            if (rounds.size()>=5) {
                last_rounds = new ArrayList<ArrayList<String>>(rounds.subList(rounds.size()-5, rounds.size()));
            }
            else {
                last_rounds = new ArrayList<ArrayList<String>>(rounds);
            }

            for (int i=last_rounds.size()-1; i>=0; i--) {
                ArrayList<String> curr = last_rounds.get(i);
                Round new_round = new Round(curr.get(0), Integer.parseInt(curr.get(1)), curr.get(2));
                this.table.getItems().add(new_round);
            }

            column1.setStyle(MyStyles.tableColumn);
            column2.setStyle(MyStyles.tableColumn);
            column3.setStyle(MyStyles.tableColumn);

            column1.prefWidthProperty().bind(this.table.widthProperty().multiply(0.4));
            column2.prefWidthProperty().bind(this.table.widthProperty().multiply(0.2));
            column3.prefWidthProperty().bind(this.table.widthProperty().multiply(0.35));

            this.table.setStyle(MyStyles.tableColumn);
            this.table.setPrefHeight(1000);

            this.title = new Label("Last 5 rounds statistics");
            this.title.setStyle(MyStyles.title);

            this.container.getChildren().add(this.title);
            this.container.getChildren().add(this.table);
        }

        this.container.setAlignment(Pos.CENTER);
        Scene scene = new Scene(container, 1800, 1000);
        popup.setScene(scene);
    }

    public Stage getPopUp() {
        return this.popup;
    }

}
