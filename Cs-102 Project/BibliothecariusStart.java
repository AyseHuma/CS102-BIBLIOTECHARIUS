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
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        loadLeaderboardFromFile();
        this.primaryStage = primaryStage;
        showMainPage();
    }

    private String selectedCategory;
    // Normally these should be from player class
    private final List<PlayerScore> leaderboard = new java.util.ArrayList<>(); // Storing players for leaderboard

    PlayerScore p1 = new PlayerScore("Sarah", 20);// Normally p1 should be us and p2 should be a friend
    PlayerScore p2 = new PlayerScore("Joe", 25);
    String player1Name = p1.name;
    String player2Name = p2.name;
    int player1Points = p1.score;
    int player2Points= p2.score;

    private final List<String> addedFriends = new java.util.ArrayList<>(); // This is for friends


    private final java.util.Map<String, String> users = new java.util.HashMap<>(); // This is user info


    private void showMainPage() {
        // Title text
        Text title = new Text("Bibliothecarius");
        title.setFont(Font.font("Georgia", 60));
        title.setFill(Color.GOLD);
        title.setStyle("-fx-effect: dropshadow(gaussian, black, 10, 0, 0, 2);");
        
        // Buttons
        Button signInButton = new Button("Sign In");
        Button signUpButton = new Button("Sign Up");
        
        signInButton.setStyle("-fx-font-size: 20px; -fx-background-color: rgba(50, 50, 50, 0.8); -fx-text-fill: white; -fx-padding: 10px 20px; -fx-background-radius: 10;");
        signUpButton.setStyle("-fx-font-size: 20px; -fx-background-color: rgba(50, 50, 50, 0.8); -fx-text-fill: white; -fx-padding: 10px 20px; -fx-background-radius: 10;");
        
        signInButton.setOnAction(e -> showSignInPage());
        signUpButton.setOnAction(e -> showSignUpPage());
        
        // Layout
        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(title, signInButton, signUpButton);
        
        StackPane root = new StackPane();
        root.getChildren().add(vbox);
        
        // Background Image
        Image backgroundImage = new Image("file:joe-taylor-collapse-render-1.jpg");
        BackgroundImage background = new BackgroundImage(
            backgroundImage, 
            BackgroundRepeat.NO_REPEAT, 
            BackgroundRepeat.NO_REPEAT, 
            BackgroundPosition.CENTER, 
            new BackgroundSize(100, 100, true, true, false, true)
        );
        root.setBackground(new Background(background));
        
        // Scene and Stage
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Bibliothecarius");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showGameStartPage() {
        // Title text
        Text title = new Text("Bibliothecarius");
        title.setFont(Font.font("Georgia", 60));
        title.setFill(Color.GOLD);
        title.setStyle("-fx-effect: dropshadow(gaussian, black, 10, 0, 0, 2);");
        
        // Buttons
        Button startButton = new Button("Start Game");
        Button friendsButton = new Button("Add Friends");
        Button tutorialButton = new Button("Tutorial");
        Button settingsButton = new Button("Settings");
        Button creditsButton = new Button("Credits");
        Button returnButton = new Button("Return");

        styleButton(returnButton);
        styleButton(creditsButton);
        styleButton(settingsButton);
        styleButton(tutorialButton);
        styleButton(friendsButton);
        styleButton(startButton);
        
        
        startButton.setOnAction(e -> showCatalogPage());
        tutorialButton.setOnAction(e -> endGame(selectedCategory, player1Name, player1Points, player2Name, player2Points));// this is only for checking code
        friendsButton.setOnAction(e -> showAddFriendsPage());

        
        // Layout
        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(title, startButton, friendsButton, tutorialButton, settingsButton, creditsButton, returnButton);
        
        StackPane root = new StackPane();
        root.getChildren().add(vbox);
        
        // Background Image
        Image backgroundImage = new Image("file:joe-taylor-collapse-render-1.jpg");
        BackgroundImage background = new BackgroundImage(
            backgroundImage, 
            BackgroundRepeat.NO_REPEAT, 
            BackgroundRepeat.NO_REPEAT, 
            BackgroundPosition.CENTER, 
            new BackgroundSize(100, 100, true, true, false, true)
        );
        root.setBackground(new Background(background));
        
        // Scene and Stage
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Bibliothecarius");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showComponentPage() {
        // Title text
        Text title = new Text("How Would You Like To Play?");
        title.setFont(Font.font("Georgia", 50));
        title.setFill(Color.WHEAT);
        title.setStyle("-fx-effect: dropshadow(gaussian, black, 10, 0, 0, 2);");
        
        // Buttons
        Button randomButton = new Button("Match Randomly");
        Button friendsButton = new Button("Play Wiyh Friend");

        styleButton(friendsButton);
        styleButton(randomButton);
        
        
        //friendsButton.setOnAction(e -> showOnlineFriends(selectedCategory));// You have to store catogory
        friendsButton.setOnAction(e -> showPlayWithFriendsPage());

        randomButton.setOnAction(e -> showQuestionPage(selectedCategory));
        
        // Layout
        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(title, randomButton, friendsButton);
        
        StackPane root = new StackPane();
        root.getChildren().add(vbox);
        
        // Background Image
        Image backgroundImage = new Image("file:2025-04-07_23-11-34.jpg");
        BackgroundImage background = new BackgroundImage(
            backgroundImage, 
            BackgroundRepeat.NO_REPEAT, 
            BackgroundRepeat.NO_REPEAT, 
            BackgroundPosition.CENTER, 
            new BackgroundSize(100, 100, true, true, false, true)
        );
        root.setBackground(new Background(background));
        
        // Scene and Stage
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Bibliothecarius");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showSignInPage() {
        Label usernameLabel = new Label("Username:");
        usernameLabel.setFont(Font.font("Arial", 15)); // Set font and size
        usernameLabel.setTextFill(Color.GOLD);
        TextField usernameField = new TextField();
        usernameField.setMaxWidth(250);  // Make text field shorter
    
        Label passwordLabel = new Label("Password:");
        passwordLabel.setFont(Font.font("Arial", 15)); // Set font and size
        passwordLabel.setTextFill(Color.GOLD);
        PasswordField passwordField = new PasswordField();
        passwordField.setMaxWidth(250);  // Make text field shorter
    
        Button loginButton = new Button("Login");
        Button backButton = new Button("Back");

        styleButton(backButton);
        styleButton(loginButton);
    
        loginButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
        
            if (users.containsKey(username) && users.get(username).equals(password)) {
                System.out.println("Login successful!");
                showGameStartPage();
            } else {
                System.out.println("Invalid username or password.");
            }
        });
        
        backButton.setOnAction(e -> showMainPage());
    
        VBox vbox = new VBox(10, usernameLabel, usernameField, passwordLabel, passwordField, loginButton, backButton);
        vbox.setAlignment(Pos.CENTER);
    
        // Background Image
        Image backgroundImage = new Image("file:joe-taylor-collapse-render-2.jpg");  // Change to your image path
        BackgroundImage background = new BackgroundImage(
            backgroundImage,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(100, 100, true, true, false, true)
        );
    
        StackPane root = new StackPane();
        root.setBackground(new Background(background));
        root.getChildren().add(vbox);
    
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
    }
    

    private void showSignUpPage() {
        Label usernameLabel = new Label("New Username:");
        usernameLabel.setFont(Font.font("Arial", 15)); // Set font and size
        usernameLabel.setTextFill(Color.GOLD);
        TextField usernameField = new TextField();
        usernameField.setMaxWidth(250);  // Make text field shorter
    
        Label passwordLabel = new Label("New Password:");
        passwordLabel.setFont(Font.font("Arial", 15)); // Set font and size
        passwordLabel.setTextFill(Color.GOLD);
        PasswordField passwordField = new PasswordField();
        passwordField.setMaxWidth(250);  // Make text field shorter
    
        Button registerButton = new Button("Register");
        Button backButton = new Button("Back");

        styleButton(backButton);
        styleButton(registerButton);
    
        registerButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
        
            if (username.isEmpty() || password.isEmpty()) {
                System.out.println("Username and password cannot be empty.");
                return;
            }
        
            if (users.containsKey(username)) {
                System.out.println("Username already exists.");
            } else {
                users.put(username, password);
                System.out.println("User registered successfully!");
                showSignInPage();
            }
        });
        
        backButton.setOnAction(e -> showMainPage());
    
        VBox vbox = new VBox(10, usernameLabel, usernameField, passwordLabel, passwordField, registerButton, backButton);
        vbox.setAlignment(Pos.CENTER);
    
        // Background Image
        Image backgroundImage = new Image("file:joe-taylor-collapse-render-2.jpg");  // Change to your image path
        BackgroundImage background = new BackgroundImage(
            backgroundImage,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(100, 100, true, true, false, true)
        );
    
        StackPane root = new StackPane();
        root.setBackground(new Background(background));
        root.getChildren().add(vbox);
    
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
    }

    private void showCatalogPage() {
        Text title = new Text("Catalog");
        title.setFont(Font.font("Georgia", 50));
        title.setFill(Color.GOLD);

        // Create labels for categories
        Label booksLabel = new Label("Books");
        Label moviesLabel = new Label("Movies");
        Label geographyLabel = new Label("Geography");

        booksLabel.setFont(Font.font("Georgia", 20));
        booksLabel.setTextFill(Color.GOLD);
        moviesLabel.setFont(Font.font("Georgia", 20));
        moviesLabel.setTextFill(Color.GOLD);
        geographyLabel.setFont(Font.font("Georgia", 20));
        geographyLabel.setTextFill(Color.GOLD);

        // Create images
        Image booksImage = new Image("file:books.jpg");
        Image moviesImage = new Image("file:movies.jpg");
        Image geographyImage = new Image("file:Geo.jpg");

        javafx.scene.image.ImageView booksView = new javafx.scene.image.ImageView(booksImage);
        javafx.scene.image.ImageView moviesView = new javafx.scene.image.ImageView(moviesImage);
        javafx.scene.image.ImageView geographyView = new javafx.scene.image.ImageView(geographyImage);

        // Set image size
        booksView.setFitWidth(200);
        booksView.setFitHeight(150);
        moviesView.setFitWidth(200);
        moviesView.setFitHeight(150);
        geographyView.setFitWidth(200);
        geographyView.setFitHeight(150);

        // Buttons for selection
        Button booksButton = new Button("Select");
        Button moviesButton = new Button("Select");
        Button geographyButton = new Button("Select");
        Button backButton = new Button("Back");

        styleButton(booksButton);
        styleButton(moviesButton);
        styleButton(geographyButton);
        styleButton(backButton);

        booksButton.setOnAction(e -> {
            selectedCategory = "Books";
            showComponentPage();
        });
        moviesButton.setOnAction(e -> {
            selectedCategory = "Movies";
            showMovieSubSectionsPage();  // new method
        });        
        geographyButton.setOnAction(e -> {
            selectedCategory = "Geography";
            showComponentPage();
        });
        

        backButton.setOnAction(e -> showMainPage());

        // Leaderboard Buttons
        Button booksLeaderboard = new Button("Leaderboard");
        Button moviesLeaderboard = new Button("Leaderboard");
        Button geographyLeaderboard = new Button("Leaderboard");

        styleButton(booksLeaderboard);
        styleButton(moviesLeaderboard);
        styleButton(geographyLeaderboard);

        booksLeaderboard.setOnAction(e -> showLeaderboardPage());
        moviesLeaderboard.setOnAction(e -> showLeaderboardPage());
        geographyLeaderboard.setOnAction(e -> showLeaderboardPage());

        // Layout for each category (Now includes Leaderboard buttons)
        VBox booksBox = new VBox(10, booksLabel, booksView, booksButton, booksLeaderboard);
        booksBox.setAlignment(Pos.CENTER);

        VBox moviesBox = new VBox(10, moviesLabel, moviesView, moviesButton, moviesLeaderboard);
        moviesBox.setAlignment(Pos.CENTER);

        VBox geographyBox = new VBox(10, geographyLabel, geographyView, geographyButton, geographyLeaderboard);
        geographyBox.setAlignment(Pos.CENTER);


        HBox catalogHBox = new HBox(30, booksBox, moviesBox, geographyBox);
        catalogHBox.setAlignment(Pos.CENTER);

        VBox vbox = new VBox(30, title, catalogHBox, backButton);
        vbox.setAlignment(Pos.CENTER);

        StackPane root = new StackPane();
        root.getChildren().add(vbox);
        setBackground(root, "file:joe-taylor-collapse-render-1.jpg"); // Background for catalog page

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
    }

    private void showQuestionPage(String category) {
        Text title = new Text("Question Time!");
        title.setFont(Font.font("Georgia", 40));
        title.setFill(Color.GOLD);
    
        // Timer Label
        Label timerLabel = new Label("Time: 10s");
        timerLabel.setFont(Font.font("Arial", 20));
        timerLabel.setTextFill(Color.RED);
    
        // Player Scores
        Label player1Score = new Label("Player 1: 0 pts");
        Label player2Score = new Label("Player 2: 0 pts");
        player1Score.setFont(Font.font("Arial", 18));
        player1Score.setTextFill(Color.WHITE);
        player2Score.setFont(Font.font("Arial", 18));
        player2Score.setTextFill(Color.WHITE);
    
        // Question Image
        Image questionImage = new Image("file:p20224_v_h9_ap.jpg"); 
        javafx.scene.image.ImageView questionView = new javafx.scene.image.ImageView(questionImage);
        questionView.setFitWidth(300);
        questionView.setFitHeight(200);
    
        // Question Text
        Text questionText = new Text("When was Good Will Hunting relased?");
        questionText.setFont(Font.font("Arial", 22));
        questionText.setFill(Color.WHITE);
    
        // Answer Buttons
        Button answer1 = new Button("2019");
        Button answer2 = new Button("1998");
        Button answer3 = new Button("1975");
        Button answer4 = new Button("2000");
    
        styleButton(answer1);
        styleButton(answer2);
        styleButton(answer3);
        styleButton(answer4);

        answer1.setOnAction(e -> endGame(selectedCategory,player1Name, player1Points, player2Name, player2Points));
    
        // Left Side (2 choices)
        VBox leftBox = new VBox(20, answer1, answer3);
        leftBox.setAlignment(Pos.CENTER_LEFT);
    
        // Right Side (2 choices)
        VBox rightBox = new VBox(20, answer2, answer4);
        rightBox.setAlignment(Pos.CENTER_RIGHT);
    
        // Answers in a row (2 left, 2 right)
        HBox answersBox = new HBox(60, leftBox, rightBox);
        answersBox.setAlignment(Pos.CENTER);
    
        // Layouts
        HBox scoreBox = new HBox(20, player1Score, timerLabel, player2Score);
        scoreBox.setAlignment(Pos.CENTER);
    
        VBox vbox = new VBox(30, title, scoreBox, questionView, questionText, answersBox);
        vbox.setAlignment(Pos.CENTER);
    
        StackPane root = new StackPane();
        root.getChildren().add(vbox);
        setBackground(root, "file:joe-taylor-collapse-render-2.jpg"); 
    
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);

    }

    private void showLeaderboardPage() {
        Text title = new Text(" Leaderboard");
        title.setFont(Font.font("Georgia", 40));
        title.setFill(Color.GOLD);
    
        // Sort by score descending
        leaderboard.sort((a, b) -> Integer.compare(b.score, a.score));
    
        ObservableList<String> leaderboardData = FXCollections.observableArrayList();
        int rank = 1;
        for (PlayerScore ps : leaderboard) {
            leaderboardData.add(rank + ". " + ps);
            rank++;
        }
    
        ListView<String> leaderboardList = new ListView<>(leaderboardData);
        leaderboardList.setMaxSize(300, 250);
    
        // Back Button
        Button backButton = new Button("Back to Catalog");
        styleButton(backButton);
        backButton.setOnAction(e -> showCatalogPage());
    
        VBox vbox = new VBox(30, title, leaderboardList, backButton);
        vbox.setAlignment(Pos.CENTER);
    
        StackPane root = new StackPane();
        root.getChildren().add(vbox);
        setBackground(root, "file:joe-taylor-collapse-render-2.jpg");
    
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
    }
    

