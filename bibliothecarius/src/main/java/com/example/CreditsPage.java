package com.example;

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

public class CreditsPage {
    private final ClientTester app;

    public CreditsPage(ClientTester app) {
        this.app = app;
    }

    public void show(Stage stage) {
        Text title = new Text("CREDITS");
        title.setFont(Font.font("Georgia", FontWeight.BOLD, 36));
        title.setFill(Color.WHEAT);
        title.setStyle("-fx-effect: dropshadow(gaussian, black, 10, 0.2, 0, 2);");
        String references = """
                DEVELOPERS:
                - Ömer Atay Gemalmayan
                - Doğa Otcu
                - Hale Betül Şahin
                - Melike Yümna Şahin
                - Ayşe Hüma Şimşek

                TECHNOLOGIES:
                - JavaFX (https://openjfx.io)
                - Java 17+
                - IDE: IntelliJ IDEA
                - DB Browser for SQLite

                IMAGES:
                - Taylor, Joe. “Collapse”. The Rookies, 31 May 2020
                  https://www.therookies.co/entries/5625

                Note: Certain visuals in this project were generated with the assistance
                of OpenAI’s DALL·E image generation system.
                Source: https://openai.com/dall-e
                """;

        Text referencesText = new Text(references);
        referencesText.setFont(Font.font("Georgia", 14));
        referencesText.setFill(Color.WHITE);
        referencesText.setStyle("-fx-effect: dropshadow(gaussian, black, 4, 0.1, 0, 1);");
        Button returnButton = new Button("Return");
        returnButton.setOnAction(e -> {
            app.showGameStartPage();
        });
        styleButton(returnButton);

        VBox vbox = new VBox(20, title, referencesText, returnButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setMaxWidth(700);
        vbox.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0.6), new CornerRadii(12), null)));

        StackPane root = new StackPane(vbox);
        setBackground(root, getClass().getResource("/images/2025-04-07_23-11-34.jpg").toString());

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Credits");
        stage.show();
    }

    private void styleButton(Button button) {
        button.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        button.setStyle(
            "-fx-background-color: rgba(50, 50, 50, 0.8);" +
            "-fx-text-fill: white;" +
            "-fx-padding: 10px 20px;" +
            "-fx-background-radius: 10;" +
            "-fx-effect: dropshadow(gaussian, black, 4, 0.1, 0, 1);"
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