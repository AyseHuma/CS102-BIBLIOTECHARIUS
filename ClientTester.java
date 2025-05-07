public class ClientTester {
    public static void main(String[] args) {
        ClientConnection client = new ClientConnection();

        if (client.connect("localhost", 12345)) {
            System.out.println("Connected to the server!");

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