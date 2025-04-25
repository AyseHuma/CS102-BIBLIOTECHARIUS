import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PlayWithFriendsPage {
    private final BibliothecariusStart app;

    public PlayWithFriendsPage(BibliothecariusStart app) {
        this.app = app;
    }

    public void show(Stage stage) {
        Text title = new Text("Play With a Friend");
        title.setFont(Font.font("Georgia", 40));
        title.setFill(Color.GOLD);

        VBox friendButtons = new VBox(10);
        friendButtons.setAlignment(Pos.CENTER);

        if (app.addedFriends.isEmpty()) {
            Label noFriends = new Label("No friends added yet.");
            noFriends.setTextFill(Color.WHITE);
            noFriends.setFont(Font.font("Arial", 16));
            friendButtons.getChildren().add(noFriends);
        } else {
            for (String friend : app.addedFriends) {
                Button friendButton = new Button("Play with " + friend);
                styleButton(friendButton);
                friendButton.setOnAction(e -> app.showQuestionPage(app.selectedCategory));
                friendButtons.getChildren().add(friendButton);
            }
        }

        Button backButton = new Button("Back");
        styleButton(backButton);
        backButton.setOnAction(e -> app.showComponentPage());

        VBox layout = new VBox(30, title, friendButtons, backButton);
        layout.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(layout);
        setBackground(root, "file:joe-taylor-collapse-render-2.jpg");

        stage.setScene(new Scene(root, 800, 600));
    }

    private void styleButton(Button button) {
        button.setStyle("-fx-font-size: 16px; -fx-background-color: rgba(50, 50, 50, 0.8); -fx-text-fill: white; -fx-padding: 10px 20px; -fx-background-radius: 10;");
    }

    private void setBackground(Pane pane, String imagePath) {
        pane.setBackground(new Background(new BackgroundImage(new Image(imagePath), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, false, true))));
    }
}
