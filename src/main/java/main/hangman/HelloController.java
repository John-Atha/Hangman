package main.hangman;

import exceptions.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import read.DictMaker;
import read.ReadDicts;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() throws InvalidCountException, InvalidRangeException, UndersizeException, NotFoundException, UnbalancedException {
        String ID = "OL45883W";
        String BASEURL = "https://openlibrary.org/works/";

        // read json and generate the dictionaries
        DictMaker dict = new DictMaker(ID, BASEURL);
        dict.write();

        // read the words from the dictionaries and pick one
        ReadDicts reader = new ReadDicts("medialab");
        String word = reader.pickWord();
        welcomeText.setText("Welcome to JavaFX Application!");
        welcomeText.setText("Chosen word: " + word);

    }
}