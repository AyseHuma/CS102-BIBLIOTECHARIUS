import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameStartPage {
    private final BibliothecariusStart app;

    public GameStartPage(BibliothecariusStart app) {
        this.app = app;
    }

    public void show(Stage stage) {
        Text title = new Text("Bibliothecarius");
        title.setFont(Font.font("Georgia", 60));
        title.setFill(Color.GOLD);
        title.setStyle("-fx-effect: dropshadow(gaussian, black, 10, 0, 0, 2);");

        Button startButton = new Button("Start Game");
        Button friendsButton = new Button("Add Friends");
        Button tutorialButton = new Button("Tutorial");
        Button settingsButton = new Button("Settings");
        Button creditsButton = new Button("Credits");
        Button returnButton = new Button("Return");

        style(startButton, friendsButton, tutorialButton, settingsButton, creditsButton, returnButton);

        startButton.setOnAction(e -> app.showCatalogPage());
        friendsButton.setOnAction(e -> app.showAddFriendsPage());
        tutorialButton.setOnAction(e -> app.endGame(app.selectedCategory, app.player1Name, app.player1Points, app.player2Name, app.player2Points));

        VBox vbox = new VBox(20, title, startButton, friendsButton, tutorialButton, settingsButton, creditsButton, returnButton);
        vbox.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(vbox);
        setBackground(root, "file:joe-taylor-collapse-render-1.jpg");

        stage.setScene(new Scene(root, 800, 600));
    }

    private void style(Button... buttons) {
        for (Button b : buttons) {
            b.setStyle("-fx-font-size: 16px; -fx-background-color: rgba(50, 50, 50, 0.8); -fx-text-fill: white; -fx-padding: 10px 20px; -fx-background-radius: 10;");
        }
    }

    private void setBackground(Pane pane, String imagePath) {
        pane.setBackground(new Background(new BackgroundImage(new Image(imagePath), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, false, true))));
    }
}
