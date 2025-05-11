

package com.example;

import java.sql.SQLException;
import java.util.Scanner;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LeaderboardPage extends Application {

    private static String type;

    
    public static void displayLeaderboard(String type)
    {
        LeaderboardPage.type = type;
        launch();
    }

    public static Scene prepareLeaderboard(String category) throws SQLException
    {
        String data = AccountDb.getLeaderboard(category);

        Label leaderboardLabel = new Label(data);
        leaderboardLabel.setStyle("-fx-font-size: 16px;");
        VBox root = new VBox(10);
        root.getChildren().add(leaderboardLabel);
        Scene scene = new Scene(root, 300, 200);

        return scene;
    }

    @Override
    public void start(Stage primaryStage) throws SQLException {
        Scanner sc = new Scanner(System.in);
        displayLeaderboard(primaryStage, type);

        primaryStage.setOnCloseRequest(event -> {
            Platform.exit(); 
        });
    }

    public static void displayLeaderboard(Stage primaryStage, String category) throws SQLException
    {
        Scene scene = prepareLeaderboard(category);
        primaryStage.setTitle(category + " Leaderboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

