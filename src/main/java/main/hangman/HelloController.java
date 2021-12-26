package main.hangman;

import exceptions.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import read.DictMaker;
import read.ReadDicts;

public class HelloController {
    @FXML
    private Text welcomeText1;
    @FXML
    private Text welcomeText2;
    @FXML
    private Text information;

    @FXML
    private Button welcomeButton;

    @FXML
    private TextField idField;

    private void hideText(Text text) {
        text.setVisible(false);
        text.managedProperty().bind(text.visibleProperty());
    }
    private void hideTextField(TextField field) {
        field.setVisible(false);
        field.managedProperty().bind(field.visibleProperty());
    }
    private void hideButton(Button button) {
        button.setVisible(false);
        button.managedProperty().bind(button.visibleProperty());
    }

    @FXML
    protected void onHelloButtonClick() throws InvalidCountException, InvalidRangeException, UndersizeException, NotFoundException, UnbalancedException {
        String ID_chosen = idField.getText(); // OL45883W
        String BASEURL = "https://openlibrary.org/works/";

        /*// read json and generate the dictionaries
        // read the words from the dictionaries and pick one
        try {
            DictMaker dict = new DictMaker(ID_chosen, BASEURL);
            dict.write();
            ReadDicts reader = new ReadDicts("medialab");
            String word = reader.pickWord();

            hideText(welcomeText1);
            hideText(welcomeText2);
            information.setText("Chosen word: " + word + " from dictionary with id " + ID_chosen);
        }
        catch (InvalidCountException|InvalidRangeException|UndersizeException|UnbalancedException|NotFoundException e) {
            information.setText("Something went wrong with this ID, please try another one.");
        }*/
    }
}