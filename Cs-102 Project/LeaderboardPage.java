import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LeaderboardPage {
    private final BibliothecariusStart app;

    public LeaderboardPage(BibliothecariusStart app) {
        this.app = app;
    }

    public void show(Stage stage) {
        Text title = new Text("Leaderboard");
        title.setFont(Font.font("Georgia", 40));
        title.setFill(Color.GOLD);

        app.leaderboard.sort((a, b) -> Integer.compare(b.score, a.score));
        ObservableList<String> leaderboardData = FXCollections.observableArrayList();
        int rank = 1;
        for (PlayerScore ps : app.leaderboard) {
            leaderboardData.add(rank + ". " + ps);
            rank++;
        }

        ListView<String> leaderboardList = new ListView<>(leaderboardData);
        leaderboardList.setMaxSize(300, 250);

        Button backButton = new Button("Back to Catalog");
        styleButton(backButton);
        backButton.setOnAction(e -> app.showCatalogPage());

        VBox layout = new VBox(30, title, leaderboardList, backButton);
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
