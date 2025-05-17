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

public class ComponentChoicePage {
    private final ClientTester app;
    private String category; 
    private String subcat; 

    public ComponentChoicePage(ClientTester app, String category, String subcat) {
        this.app = app;
        this.category = category;
        this.subcat = subcat; 
    }

    public void show(Stage stage) {
        Text title = new Text("How Would You Like To Play?\n Category : " + category);
        title.setFont(Font.font("Georgia", 50));
        title.setFill(Color.WHEAT);
        title.setStyle("-fx-effect: dropshadow(gaussian, black, 10, 0, 0, 2);");

        Button randomButton = new Button("Match Randomly");
        Button friendsButton = new Button("Play With Friend");

        style(randomButton, friendsButton);

        randomButton.setOnAction(e -> {app.sendMatchRequest(category, subcat); app.showLoadingPage();});

        friendsButton.setOnAction(e -> {app.showLoadingPage(); app.sendMatchRequestToFriend(category, subcat); });
        VBox vbox = new VBox(20, title, randomButton, friendsButton);
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

