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

public class MovieSubcategoryPage {
    private final ClientTester app;

    public MovieSubcategoryPage(ClientTester app) {
        this.app = app;
    }

    public void show(Stage stage) {
        Text title = new Text("Select a Subsection");
        title.setFont(Font.font("Georgia", 40));
        title.setFill(Color.GOLD);

        Button top100Movies = new Button("Most Popular 100 Movies");
        Button top250Movies = new Button("Most Popular 250 Movies");
        Button top100Series = new Button("Most Popular 100 Series");
        Button top250Series = new Button("Most Popular 250 Series");
        Button backButton = new Button("Back");

        style(top100Movies, top250Movies, top100Series, top250Series, backButton);

        top100Movies.setOnAction(e -> {
            app.setLastChosenSubcategory("Top_100_Movies");
            app.showComponentChoicePage("MOVIE", "Top_100_Movies");
        });

        top250Movies.setOnAction(e -> {
            app.setLastChosenSubcategory("Top_250_Movies");
            app.showComponentChoicePage("MOVIE", "Top_250_Movies");
        });

        top100Series.setOnAction(e -> {
            app.setLastChosenSubcategory("Top_100_Series");
            app.showComponentChoicePage("MOVIE" , "Top_100_Series");
        });

        top250Series.setOnAction(e -> {
            app.setLastChosenSubcategory("Top_250_Series");
            app.showComponentChoicePage("MOVIE", "Top_250_Series");
        });

        backButton.setOnAction(e -> app.showCatalogPage());

        VBox vbox = new VBox(20, title, top100Movies, top250Movies, top100Series, top250Series, backButton);
        vbox.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(vbox);
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
}

