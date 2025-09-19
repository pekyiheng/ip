import java.io.IOException;
import java.util.Collections;

import hamlet.enums.Command;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;


/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's face
 * and a label containing text from the speaker.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    /**
     * Constructs a DialogBox object with the given text and image.
     *
     * @param text the dialog text
     * @param img the image to display
     */
    public DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
        dialog.getStyleClass().add("reply-label");
    }

    /**
     * Creates a dialog box styled as a user message.
     *
     * @param text the dialog text
     * @param img the image to display
     * @return a DialogBox for the user
     */
    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img);
    }

    /**
     * Creates a dialog box styled as a Hamlet response.
     *
     * @param text the dialog text
     * @param img the image to display
     * @param commandType the command type used to determine style
     * @return a DialogBox for Hamlet
     */
    public static DialogBox getHamletDialog(String text, Image img, Command commandType) {
        var db = new DialogBox(text, img);
        db.flip();
        db.changeDialogStyle(commandType);
        return db;
    }

    /**
     * Updates the dialog style based on the command type.
     *
     * @param commandType the type of command executed
     */
    private void changeDialogStyle(Command commandType) {
        switch (commandType) {
        case TODO:
        case DEADLINE:
        case EVENT:
            dialog.getStyleClass().add("add-label");
            break;
        case MARK:
        case UNMARK:
            dialog.getStyleClass().add("marked-label");
            break;
        case DELETE:
            dialog.getStyleClass().add("delete-label");
            break;
        default:
            // no styling applied
        }
    }
}
