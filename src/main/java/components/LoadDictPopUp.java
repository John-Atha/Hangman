package components;

import exceptions.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import read.DictMaker;
import read.DictReader;

import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class LoadDictPopUp {

    @FXML
    private Label label1;
    @FXML
    private TextField dictField;
    @FXML
    private Button submit_button;
    @FXML
    private Text message;
    @FXML
    private Stage popup;
    @FXML
    private VBox vBox;

    private ArrayList<String> words;

    public LoadDictPopUp(ArrayList<String> words) {
        this.words = words;

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

        this.label1 = new Label("Insert the id of the dictionary you would like to load.");
        this.submit_button = new Button("Submit");
        this.dictField = new TextField();
        this.message = new Text();

        this.vBox.getChildren().add(this.label1);
        this.vBox.getChildren().add(this.dictField);
        this.vBox.getChildren().add(this.submit_button);
        this.vBox.getChildren().add(this.message);

        this.vBox.setStyle(
                "-fx-padding: 10px"
        );

        this.label1.setStyle(labelsStyling);

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

    public ArrayList<String> getWords() {
        return this.words;
    }

    private void submit() {
        String ID_dict = dictField.getText();

        // read the words from the dictionaries and pick one
        DictReader reader = new DictReader(ID_dict);

        try {
            reader.read();
            this.words.addAll(reader.getWords());
            this.message.setText("Read dictionary with id " + ID_dict + " successfully.");
            this.message.setStyle("-fx-font-size: 30px; -fx-fill: green;");

        }
        catch (FileNotFoundException e) {
            this.message.setText("Dictionary " +  reader.getName() + " not found, please try another ID");
            this.message.setStyle("-fx-font-size: 30px; -fx-fill: red;");
        }
    }
}
