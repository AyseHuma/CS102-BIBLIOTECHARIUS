package com.example;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TutorialPage {  
    private final ClientTester app;

    public TutorialPage(ClientTester app) {
        this.app = app;
    }

    public void show(Stage stage) {
        Text title = new Text("HOW TO PLAY");
        title.setFont(Font.font("Georgia", 32)); // Smaller title
        title.setFill(Color.WHEAT);
        title.setStyle("-fx-effect: dropshadow(gaussian, black, 10, 0, 0, 2)");

        Font bodyFont = Font.font("Georgia", 14); // Smaller body font

        Text step1Text = new Text("1. If you already have an account please sign up with your username and password, if you don't create a new account with sign up!");
        step1Text.setFont(bodyFont);
        step1Text.setFill(Color.BLACK);

        ImageView step1Image = new ImageView(new Image(getClass().getResource("/images/2025-04-26_17-31-42.png").toString()));
        step1Image.setFitWidth(350);
        step1Image.setPreserveRatio(true);
        ImageView step12Image = new ImageView(new Image(getClass().getResource("/images/2025-04-26_17-32-53.png").toString()));
        step12Image.setFitWidth(350);
        step12Image.setPreserveRatio(true);

        Text step2Text = new Text("2. After signing in you can start a new game, add your friends or the tutorial.");
        step2Text.setFont(bodyFont);
        step2Text.setFill(Color.BLACK);

        ImageView step2Image = new ImageView(new Image(getClass().getResource("/images/2025-04-26_17-33-16.png").toString()));
        step2Image.setFitWidth(350);
        step2Image.setPreserveRatio(true);

        Text step3Text = new Text("3. Before starting the game you can choose one of the categories and one of its subsections!");
        step3Text.setFont(bodyFont);
        step3Text.setFill(Color.BLACK);
        ImageView step3Image = new ImageView(new Image(getClass().getResource("/images/2025-04-26 173344.png").toString()));
        step3Image.setFitWidth(350);
        step3Image.setPreserveRatio(true);

        ImageView step32Image = new ImageView(new Image(getClass().getResource("/images/2025-04-26_17-34-07.png").toString()));
        step32Image.setFitWidth(350);
        step32Image.setPreserveRatio(true);
        
        Text step4Text = new Text("4. You can match randomly with the current players or add your friends and challenge yourself while having fun!");
        step4Text.setFont(bodyFont);
        step4Text.setFill(Color.BLACK);

        ImageView step4Image = new ImageView(new Image(getClass().getResource("/images/2025-04-26 173428.png").toString()));
        step4Image.setFitWidth(350);
        step4Image.setPreserveRatio(true);
        
        Text step5Text = new Text("5. After choosing your friend or matching randomly, be ready for the questions!\nYou will compete against 10 second time and the first player answering correctly gets the point!");
        step5Text.setFont(bodyFont);
        step5Text.setFill(Color.BLACK);

        ImageView step5Image = new ImageView(new Image(getClass().getResource("/images/2025-04-26_17-37-33.png").toString()));
        step5Image.setFitWidth(350);
        step5Image.setPreserveRatio(true);
        
        Text step6Text = new Text("6. After the turn, you can see your component's and your points and see who is the winner!");
        step6Text.setFont(bodyFont);
        step6Text.setFill(Color.BLACK);

        ImageView step6Image = new ImageView(new Image(getClass().getResource("/images/2025-04-26_17-37-52.png").toString()));
        step6Image.setFitWidth(350);
        step6Image.setPreserveRatio(true);
        
        Text step7Text = new Text("7. Now it is time for the leaders! In the leaderboards you can see all the rankings from all categories.");
        step7Text.setFont(bodyFont);
        step7Text.setFill(Color.BLACK);

        ImageView step7Image = new ImageView(new Image(getClass().getResource("/images/2025-04-26_17-38-09.png").toString()));
        step7Image.setFitWidth(350);
        step7Image.setPreserveRatio(true);

        Button tutorialButton = new Button("Return");
        tutorialButton.setOnAction(e -> app.showGameStartPage());
        style(tutorialButton);

        VBox tutorialBox = new VBox(12,
            title,
            step1Text, step1Image, step12Image,
            step2Text, step2Image,
            step3Text, step3Image, step32Image,
            step4Text, step4Image,
            step5Text, step5Image,
            step6Text, step6Image,
            step7Text, step7Image,
            tutorialButton
        );
        tutorialBox.setAlignment(Pos.CENTER);
        tutorialBox.setMaxWidth(700);
        tutorialBox.setStyle("-fx-background-color: rgba(255,255,255,0.8);");

        ScrollPane p = new ScrollPane(tutorialBox); 
        p.setFitToWidth(true);

        StackPane root = new StackPane(p);
        setBackground(root, getClass().getResource("/images/2025-04-07_23-11-34.jpg").toString());
        stage.setScene(new Scene(root, 800, 700));
    }

    private void style(Button... buttons) {
        for (Button b : buttons) {
            b.setStyle("-fx-font-size: 14px; -fx-background-color: rgba(50, 50, 50, 0.8); -fx-text-fill: white; -fx-padding: 8px 16px; -fx-background-radius: 8;");
        }
    }

    private void setBackground(Pane pane, String imagePath) {
        pane.setBackground(new Background(new BackgroundImage(
            new Image(imagePath),
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(100, 100, true, true, false, true))));
    }
}