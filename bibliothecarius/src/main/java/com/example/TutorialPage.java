package com.example;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
        title.setFont(Font.font("Georgia", 50));
        title.setFill(Color.WHEAT);
        title.setStyle("-fx-effect: dropshadow(gaussian, black, 10, 0, 0, 2)");

        // sign in/ up tutorial
        Text step1Text = new Text("1. If you already have an account please sign up with your username and password, if you dont create a new account with sign up!");
        step1Text.setFont(Font.font("Georgia", 18));
        step1Text.setFill(Color.WHITE);

        ImageView step1Image = new ImageView(new Image(getClass().getResource("/images/2025-04-26_17-31-42.png").toString()));
        step1Image.setFitWidth(400);
        step1Image.setPreserveRatio(true);
        ImageView step12Image = new ImageView(new Image(getClass().getResource("/images/2025-04-26_17-32-53.png").toString()));
        step12Image.setFitWidth(400);
        step12Image.setPreserveRatio(true);

        // 
        Text step2Text = new Text("2. After signing in you can start a new game, add your friends or the tutorial.");
        step2Text.setFont(Font.font("Georgia", 18));
        step2Text.setFill(Color.WHITE);

        ImageView step2Image = new ImageView(new Image(getClass().getResource("/images/2025-04-26_17-33-16.png").toString()));
        step2Image.setFitWidth(400);
        step2Image.setPreserveRatio(true);

        // Subsection
        Text step3Text = new Text("3. Before starting the game you can choose one of the categories and one od its subsections!");
        step3Text.setFont(Font.font("Georgia", 18));
        step3Text.setFill(Color.WHITE);
        ImageView step3Image = new ImageView(new Image(getClass().getResource("/images/2025-04-26 173344.png").toString()));
        step3Image.setFitWidth(400);
        step3Image.setPreserveRatio(true);

        ImageView step32Image = new ImageView(new Image(getClass().getResource("/images/2025-04-26_17-34-07.png").toString()));
        step32Image.setFitWidth(400);
        step32Image.setPreserveRatio(true);
        
        // Add friend
        Text step4Text = new Text("4. You can match randomly with the current players or you can also add your friends and challange yourself while having fun!");
        step4Text.setFont(Font.font("Georgia", 18));
        step4Text.setFill(Color.WHITE);

        ImageView step4Image = new ImageView(new Image(getClass().getResource("/images/2025-04-26 173428.png").toString()));
        step4Image.setFitWidth(400);
        step4Image.setPreserveRatio(true);
        
        // Questionpage
        Text step5Text = new Text("5. After choosing your friend or matching randomly, be ready for the questions!\nYou will compete against 10 second time and the first player answers the question right gets the point and you move to the other questions! ");
        step5Text.setFont(Font.font("Georgia", 18));
        step5Text.setFill(Color.WHITE);

        ImageView step5Image = new ImageView(new Image(getClass().getResource("/images/2025-04-26_17-37-33.png").toString()));
        step5Image.setFitWidth(400);
        step5Image.setPreserveRatio(true);
        
        // end of game screen
        Text step6Text = new Text("6. After the turn, you can see your component's and your points and see who is the winner!");
        step6Text.setFont(Font.font("Georgia", 18));
        step6Text.setFill(Color.WHITE);

        ImageView step6Image = new ImageView(new Image(getClass().getResource("/images/2025-04-26_17-37-52.png").toString()));
        step6Image.setFitWidth(400);
        step6Image.setPreserveRatio(true);
        
        // leaders
        Text step7Text = new Text("7. Now it is time for the leaders, in the leaderboards you can see all the ranking from all the categories!");
        step7Text.setFont(Font.font("Georgia", 18));
        step7Text.setFill(Color.WHITE);

        ImageView step7Image = new ImageView(new Image(getClass().getResource("/images/2025-04-26_17-38-09.png").toString()));
        step7Image.setFitWidth(400);
        step7Image.setPreserveRatio(true);


        // Geri Butonu
        Button tutorialButton = new Button("Tutorial");
        tutorialButton.setOnAction(e -> app.showTutorialPage());
        style(tutorialButton);

        VBox tutorialBox = new VBox(15,
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

        StackPane root = new StackPane(tutorialBox);
        setBackground(root, getClass().getResource("/images/2025-04-07_23-11-34.jpg").toString());
        stage.setScene(new Scene(root, 800, 700));
    }

    private void style(Button... buttons) {
        for (Button b : buttons) {
            b.setStyle("-fx-font-size: 16px; -fx-background-color: rgba(50, 50, 50, 0.8); -fx-text-fill: white; -fx-padding: 10px 20px; -fx-background-radius: 10;");
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
