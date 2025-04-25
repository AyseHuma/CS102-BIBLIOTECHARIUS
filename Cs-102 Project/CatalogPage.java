import javafx.collections.FXCollections;
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

public class CatalogPage {
    private final BibliothecariusStart app;

    public CatalogPage(BibliothecariusStart app) {
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

        ImageView booksView = new ImageView(new Image("file:books.jpg"));
        ImageView moviesView = new ImageView(new Image("file:movies.jpg"));
        ImageView geographyView = new ImageView(new Image("file:Geo.jpg"));

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
            app.selectedCategory = "Books";
            app.showComponentPage();
        });

        moviesButton.setOnAction(e -> {
            app.selectedCategory = "Movies";
            app.showMovieSubSectionsPage();
        });

        geographyButton.setOnAction(e -> {
            app.selectedCategory = "Geography";
            app.showComponentPage();
        });

        booksLeaderboard.setOnAction(e -> app.showLeaderboardPage());
        moviesLeaderboard.setOnAction(e -> app.showLeaderboardPage());
        geographyLeaderboard.setOnAction(e -> app.showLeaderboardPage());
        backButton.setOnAction(e -> app.showMainPage());

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
        setBackground(root, "file:joe-taylor-collapse-render-1.jpg");

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

