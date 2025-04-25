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

public class AddFriendsPage {
    private final BibliothecariusStart app;

    public AddFriendsPage(BibliothecariusStart app) {
        this.app = app;
    }

    public void show(Stage stage) {
        Text title = new Text("Add Friends");
        title.setFont(Font.font("Georgia", 40));
        title.setFill(Color.GOLD);

        String[] onlineUsers = {"Alice", "Bob", "Charlie", "Dave", "Eve"};

        VBox friendsList = new VBox(10);
        friendsList.setAlignment(Pos.CENTER);

        for (String user : onlineUsers) {
            if (!app.addedFriends.contains(user)) {
                Button addButton = new Button("Add " + user);
                styleButton(addButton);
                addButton.setOnAction(e -> {
                    app.addedFriends.add(user);
                    System.out.println(user + " added to friend list.");
                    app.showAddFriendsPage(); // refresh
                });
                friendsList.getChildren().add(addButton);
            }
        }

        Button backButton = new Button("Back");
        styleButton(backButton);
        backButton.setOnAction(e -> app.showGameStartPage());

        VBox layout = new VBox(20, title, friendsList, backButton);
        layout.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(layout);
        setBackground(root, "file:joe-taylor-collapse-render-2.jpg");

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
    }

    private void styleButton(Button button) {
        button.setStyle(
            "-fx-font-size: 16px; " +
            "-fx-background-color: rgba(50, 50, 50, 0.8); " +
            "-fx-text-fill: white; " +
            "-fx-padding: 10px 20px; " +
            "-fx-background-radius: 10;"
        );
    }

    private void setBackground(Pane pane, String imagePath) {
        Image backgroundImage = new Image(imagePath);
        BackgroundImage background = new BackgroundImage(
            backgroundImage,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(100, 100, true, true, false, true)
        );
        pane.setBackground(new Background(background));
    }
}