private void showAddFriendsPage() {
    Text title = new Text("Add Friends");
    title.setFont(Font.font("Georgia", 40));
    title.setFill(Color.GOLD);

    // Sample online users
    String[] onlineUsers = {"Alice", "Bob", "Charlie", "Dave", "Eve"};

    VBox friendsList = new VBox(10);
    friendsList.setAlignment(Pos.CENTER);

    for (String user : onlineUsers) {
        // Only show those who aren’t already added
        if (!addedFriends.contains(user)) {
            Button addButton = new Button("Add " + user);
            styleButton(addButton);
            addButton.setOnAction(e -> {
                addedFriends.add(user);
                System.out.println(user + " added to friend list.");
                showAddFriendsPage(); // Refresh UI
            });
            friendsList.getChildren().add(addButton);
        }
    }

    Button backButton = new Button("Back");
    styleButton(backButton);
    backButton.setOnAction(e -> showGameStartPage());

    VBox layout = new VBox(20, title, friendsList, backButton);
    layout.setAlignment(Pos.CENTER);

    StackPane root = new StackPane(layout);
    setBackground(root, "file:joe-taylor-collapse-render-2.jpg");
    Scene scene = new Scene(root, 800, 600);
    primaryStage.setScene(scene);
}

private void showPlayWithFriendsPage() {
    Text title = new Text("Play With a Friend");
    title.setFont(Font.font("Georgia", 40));
    title.setFill(Color.GOLD);

    VBox friendButtons = new VBox(10);
    friendButtons.setAlignment(Pos.CENTER);

    if (addedFriends.isEmpty()) {
        Label noFriends = new Label("No friends added yet.");
        noFriends.setTextFill(Color.WHITE);
        noFriends.setFont(Font.font("Arial", 16));
        friendButtons.getChildren().add(noFriends);
    } else {
        for (String friend : addedFriends) {
            Button friendButton = new Button("Play with " + friend);
            styleButton(friendButton);
            friendButton.setOnAction(e -> showQuestionPage(selectedCategory));
            friendButtons.getChildren().add(friendButton);
        }
    }

    Button backButton = new Button("Back");
    styleButton(backButton);
    backButton.setOnAction(e -> showComponentPage());

    VBox layout = new VBox(30, title, friendButtons, backButton);
    layout.setAlignment(Pos.CENTER);

    StackPane root = new StackPane(layout);
    setBackground(root, "file:joe-taylor-collapse-render-2.jpg");
    Scene scene = new Scene(root, 800, 600);
    primaryStage.setScene(scene);
}

