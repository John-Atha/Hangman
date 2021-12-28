package components.popups;

import helpers.MyStyles;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ErrorPopUp {
    private String message;

    @FXML
    private Stage popup;
    @FXML
    private VBox vBox;
    @FXML
    private Label text;

    public ErrorPopUp(String message) {
        this.vBox = new VBox();
        this.setMessage(message);
    }

    public void setMessage(String message) {
        this.popup = new Stage();
        this.message = message;
        this.text = new Label(message);
        this.text.setStyle(MyStyles.label);
        this.vBox.getChildren().clear();
        this.vBox.getChildren().add(this.text);
        this.vBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vBox, 800, 600);
        this.popup.setTitle("Error");
        this.popup.setScene(scene);
    }

    public Stage getPopUp() {
        return this.popup;
    }
}
