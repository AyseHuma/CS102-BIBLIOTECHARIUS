package com.example;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EndGamePage {
    private final ClientTester app;

    public EndGamePage(ClientTester app) {
        this.app = app;
    }

    public void show(Stage stage, String category, String player1Name, int player1Points, String player2Name, int player2Points) {
        Text title = new Text("Game Over - " + category);
        title.setFont(Font.font("Georgia", 36));
        title.setFill(Color.GOLD);

        Text scoreHeader = new Text("Final Scores");
        scoreHeader.setFont(Font.font("Arial", 28));
        LinearGradient gradient = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
            new Stop(0, Color.rgb(122, 137, 83)), new Stop(1, Color.GOLD));
        scoreHeader.setFill(gradient);
        scoreHeader.setStyle("-fx-effect: dropshadow(gaussian,rgb(159, 131, 40), 6, 0, 0, 5);");
        scoreHeader.setStroke(Color.DARKGREEN);
        scoreHeader.setStrokeWidth(1);

        Text player1Text = new Text(player1Name + ": " + player1Points + " points");
        Text player2Text = new Text(player2Name + ": " + player2Points + " points");

        player1Text.setFont(Font.font("Georgia", 22));
        player2Text.setFont(Font.font("Georgia", 22));
        player1Text.setFill(Color.BEIGE);
        player2Text.setFill(Color.BEIGE);

        Button newGameButton = new Button("New Game");
        Button leaderboardButton = new Button("View Leaderboard");
        styleButton(newGameButton);
        styleButton(leaderboardButton);

        newGameButton.setOnAction(e -> app.showGameStartPage());
        leaderboardButton.setOnAction(e -> app.showMainPage());

        HBox playerInfoBox = new HBox(50, player1Text, player2Text);
        playerInfoBox.setAlignment(Pos.CENTER);
        playerInfoBox.setPadding(new Insets(0, 50, 0, 50));

        VBox buttonBox = new VBox(20, newGameButton, leaderboardButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(25, title, scoreHeader, playerInfoBox, buttonBox);
        layout.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(layout);
        setBackground(root, getClass().getResource("/images/2025-04-07_23-12-42.jpg").toString());

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);

        // Leaderboard update TODO 
        // app.addOrUpdatePlayerScore(player1Name, player1Points);
        // app.addOrUpdatePlayerScore(player2Name, player2Points);
        // app.saveLeaderboardToFile();
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

