package components;

import exceptions.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import read.DictMaker;

public class CreateDictPopUp {

    @FXML
    private Label title;

    @FXML
    private HBox hBox1;
    @FXML
    private Label label1;
    @FXML
    private TextField dictField;

    @FXML
    private HBox hBox2;
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

        this.popup = new Stage();
        popup.setTitle("Create dictionary");
        this.vBox = new VBox();
        this.hBox1 = new HBox();
        this.hBox2 = new HBox();

        this.title = new Label("Create");
        this.label1 = new Label("Insert the id of the dictionary you would like to create. ");
        this.label2 = new Label("Insert the id of the book you would like to use as source.");
        this.submit_button = new Button("Submit");
        this.dictField = new TextField();
        this.idField = new TextField();
        this.message = new Text();

        this.vBox.getChildren().add(this.title);

        this.hBox1.getChildren().add(this.label1);
        this.hBox1.getChildren().add(this.dictField);
        this.vBox.getChildren().add(hBox1);

        this.hBox2.getChildren().add(this.label2);
        this.hBox2.getChildren().add(this.idField);
        this.vBox.getChildren().add(hBox2);

        this.vBox.getChildren().add(this.submit_button);
        this.vBox.getChildren().add(this.message);

        this.vBox.setStyle(
                "-fx-padding: 100px 10px 0px 10px"
        );

        this.vBox.setAlignment(Pos.TOP_CENTER);
        this.hBox1.setAlignment(Pos.CENTER);
        this.hBox2.setAlignment(Pos.CENTER);

        this.title.setPadding(new Insets(40));
        this.title.setStyle(MyStyles.title);
        this.label1.setStyle(MyStyles.label);
        this.label2.setStyle(MyStyles.label);

        this.idField.setStyle(MyStyles.textField);
        this.dictField.setStyle(MyStyles.textField);

        this.submit_button.setStyle(MyStyles.button);

        this.submit_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                submit();
            }
        });

        Scene scene = new Scene(vBox, 1800, 1000);
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
        try {
            DictMaker dict = new DictMaker(ID_chosen, BASEURL, ID_dict);
            dict.write();

            this.message.setText("Built dictionary with id " + ID_chosen + " successfully.");
            this.message.setStyle(MyStyles.success);

        }
        catch (InvalidCountException | InvalidRangeException | UndersizeException | UnbalancedException | NotFoundException e) {
            this.message.setText(e.getMessage() + ", please try another URL.");
            this.message.setStyle(MyStyles.error);
        }
    }
}
