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

public class CreditsPage {  
    private final ClientTester app;
 

    public CreditsPage(ClientTester app) {
        this.app = app;
    }

    public void show(Stage stage) {
        Text title = new Text("CREDITS");
        title.setFont(Font.font("Georgia", 50));
        title.setFill(Color.WHEAT);
        title.setStyle("-fx-effect: dropshadow(gaussian, black, 10, 0, 0, 2)");

        String references = """
                DEVELOPERS: [ÖMER ATAY GEMALMAYAN\n
                DOĞA OTCU\n
                HALE BETÜL ŞAHİN\n
                MELİKE YÜMNA ŞAHİN\n
                AYŞE HÜMA ŞİMŞEK\n
                ]
                TECHNOLOGIES:
                - JavaFX (https://openjfx.io)\n
                - Java 17+\n
                - IDE: IntelliJ IDEA\n
                - DB Browser for SQLite\n

                IMAGES:
                - Taylor, Joe.“Collapse”. The Rookies, 31 May 2020, https://www.therookies.co/entries/5625\n
                Taylor, Joe.“Collapse”. The Rookies, 31 May 2020, https://www.therookies.co/entries/5625\n
                Taylor, Joe.“Collapse”. The Rookies, 31 May 2020, https://www.therookies.co/entries/5625\n


                Note: Certain visuals in this project were generated with the assistance of OpenAI’s DALL·E image generation system.\n
                Source: https://openai.com/dall-e\n
                """;

        Text referencesText = new Text(references);
        referencesText.setFont(Font.font("Georgia", 16));
        referencesText.setFill(Color.WHITE);
        referencesText.setStyle("-fx-effect: dropshadow(gaussian, black, 5, 0, 0, 1)");

        Button creditsButton = new Button("Credits ");
        creditsButton.setOnAction(e -> app.showCreditsPage());
        style(creditsButton);

        VBox vbox = new VBox(20, title, referencesText, creditsButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setMaxWidth(700); 
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

