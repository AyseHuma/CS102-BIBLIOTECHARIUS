import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainPage {
    private final BibliothecariusStart app;

    public MainPage(BibliothecariusStart app) {
        this.app = app;
    }

    public void show(Stage stage) {
        // Title text
        Text title = new Text("Bibliothecarius");
        title.setFont(Font.font("Georgia", 60));
        title.setFill(Color.GOLD);
        title.setStyle("-fx-effect: dropshadow(gaussian, black, 10, 0, 0, 2);");

        // Buttons
        Button signInButton = new Button("Sign In");
        Button signUpButton = new Button("Sign Up");

        styleButton(signInButton);
        styleButton(signUpButton);

        signInButton.setOnAction(e -> app.showSignInPage());
        signUpButton.setOnAction(e -> app.showSignUpPage());

        // Layout
        VBox vbox = new VBox(20, title, signInButton, signUpButton);
        vbox.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(vbox);

        // Background
        Image backgroundImage = new Image("file:joe-taylor-collapse-render-1.jpg");
        BackgroundImage background = new BackgroundImage(
            backgroundImage,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(100, 100, true, true, false, true)
        );
        root.setBackground(new Background(background));

        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Bibliothecarius");
        stage.setScene(scene);
        stage.show();
    }

    private void styleButton(Button button) {
        button.setStyle(
            "-fx-font-size: 20px; " +
            "-fx-background-color: rgba(50, 50, 50, 0.8); " +
            "-fx-text-fill: white; " +
            "-fx-padding: 10px 20px; " +
            "-fx-background-radius: 10;"
        );
    }
}