private void showMovieSubSectionsPage() {
    Text title = new Text("Select a Subsection");
    title.setFont(Font.font("Georgia", 40));
    title.setFill(Color.GOLD);

    // Subsection Buttons
    Button top100Movies = new Button("Most Popular 100 Movies");
    Button top250Movies = new Button("Most Popular 250 Movies");
    Button top100Series = new Button("Most Popular 100 Series");
    Button top250Series = new Button("Most Popular 250 Series");
    Button backButton = new Button("Back");

    styleButton(top100Movies);
    styleButton(top250Movies);
    styleButton(top100Series);
    styleButton(top250Series);
    styleButton(backButton);

    // Sub- Catagories for now
    top100Movies.setOnAction(e -> {
        selectedCategory = "Top 100 Movies";
        showComponentPage();
    });

    top250Movies.setOnAction(e -> {
        selectedCategory = "Top 250 Movies";
        showComponentPage();
    });

    top100Series.setOnAction(e -> {
        selectedCategory = "Top 100 Series";
        showComponentPage();
    });

    top250Series.setOnAction(e -> {
        selectedCategory = "Top 250 Series";
        showComponentPage();
    });

    backButton.setOnAction(e -> showCatalogPage());

    VBox vbox = new VBox(20, title, top100Movies, top250Movies, top100Series, top250Series, backButton);
    vbox.setAlignment(Pos.CENTER);

    StackPane root = new StackPane(vbox);
    setBackground(root, "file:joe-taylor-collapse-render-2.jpg");
    Scene scene = new Scene(root, 800, 600);
    primaryStage.setScene(scene);
}




