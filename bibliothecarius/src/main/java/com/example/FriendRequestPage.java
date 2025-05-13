package com.example;

import java.sql.SQLException;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class FriendRequestPage {

    private final ClientTester app;
    private final String currentPlayer;

    public FriendRequestPage(ClientTester app) {
        this.app = app;
        this.currentPlayer = app.myUsername; // Make sure this exists
    }

    public void show(Stage stage) {
        VBox vbox = new VBox(15);
        vbox.setAlignment(Pos.CENTER);

        String requestString = "";
        try {
            requestString = AccountDb.getPendingRequests(currentPlayer);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (requestString == null || requestString.isEmpty()) {
            Label none = new Label("No pending friend requests.");
            vbox.getChildren().add(none);
        } else {
            int start = 0;
            while (start < requestString.length()) {
                int starIndex = requestString.indexOf("*", start);
                String entry;
                if (starIndex == -1) {
                    entry = requestString.substring(start);
                    start = requestString.length();
                } else {
                    entry = requestString.substring(start, starIndex);
                    start = starIndex + 1;
                }

                // entry = "7:Alice"
                int colonIndex = entry.indexOf(":");
                if (colonIndex != -1) {
                    String idStr = entry.substring(0, colonIndex);
                    String name = entry.substring(colonIndex + 1);
                    int senderId = Integer.parseInt(idStr);

                    Button label = new Button("Request from " + name + " (ID: " + senderId + ")");
                    Button acceptButton = new Button("Accept");
                    style(acceptButton, label);

                    acceptButton.setOnAction(e -> {
                        try {
                            AccountDb.acceptFriendRequest(currentPlayer, senderId);
                            app.showFriendRequestPage(); // Refresh the page
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    });

                    HBox hbox = new HBox(10, label, acceptButton);
                    hbox.setAlignment(Pos.CENTER);
                    vbox.getChildren().add(hbox);
                }
            }
        }

        Button returnButton = new Button("Return");
        returnButton.setOnAction(e -> app.showGameStartPage());
        style(returnButton);
        vbox.getChildren().add(returnButton);

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
        pane.setBackground(new Background(new BackgroundImage(
            new Image(imagePath), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, false, true))));
    }
}
