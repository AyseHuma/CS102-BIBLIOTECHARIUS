package com.example;

import java.sql.SQLException;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class FriendRequestPage {

    private final ClientTester app;
    private final String currentPlayer;
    private String s; 

    public FriendRequestPage(ClientTester app, String s) {
        this.app = app;
        this.currentPlayer = app.myUsername; // Make sure this exists
        this.s = s;
    }

    public void show(Stage stage) {

        // Text title = new Text("Requests");
        // title.setFont(Font.font("Georgia", 50));
        // title.setFill(Color.GOLD);

        // Button returnButton = new Button("Return");
        // returnButton.setOnAction(e -> app.showGameStartPage());
        // style(returnButton);
        
        // // String s = leaderboardString.replace("*", "\n");
        // TextArea leaderboardLabel = new TextArea(s);
        // leaderboardLabel.setEditable(false);

        // VBox vbox = new VBox(20, title, leaderboardLabel, returnButton);
        // vbox.setAlignment(Pos.CENTER);
        // StackPane root = new StackPane(vbox);
        // setBackground(root, getClass().getResource("/images/2025-04-07_23-11-34.jpg").toString());
        // stage.setScene(new Scene(root, 800, 600));

        VBox vbox = new VBox(15);
        vbox.setAlignment(Pos.CENTER);



        if (s == null || s.isEmpty()) {
            Text title = new Text("No pending friend requests.");
            title.setFont(Font.font("Georgia", 30));
            title.setFill(Color.GOLD);

            vbox.getChildren().add(title);
        } else {
            int start = 0;
            while (start < s.length()) {
                int starIndex = s.indexOf("*", start);
                String entry;
                if (starIndex == -1) {
                    entry = s.substring(start);
                    start = s.length();
                } else {
                    entry = s.substring(start, starIndex);
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
                        
                            app.sendAcceptFriendRequest(currentPlayer, senderId);
                            app.sendGetFriendRequests();; // Refresh the page

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
