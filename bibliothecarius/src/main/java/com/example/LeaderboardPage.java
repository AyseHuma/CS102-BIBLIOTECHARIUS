

package com.example;

import java.sql.SQLException;
import java.util.Scanner;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LeaderboardPage{

    private String leaderboardString;
    private Stage s; 

    public LeaderboardPage(ClientTester app, String leaderBoardString){
        this.app = app; 
        leaderboardString = leaderBoardString; 
    }

    // public void displayLeaderboard(String type)
    // {
    //     launch();
    // }

    // public static String prepareLeaderboard(Stage s, String category) throws SQLException
    // {
    //     System.out.println(data);
    //     return data;
    // }

    // @Override
    // public void start(Stage primaryStage) throws SQLException {
    //     Scanner sc = new Scanner(System.in);
    //     displayLeaderboard(primaryStage, type);

    //     primaryStage.setOnCloseRequest(event -> {
    //         Platform.exit(); 
    //     });
    // }

    // public static void displayLeaderboard(Stage primaryStage, String category) throws SQLException
    // {
    //     Scene scene = prepareLeaderboard(category);
    //     primaryStage.setTitle(category + " Leaderboard");
    //     primaryStage.setScene(scene);
    //     primaryStage.show();
    // }

    private final ClientTester app;

    public void show(Stage stage){

        Button returnButton = new Button("Return");
        returnButton.setOnAction(e -> app.showGameStartPage());
        style(returnButton);
        
        String s = leaderboardString.replace("*", "\n");
        TextArea leaderboardLabel = new TextArea(s);

        VBox vbox = new VBox(20, leaderboardLabel, returnButton);
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

