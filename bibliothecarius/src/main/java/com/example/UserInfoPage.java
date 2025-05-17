package com.example;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UserInfoPage {

    private final ClientTester app;
    private final String userInfoString;

    public UserInfoPage(ClientTester app, String infoString) {
        this.app = app;
        this.userInfoString = infoString;
    }

    public void show(Stage stage) {
        Text title = new Text("User Information");
        title.setFont(Font.font("Georgia", FontWeight.BOLD, 42));
        title.setFill(Color.GOLD);
        title.setStyle("-fx-effect: dropshadow(gaussian, black, 10, 0.4, 0, 2);");
        String formattedInfo = userInfoString.replace("*", "\n");
        TextArea infoArea = new TextArea(formattedInfo);
        infoArea.setEditable(false);
        infoArea.setWrapText(true);
        infoArea.setStyle(
            "-fx-control-inner-background: rgba(255,255,255,0.9);" +
            "-fx-font-size: 14px;" +
            "-fx-font-family: 'Consolas';" +
            "-fx-text-fill: black;" +
            "-fx-border-color: gold;" +
            "-fx-border-width: 2px;" +
            "-fx-background-radius: 10;" +
            "-fx-border-radius: 10;"
        );
        infoArea.setMaxWidth(600);
        infoArea.setPrefHeight(300);
        Button returnButton = new Button("Return");
        returnButton.setOnAction(e -> app.showGameStartPage());
        styleButton(returnButton);
        VBox vbox = new VBox(30, title, infoArea, returnButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(30));
        vbox.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0.6), new CornerRadii(12), Insets.EMPTY)));

        StackPane root = new StackPane(vbox);
        setBackground(root, getClass().getResource("/images/2025-04-07_23-11-34.jpg").toString());

        stage.setScene(new Scene(root, 800, 600));
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