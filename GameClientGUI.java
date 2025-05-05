import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class GameClientGUI extends Application {

    private TextArea messageArea = new TextArea();
    private TextField inputField = new TextField();
    private Button sendButton = new Button("Gönder");

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    @Override
    public void start(Stage primaryStage) {
        messageArea.setEditable(false);
        inputField.setPromptText("Komut girin (örneğin: LOGIN ahmet)");

        sendButton.setOnAction(e -> sendMessage());
        inputField.setOnAction(e -> sendMessage());

        VBox root = new VBox(10,
                new Label("Kim Milyoner Olmak İster - Multiplayer"),
                messageArea,
                new HBox(10, inputField, sendButton)
        );
        root.setStyle("-fx-padding: 15;");

        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setTitle("Game Client");
        primaryStage.show();

        connectToServer();
    }

    private void connectToServer() {
        try {
            socket = new Socket("127.0.0.1", 8888); // Sunucu IP ve port
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            Thread listenerThread = new Thread(() -> {
                String response;
                try {
                    while ((response = in.readLine()) != null) {
                        String finalResponse = response;
                        Platform.runLater(() -> messageArea.appendText("[Server] " + finalResponse + "\n"));
                    }
                } catch (IOException e) {
                    Platform.runLater(() -> messageArea.appendText("Bağlantı kesildi.\n"));
                }
            });
            listenerThread.setDaemon(true);
            listenerThread.start();

        } catch (IOException e) {
            messageArea.appendText("Sunucuya bağlanılamadı: " + e.getMessage() + "\n");
        }
    }

    private void sendMessage() {
        String message = inputField.getText();
        if (message.isEmpty()) return;

        out.println(message);
        messageArea.appendText("[Sen] " + message + "\n");
        inputField.clear();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
