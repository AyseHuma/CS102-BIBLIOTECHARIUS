import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SignUpPage {
    private final BibliothecariusStart app;

    public SignUpPage(BibliothecariusStart app) {
        this.app = app;
    }

    public void show(Stage stage) {
        Label usernameLabel = new Label("New Username:");
        usernameLabel.setFont(Font.font("Arial", 15));
        usernameLabel.setTextFill(Color.GOLD);
        TextField usernameField = new TextField();
        usernameField.setMaxWidth(250);

        Label passwordLabel = new Label("New Password:");
        passwordLabel.setFont(Font.font("Arial", 15));
        passwordLabel.setTextFill(Color.GOLD);
        PasswordField passwordField = new PasswordField();
        passwordField.setMaxWidth(250);

        Button registerButton = new Button("Register");
        Button backButton = new Button("Back");
        styleButton(registerButton);
        styleButton(backButton);

        registerButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();

            if (username.isEmpty() || password.isEmpty()) {
                System.out.println("Username and password cannot be empty.");
                return;
            }

            if (app.users.containsKey(username)) {
                System.out.println("Username already exists.");
            } else {
                app.users.put(username, password);
                System.out.println("User registered successfully!");
                app.showSignInPage();
            }
        });

        backButton.setOnAction(e -> app.showMainPage());

        VBox vbox = new VBox(10, usernameLabel, usernameField, passwordLabel, passwordField, registerButton, backButton);
        vbox.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(vbox);
        setBackground(root, "file:joe-taylor-collapse-render-2.jpg");

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
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
