package components;

import exceptions.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import read.DictMaker;
import read.ReadDicts;

public class CreateDictPopUp {

    @FXML
    private Label label1;
    @FXML
    private TextField dictField;
    @FXML
    private Label label2;
    @FXML
    private TextField idField;
    @FXML
    private Button submit_button;
    @FXML
    private Text message;
    @FXML
    private Stage popup;
    @FXML
    private VBox vBox;

    public CreateDictPopUp() {

        String textFieldsStyling =
                "-fx-font-size: 30px;" +
                "-fx-border-insets: 10px;" +
                "-fx-background-insets: 10px;"+
                "-fx-max-width: 500px";

        String labelsStyling =
                "-fx-font-size: 30px;";
        this.popup = new Stage();
        popup.setTitle("Create dictionary");
        this.vBox = new VBox();

        this.label1 = new Label("Insert the id of the dictionary you would like to create.");
        this.label2 = new Label("Insert the id of the open library book you would like to use as source.");
        this.submit_button = new Button("Submit");
        this.dictField = new TextField();
        this.idField = new TextField();
        this.message = new Text();

        this.vBox.getChildren().add(this.label1);
        this.vBox.getChildren().add(this.dictField);
        this.vBox.getChildren().add(this.label2);
        this.vBox.getChildren().add(this.idField);
        this.vBox.getChildren().add(this.submit_button);
        this.vBox.getChildren().add(this.message);

        this.vBox.setStyle(
                "-fx-padding: 10px"
        );

        this.label1.setStyle(labelsStyling);
        this.label2.setStyle(labelsStyling);

        this.idField.setStyle(textFieldsStyling);
        this.dictField.setStyle(textFieldsStyling);

        this.submit_button.setStyle(
                "-fx-font-size: 30px;" +
                "-fx-border-insets: 10px;" +
                "-fx-background-insets: 10px;"
        );

        this.submit_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                submit();
            }
        });

        Scene scene = new Scene(vBox, 600, 400);
        popup.setScene(scene);
    }

    public Stage getPopup() {
        return this.popup;
    }

    private void submit() {
        String ID_dict = dictField.getText();
        String ID_chosen = idField.getText(); // OL45883W
        String BASEURL = "https://openlibrary.org/works/";

        // read json and generate the dictionaries
        // read the words from the dictionaries and pick one
        try {
            DictMaker dict = new DictMaker(ID_chosen, BASEURL, ID_dict);
            dict.write();

            this.message.setText("Built dictionary with id " + ID_chosen + " successfully.");
            this.message.setStyle("-fx-font-size: 30px; -fx-fill: green;");

        }
        catch (InvalidCountException | InvalidRangeException | UndersizeException | UnbalancedException | NotFoundException e) {
            this.message.setText(e.getMessage() + ", please try another URL.");
            this.message.setStyle("-fx-font-size: 30px; -fx-fill: red;");
        }
    }
}
