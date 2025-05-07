import java.util.Scanner;

public class ClientTester {
    public static void main(String[] args) {
        ClientConnection client = new ClientConnection();

        if (client.connect("139.179.26.85", 12345)) {
            System.out.println("Connected to the server!");

            Scanner in = new Scanner(System.in);
            String username = in.nextLine();
            String pass = in.nextLine();
            client.sendMessage("SIGN_UP_REQUEST:" + username + ":" + pass);

            // Listen for the welcome message
            new Thread(() -> {
                try {
                    String msg;
                    while ((msg = client.receiveMessage()) != null) {
                        System.out.println("Server says: " + msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();

            // Send a matchmaking request after connecting
            client.sendMessage("MATCH_REQUEST");

            // You can add a delay and send more test messages if needed
        } else {
            System.out.println("Failed to connect to the server.");
        }
    }
}