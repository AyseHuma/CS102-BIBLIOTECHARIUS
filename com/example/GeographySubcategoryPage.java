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

public class GeographySubcategoryPage {
    private final ClientTester app;

    public GeographySubcategoryPage(ClientTester app) {
        this.app = app;
    }

    public void show(Stage stage) {
        Text title = new Text("Select a Subsection");
        title.setFont(Font.font("Georgia", 40));
        title.setFill(Color.GOLD);

        Button subcat1Button = new Button("SUBCAT");
        Button backButton = new Button("Back");

        style(subcat1Button, backButton);

        subcat1Button.setOnAction(e -> {
            app.setLastChosenSubcategory("SUBCAT");
            app.showComponentChoicePage("GEOGRAPHY", "Top_100_Books");
        });

        backButton.setOnAction(e -> app.showCatalogPage());

        VBox vbox = new VBox(20, title, subcat1Button, backButton);
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

