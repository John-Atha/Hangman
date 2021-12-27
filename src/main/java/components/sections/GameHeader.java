package components.sections;

import helpers.HeaderState;
import helpers.MyStyles;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import main.hangman.Game;

public class GameHeader {

    @FXML
    private TableView table;

    @FXML
    private VBox vBox;

    @FXML
    private VBox subBox1;

    @FXML
    private VBox subBox2;

    @FXML
    private Text message;

    // private boolean created = false;

    private Game game;
    private HeaderState state;

    private BorderPane parent;

    public GameHeader(Game game, BorderPane parent) {
        this.vBox = new VBox();
        this.vBox.setPadding(new Insets(10));
        vBox.setSpacing(8);
        this.parent = parent;
        this.setGame(game, true);
    }

    public void setGame(Game game, boolean create) {
        this.game = game;
        if (create) {
            System.out.print("Creating the header... ");
            this.state = new HeaderState(game);
            this.table = new TableView<>();
        }
        else {
            System.out.print("Updating the header... ");
            this.state.setGame(game);
        }

        TableColumn<HeaderState, Integer> column1 = new TableColumn<>("Total words");
        column1.setCellValueFactory(new PropertyValueFactory<>("total_words"));
        TableColumn<HeaderState, Integer> column2 = new TableColumn<>("Words left");
        column2.setCellValueFactory(new PropertyValueFactory<>("words_left"));
        TableColumn<HeaderState, Integer> column3 = new TableColumn<>("Points");
        column3.setCellValueFactory(new PropertyValueFactory<>("points"));
        TableColumn<HeaderState, Integer> column4 = new TableColumn<>("Success Rate");
        column4.setCellValueFactory(new PropertyValueFactory<>("rate"));
        TableColumn<HeaderState, Integer> column5 = new TableColumn<>("Hidden word");
        column5.setCellValueFactory(new PropertyValueFactory<>("hidden_word"));

        this.table.getColumns().clear();
        this.table.getColumns().add(column1);
        this.table.getColumns().add(column2);
        this.table.getColumns().add(column3);
        this.table.getColumns().add(column4);
        this.table.getColumns().add(column5);

        this.table.getItems().clear();
        this.table.getItems().add(this.state);

        column1.setStyle(MyStyles.tableColumn);
        column2.setStyle(MyStyles.tableColumn);
        column3.setStyle(MyStyles.tableColumn);
        column4.setStyle(MyStyles.tableColumn);
        column5.setStyle(MyStyles.tableColumn);

        column1.prefWidthProperty().bind(this.table.widthProperty().multiply(0.199));
        column2.prefWidthProperty().bind(this.table.widthProperty().multiply(0.19));
        column3.prefWidthProperty().bind(this.table.widthProperty().multiply(0.199));
        column4.prefWidthProperty().bind(this.table.widthProperty().multiply(0.19));
        column5.prefWidthProperty().bind(this.table.widthProperty().multiply(0.19));


        this.message = new Text();

        this.subBox2 = new VBox();
        this.subBox2.getChildren().add(this.message);
        this.subBox1 = new VBox(this.table);

        this.subBox2.setAlignment(Pos.CENTER);
        this.subBox1.setPadding(new Insets(10));
        this.subBox2.setPadding(new Insets(10));

        this.vBox.getChildren().clear();
        this.vBox.getChildren().add(subBox1);
        this.vBox.getChildren().add(subBox2);

        this.message.setStyle(MyStyles.error);
        this.table.setStyle(MyStyles.tableColumn);

        this.table.setPrefHeight(200);
        this.parent.setTop(this.vBox);
    }

    public void setMessage(String message) {
        if (message.length()!=0) {
            this.table.getItems().clear();
        }
        this.message.setText(message);
        this.message.setStyle(MyStyles.error);
    }

    public VBox getVBox() {
        return this.vBox;
    }
}
