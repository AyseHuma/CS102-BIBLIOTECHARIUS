import java.util.Arrays;
import java.util.List;

import javax.xml.catalog.Catalog;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class BibliothecariusStart extends Application {
    public Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        loadLeaderboardFromFile();
        this.primaryStage = primaryStage;
        showMainPage();
    }

    public String selectedCategory;
    // Normally these should be from player class
    public  List<PlayerScore> leaderboard = new java.util.ArrayList<>(); // Storing players for leaderboard

    public PlayerScore p1 = new PlayerScore("Sarah", 20);// Normally p1 should be us and p2 should be a friend
    public PlayerScore p2 = new PlayerScore("Joe", 25);
    public String player1Name = p1.name;
    public String player2Name = p2.name;
    public int player1Points = p1.score;
    public int player2Points= p2.score;

    public final List<String> addedFriends = new java.util.ArrayList<>(); // This is for friends


    public final java.util.Map<String, String> users = new java.util.HashMap<>(); // This is user info


    public void showMainPage() {
        new MainPage(this).show(primaryStage);
    }
    

    public void showGameStartPage() {
        new GameStartPage(this).show(primaryStage);
    }

    public void showComponentPage() {
        new ComponentChoicePage(this).show(primaryStage);
    }

    public void showSignInPage() {
        new SignInPage(this).show(primaryStage);
    }
    
    

    public void showSignUpPage() {
        new SignUpPage(this).show(primaryStage);
    }

    public void showCatalogPage() {
        new CatalogPage(this).show(primaryStage);
    }
    

    public void showQuestionPage(String category) {
        new QuestionPage(this).show(primaryStage, category);
    }

    public void showLeaderboardPage() {
        new LeaderboardPage(this).show(primaryStage);
    }
    

    public void showAddFriendsPage() {
        new AddFriendsPage(this).show(primaryStage);
    }

    public void showPlayWithFriendsPage() {
        new PlayWithFriendsPage(this).show(primaryStage);
    }

    public void showMovieSubSectionsPage() {
        new MovieSubSectionsPage(this).show(primaryStage);
    }




public void endGame(String category, String p1, int s1, String p2, int s2) {
    new EndGamePage(this).show(primaryStage, category, p1, s1, p2, s2);
}


    
    
    public void styleButton(Button button) {
        button.setStyle(
            "-fx-font-size: 16px; " +
            "-fx-background-color: rgba(50, 50, 50, 0.8); " +
            "-fx-text-fill: white; " +
            "-fx-padding: 10px 20px; " +
            "-fx-background-radius: 10;"
        );
    }
    public void setBackground(Pane pane, String imagePath) {
        Image backgroundImage = new Image(imagePath);
        BackgroundImage background = new BackgroundImage(
            backgroundImage,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(100, 100, true, true, false, true)
        );
        pane.setBackground(new Background(background));
    }

    public void saveLeaderboardToFile() {
        try (java.io.PrintWriter writer = new java.io.PrintWriter("leaderboard.txt")) {
            for (PlayerScore ps : leaderboard) {
                writer.println(ps.name + "," + ps.score);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void loadLeaderboardFromFile() {
        leaderboard.clear(); // just in case
    
        java.io.File file = new java.io.File("leaderboard.txt");
        if (!file.exists()) return;
    
        try (java.util.Scanner scanner = new java.util.Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                if (parts.length == 2) {
                    String name = parts[0];
                    int score = Integer.parseInt(parts[1]);
                    leaderboard.add(new PlayerScore(name, score));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void addOrUpdatePlayerScore(String name, int pointsToAdd) {
        for (PlayerScore ps : leaderboard) {
            if (ps.name.equalsIgnoreCase(name)) {
                ps.score += pointsToAdd;
                return;
            }
        }
        leaderboard.add(new PlayerScore(name, pointsToAdd));
    }
    
    
    

    public static void main(String[] args) {
        launch(args);
    }
}


