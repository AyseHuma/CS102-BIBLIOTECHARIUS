import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class QuestionPage {
    private final BibliothecariusStart app;

    public QuestionPage(BibliothecariusStart app) {
        this.app = app;
    }

    public void show(Stage stage, String category) {
        Text title = new Text("Question Time!");
        title.setFont(Font.font("Georgia", 40));
        title.setFill(Color.GOLD);

        Label timerLabel = new Label("Time: 10s");
        timerLabel.setFont(Font.font("Arial", 20));
        timerLabel.setTextFill(Color.RED);

        Label player1Score = new Label("Player 1: 0 pts");
        Label player2Score = new Label("Player 2: 0 pts");
        player1Score.setFont(Font.font("Arial", 18));
        player2Score.setFont(Font.font("Arial", 18));
        player1Score.setTextFill(Color.WHITE);
        player2Score.setTextFill(Color.WHITE);

        ImageView questionImage = new ImageView(new Image("file:p20224_v_h9_ap.jpg"));
        questionImage.setFitWidth(300);
        questionImage.setFitHeight(200);

        Text questionText = new Text("When was Good Will Hunting released?");
        questionText.setFont(Font.font("Arial", 22));
        questionText.setFill(Color.WHITE);

        Button answer1 = new Button("2019");
        Button answer2 = new Button("1998");
        Button answer3 = new Button("1975");
        Button answer4 = new Button("2000");

        style(answer1, answer2, answer3, answer4);

        answer1.setOnAction(e -> app.endGame(category, app.player1Name, app.player1Points, app.player2Name, app.player2Points));

        VBox leftBox = new VBox(20, answer1, answer3);
        VBox rightBox = new VBox(20, answer2, answer4);
        HBox answersBox = new HBox(60, leftBox, rightBox);
        leftBox.setAlignment(Pos.CENTER_LEFT);
        rightBox.setAlignment(Pos.CENTER_RIGHT);
        answersBox.setAlignment(Pos.CENTER);

        HBox scoreBox = new HBox(20, player1Score, timerLabel, player2Score);
        scoreBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(30, title, scoreBox, questionImage, questionText, answersBox);
        layout.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(layout);
        setBackground(root, "file:joe-taylor-collapse-render-2.jpg");

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
