package components.sections;

import helpers.MyStyles;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import main.hangman.Game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ChancesImage extends UpdatableSection {
    private Game game;
    private Image image;

    @FXML
    private Label text;
    @FXML
    private ImageView imageView;
    @FXML
    private VBox vBox;

    public ChancesImage(Game game) {
        this.vBox = new VBox();
        this.vBox.setPadding(new Insets(10));
        this.vBox.setSpacing(8);
        this.setGame(game);
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
        int chances = this.game.getChances_remaining();

        try {
            FileInputStream fileStream = new FileInputStream("src/main/resources/images/" + (7-chances) + ".jpg");
            this.image = new Image(fileStream);
            this.imageView = new ImageView(this.image);
        }
        catch (FileNotFoundException e) {
            this.image = null;
            this.imageView = new ImageView();
            System.out.println("Image not found: " + "src/main/resources/images/" + (7-chances) + ".jpg");
        }

        this.text = new Label(chances + " chances remaining");
        this.text.setStyle(MyStyles.title);

        this.vBox.getChildren().clear();

        if (this.game.getWord()!=null) {
            this.vBox.getChildren().add(this.text);
            this.vBox.getChildren().add(this.imageView);
        }
    }

    public VBox getVBox() {
        return this.vBox;
    }

}
