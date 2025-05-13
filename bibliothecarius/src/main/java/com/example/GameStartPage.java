package com.example;

import java.util.Timer;
import java.util.TimerTask;

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
    private final ClientTester app;

    public GameStartPage(ClientTester app) {
        this.app = app;
    }

    public void show(Stage stage) {
        Text title = new Text("Bibliothecarius");
        title.setFont(Font.font("Georgia", 60));
        title.setFill(Color.GOLD);
        title.setStyle("-fx-effect: dropshadow(gaussian, black, 10, 0, 0, 2);");

        Button startButton = new Button("Start Game");
        
        Button tutorialButton = new Button("Tutorial");
        Button friendsButton = new Button("Friend request");
        Button settingsButton = new Button("Settings");
        Button creditsButton = new Button("Credits");
        Button closeButton = new Button("Close");

        style(startButton, tutorialButton, settingsButton, creditsButton, closeButton, friendsButton);

        startButton.setOnAction(e -> app.showCatalogPage());
        friendsButton.setOnAction(e -> app.showFriendRequestPage());
       
        tutorialButton.setOnAction(e -> app.showMainPage());

        closeButton.setOnAction(e -> app.sendDisconnectRequest());

        VBox vbox = new VBox(20, title, startButton,friendsButton, tutorialButton, settingsButton, creditsButton, closeButton);
        vbox.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(vbox);
        setBackground(root, getClass().getResource("/images/joe-taylor-collapse-render-1.jpg").toString());

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
