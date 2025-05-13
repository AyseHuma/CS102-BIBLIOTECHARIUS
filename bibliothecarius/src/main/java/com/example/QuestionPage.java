package com.example;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class QuestionPage {
    private final ClientTester app;
    private VBox vbox; 
    private int timeLeft; 
    private GameTimer timer; 
    private Label timerLabel; 
    private String pointMe; 
    private String pointOther;
    private String nameMe; 
    private String nameOther;

    public QuestionPage(ClientTester app, VBox vbox, int timeLeft, String pM, String pO, String nM, String nO) {
        this.app = app;
        this.vbox = vbox; 
        this.timeLeft = timeLeft; 
        pointMe = pM; 
        pointOther = pO; 
        nameMe = nM; 
        nameOther = nO; 
        timer = new GameTimer(timeLeft);
        timer.startTimer();
    }

    public void show(Stage stage) {
        Text title = new Text("Question Time!");
        title.setFont(Font.font("Georgia", 40));
        title.setFill(Color.GOLD);

        timerLabel = new Label("Time: " + timeLeft + "s");
        timerLabel.setFont(Font.font("Arial", 20));
        timerLabel.setTextFill(Color.RED);

        Label player1Score = new Label(nameMe + ": " + pointMe + " pts");
        Label player2Score = new Label(nameOther + ": " + pointOther + " pts");
        player1Score.setFont(Font.font("Arial", 18));
        player2Score.setFont(Font.font("Arial", 18));
        player1Score.setTextFill(Color.WHITE);
        player2Score.setTextFill(Color.WHITE);

        HBox scoreBox = new HBox(20, player1Score, timerLabel, player2Score);
        scoreBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(30, title, scoreBox, vbox);
        layout.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(layout);
        setBackground(root, getClass().getResource("/images/joe-taylor-collapse-render-2.jpg").toString());

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

    private class GameTimer {

        private int timeLeft; 
        private Timer timer;

        public GameTimer(int i){
            timer = new Timer();
            timeLeft = i; 
        }

        public void startTimer(){
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    timeLeft --;
                    Platform.runLater(() -> {  
                        timerLabel.setText("Time: " + timeLeft + "s");
                    });
                    if (timeLeft <= 0) {
                        stopTimer();
                    }
                }  
            }, 0, 1000);
        }

        public void stopTimer(){
            timeLeft = 0; 
            timer.cancel();
        }

        public int getTimeLeft(){
            return timeLeft;
        }
    }
}
