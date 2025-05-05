import java.io.*;
import java.net.*;
import java.util.Scanner;

public class GameClient {
    private static final String SERVER_IP = "127.0.0.1"; // veya sunucu IP'si
    private static final int SERVER_PORT = 8888;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            // Sunucudan gelen mesajları arka planda dinle
            Thread listener = new Thread(() -> {
                try {
                    String response;
                    while ((response = in.readLine()) != null) {
                        System.out.println("[SERVER] " + response);
                    }
                } catch (IOException e) {
                    System.out.println("Connection lost.");
                }
            });
            listener.start();

            // Kullanıcıdan komutları al ve sunucuya gönder
            while (true) {
                String command = scanner.nextLine();
                out.println(command);

                if (command.equalsIgnoreCase("exit")) {
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("Unable to connect to server: " + e.getMessage());
        }
    }
}

