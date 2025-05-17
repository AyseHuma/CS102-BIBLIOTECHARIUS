package com.example;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LeaderboardPage {

    private final ClientTester app;
    private final String leaderboardString;

    public LeaderboardPage(ClientTester app, String leaderboardString) {
        this.app = app;
        this.leaderboardString = leaderboardString;
    }

    public void show(Stage stage) {
        Text title = new Text("Leaderboard");
        title.setFont(Font.font("Georgia", FontWeight.BOLD, 50));
        title.setFill(Color.GOLD);
        title.setStyle("-fx-effect: dropshadow(gaussian, black, 5, 0.5, 0, 0);");
        String formattedLeaderboard = leaderboardString.replace("*", "\n");
        Label leaderboardLabel = new Label(formattedLeaderboard);
        leaderboardLabel.setFont(Font.font("Consolas", 16));
        leaderboardLabel.setTextFill(Color.WHITE);
        leaderboardLabel.setWrapText(true);
        ScrollPane scrollPane = new ScrollPane(leaderboardLabel);
        scrollPane.setPrefHeight(300);
        scrollPane.setMaxWidth(500);
        scrollPane.setStyle("-fx-background: rgba(0,0,0,0.5); -fx-background-color: transparent;");
        scrollPane.setFitToWidth(true);
        Button returnButton = new Button("Return");
        returnButton.setOnAction(e -> app.showGameStartPage());
        styleButton(returnButton);
        VBox vbox = new VBox(30, title, scrollPane, returnButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(40));
        vbox.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0.5), new CornerRadii(15), Insets.EMPTY)));
        StackPane root = new StackPane(vbox);
        setBackground(root, getClass().getResource("/images/2025-04-07_23-11-34.jpg").toString());
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
    }

    private void styleButton(Button button) {
        button.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 16));
        button.setStyle(
            "-fx-background-color: linear-gradient(to right, #444, #222);" +
            "-fx-text-fill: white;" +
            "-fx-padding: 10px 25px;" +
            "-fx-background-radius: 12px;" +
            "-fx-effect: dropshadow(gaussian, black, 5, 0.3, 0, 2);"
        );
    }

    private void setBackground(Pane pane, String imagePath) {
        pane.setBackground(new Background(new BackgroundImage(
            new Image(imagePath, 800, 600, false, true),
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(100, 100, true, true, false, true)
        )));
    }
}