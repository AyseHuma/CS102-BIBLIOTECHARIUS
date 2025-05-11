package com.example;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoadingPage {  // TODO maybe add a timer so that matchmaking times out
    private final ClientTester app;
    private String category; 

    public LoadingPage(ClientTester app) {
        this.app = app;
    }

    public void show(Stage stage) {
        Text title = new Text("WAITING FOR MATCHMAKING OR QUESTION FORMATION");
        Text t = new Text("Please do not exit");
        title.setFont(Font.font("Georgia", 50));
        t.setFont(Font.font("Georgia", 50));
        title.setFill(Color.WHEAT);
        t.setFill(Color.WHITE);
        title.setStyle("-fx-effect: dropshadow(gaussian, black, 10, 0, 0, 2)");
        t.setStyle("-fx-effect: dropshadow(gaussian, black, 10, 0, 0, 2)");

        Button returnButton = new Button("Cancel Matchmaking");
        returnButton.setOnAction(e -> {app.sendCancelMatchmakingRequest(); app.showCatalogPage();});
        style(returnButton);

        VBox vbox = new VBox(20, title, t, returnButton);
        vbox.setAlignment(Pos.CENTER);
        StackPane root = new StackPane(vbox);
        setBackground(root, getClass().getResource("/images/2025-04-07_23-11-34.jpg").toString());
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

