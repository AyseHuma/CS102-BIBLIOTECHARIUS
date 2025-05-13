package com.example;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CatalogPage {
    private final ClientTester app;

    public CatalogPage(ClientTester app) {
        this.app = app;
    }

    public void show(Stage stage) {
        Text title = new Text("Catalog");
        title.setFont(Font.font("Georgia", 50));
        title.setFill(Color.GOLD);

        Label booksLabel = new Label("Books");
        Label moviesLabel = new Label("Movies");
        Label geographyLabel = new Label("Geography");

        setupLabel(booksLabel);
        setupLabel(moviesLabel);
        setupLabel(geographyLabel);

        ImageView booksView = new ImageView(new Image(getClass().getResource("/images/books.jpg").toExternalForm()));
        ImageView moviesView = new ImageView(new Image(getClass().getResource("/images/movies.jpg").toExternalForm()));
        ImageView geographyView = new ImageView(new Image(getClass().getResource("/images/movies.jpg").toExternalForm()));

        setImageSize(booksView);
        setImageSize(moviesView);
        setImageSize(geographyView);

        Button booksButton = new Button("Select");
        Button moviesButton = new Button("Select");
        Button geographyButton = new Button("Select");
        Button backButton = new Button("Back");

        Button booksLeaderboard = new Button("Leaderboard");
        Button moviesLeaderboard = new Button("Leaderboard");
        Button geographyLeaderboard = new Button("Leaderboard");

        styleButton(booksButton);
        styleButton(moviesButton);
        styleButton(geographyButton);
        styleButton(backButton);
        styleButton(booksLeaderboard);
        styleButton(moviesLeaderboard);
        styleButton(geographyLeaderboard);

        booksButton.setOnAction(e -> {
            app.showBookSubcategoryPage();
        });

        moviesButton.setOnAction(e -> {
            app.showMovieSubcategoryPage();
        });

        geographyButton.setOnAction(e -> {
            app.showGeographySubcategoryPage();
        });

        booksLeaderboard.setOnAction(e -> app.sendLeaderboardRequest("BOOK"));
        moviesLeaderboard.setOnAction(e -> app.sendLeaderboardRequest("MOVIE"));
        geographyLeaderboard.setOnAction(e -> app.sendLeaderboardRequest("GEOGRAPHY"));
        backButton.setOnAction(e -> app.showGameStartPage());

        VBox booksBox = new VBox(10, booksLabel, booksView, booksButton, booksLeaderboard);
        VBox moviesBox = new VBox(10, moviesLabel, moviesView, moviesButton, moviesLeaderboard);
        VBox geographyBox = new VBox(10, geographyLabel, geographyView, geographyButton, geographyLeaderboard);

        booksBox.setAlignment(Pos.CENTER);
        moviesBox.setAlignment(Pos.CENTER);
        geographyBox.setAlignment(Pos.CENTER);

        HBox catalogHBox = new HBox(30, booksBox, moviesBox, geographyBox);
        catalogHBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(30, title, catalogHBox, backButton);
        layout.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(layout);
        setBackground(root, getClass().getResource("/images/joe-taylor-collapse-render-1.jpg").toString());

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
    }

    private void setupLabel(Label label) {
        label.setFont(Font.font("Georgia", 20));
        label.setTextFill(Color.GOLD);
    }

    private void setImageSize(ImageView view) {
        view.setFitWidth(200);
        view.setFitHeight(150);
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
}
