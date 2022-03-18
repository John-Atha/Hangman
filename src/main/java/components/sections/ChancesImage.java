package components.sections;

import helpers.MyStyles;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import main.hangman.Game;

import java.io.File;
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
            File directory = new File("./");
            String current_dir = directory.getAbsolutePath();
            System.out.println("current dir:" + current_dir);
            String images_path = null;
            if (current_dir.contains("Hangman")) {
                images_path = "src/main/resources/images/";
            }
            else {
                images_path = "Hangman/src/main/resources/images/";
            }
            System.out.println("images path:" + images_path);
            String image_ = images_path + (7-chances) + ".jpg";
            System.out.println(image_);
            FileInputStream fileStream = new FileInputStream(image_);
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

        if (this.game.isPlaying()) {
            this.vBox.getChildren().add(this.text);
            this.vBox.getChildren().add(this.imageView);
        }
    }

    public VBox getVBox() {
        return this.vBox;
    }

}
