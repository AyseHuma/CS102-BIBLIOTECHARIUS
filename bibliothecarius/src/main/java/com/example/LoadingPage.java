package com.example;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoadingPage {

    private final ClientTester app;

    public LoadingPage(ClientTester app) {
        this.app = app;
    }

    public void show(Stage stage) {
        // Title
        Text title = new Text("Waiting for Matchmaking or Question Formation...");
        title.setFont(Font.font("Georgia", FontWeight.BOLD, 32));
        title.setFill(Color.WHEAT);
        title.setStyle("-fx-effect: dropshadow(gaussian, black, 10, 0.5, 0, 2);");

        // Subtext
        Text subtext = new Text("Please do not exit");
        subtext.setFont(Font.font("Georgia", FontWeight.SEMI_BOLD, 24));
        subtext.setFill(Color.WHITE);
        subtext.setStyle("-fx-effect: dropshadow(gaussian, black, 10, 0.3, 0, 2);");

        // Cancel Button
        Button cancelButton = new Button("Cancel Matchmaking");
        cancelButton.setOnAction(e -> {
            app.sendCancelMatchmakingRequest();
            app.showCatalogPage();
        });
        styleButton(cancelButton);
        VBox overlay = new VBox(30, title, subtext, cancelButton);
        overlay.setAlignment(Pos.CENTER);
        overlay.setPadding(new Insets(40));
        overlay.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0.6), new CornerRadii(15), Insets.EMPTY)));
        StackPane root = new StackPane(overlay);
        setBackground(root, getClass().getResource("/images/2025-04-07_23-11-34.jpg").toString());
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
    }

    private void styleButton(Button button) {
        button.setFont(Font.font("Arial", FontWeight.MEDIUM, 16));
        button.setStyle(
            "-fx-background-color: linear-gradient(to right, #555, #222);" +
            "-fx-text-fill: white;" +
            "-fx-padding: 12px 25px;" +
            "-fx-background-radius: 12px;" +
            "-fx-effect: dropshadow(gaussian, black, 6, 0.4, 0, 2);"
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