private void endGame(String category, String player1Name, int player1Points, String player2Name, int player2Points) {
    Text title = new Text("Game Over - " + category);
    title.setFont(Font.font("Georgia", 36));
    title.setFill(Color.GOLD);

    Text scoreHeader = new Text("Final Scores");
    scoreHeader.setFont(Font.font("Arial", 28));
    scoreHeader.setFill(Color.WHITE);

   LinearGradient gradient = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
        new Stop(0, Color.rgb(122,137,83)), new Stop(1, Color.GOLD)); // Light green to a soft wood color
    scoreHeader.setFill(gradient);

    // Soft shadow for the 3D effect, matching the gentle light in the library
    scoreHeader.setStyle("-fx-effect: dropshadow(gaussian,rgb(159, 131, 40), 6, 0, 0, 5);");

    // Optional: Add a subtle stroke for contrast
    scoreHeader.setStroke(Color.DARKGREEN);  // A darker green for a natural feel
    scoreHeader.setStrokeWidth(1);

    // Player 1 Information
    Text player1Text = new Text(player1Name + ": " + player1Points + " points");
    player1Text.setFont(Font.font("Georgia", 22));
    player1Text.setFill(Color.BEIGE);

    // Player 2 Information
    Text player2Text = new Text(player2Name + ": " + player2Points + " points");
    player2Text.setFont(Font.font("Georgia", 22));
    player2Text.setFill(Color.BEIGE);

    // Buttons
    Button newGameButton = new Button("New Game");
    styleButton(newGameButton);
    newGameButton.setOnAction(e -> showCatalogPage());

    Button leaderboardButton = new Button("View Leaderboard");
    styleButton(leaderboardButton);
    leaderboardButton.setOnAction(e -> showLeaderboardPage());

    // Layout for buttons (centered)
    VBox buttonBox = new VBox(20, newGameButton, leaderboardButton);
    buttonBox.setAlignment(Pos.CENTER);

    // Layout for player information (on the sides)
    HBox playerInfoBox = new HBox(50, player1Text, player2Text);
    playerInfoBox.setAlignment(Pos.CENTER);
    playerInfoBox.setPadding(new Insets(0, 50, 0, 50)); // Optional padding to give some space on the sides

    // Main layout combining title, scores, player info, and buttons
    VBox vbox = new VBox(25, title, scoreHeader, playerInfoBox, buttonBox);
    vbox.setAlignment(Pos.CENTER);

    StackPane root = new StackPane(vbox);
    setBackground(root, "file:2025-04-07_23-12-42.jpg");
    Scene scene = new Scene(root, 800, 600);
    primaryStage.setScene(scene);

    //For Leaderboard
    addOrUpdatePlayerScore(player1Name, player1Points);
    addOrUpdatePlayerScore(player2Name, player2Points);

    saveLeaderboardToFile();
}


    
    
    private void styleButton(Button button) {
        button.setStyle(
            "-fx-font-size: 16px; " +
            "-fx-background-color: rgba(50, 50, 50, 0.8); " +
            "-fx-text-fill: white; " +
            "-fx-padding: 10px 20px; " +
            "-fx-background-radius: 10;"
        );
    }
    private void setBackground(Pane pane, String imagePath) {
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

    private void saveLeaderboardToFile() {
        try (java.io.PrintWriter writer = new java.io.PrintWriter("leaderboard.txt")) {
            for (PlayerScore ps : leaderboard) {
                writer.println(ps.name + "," + ps.score);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void loadLeaderboardFromFile() {
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
    private void addOrUpdatePlayerScore(String name, int pointsToAdd) {
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


