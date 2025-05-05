import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class GameServer {
    private static final int PORT = 8888;
    private static Map<String, ClientHandler> onlinePlayers = new ConcurrentHashMap<>();
    private static ExecutorService pool = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        System.out.println("Game Server started on port " + PORT);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(clientSocket);
                pool.execute(handler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Oyuncular arası eşleşme başlatma (örnek)
    public static void startMatch(String player1, String player2) {
        ClientHandler p1 = onlinePlayers.get(player1);
        ClientHandler p2 = onlinePlayers.get(player2);

        if (p1 != null && p2 != null) {
            p1.startGameAgainst(p2);
            p2.startGameAgainst(p1);
        }
    }

    public static void registerPlayer(String username, ClientHandler handler) {
        onlinePlayers.put(username, handler);
    }

    public static void unregisterPlayer(String username) {
        onlinePlayers.remove(username);
    }

    public static List<String> getOnlinePlayers(String excludeUsername) {
        List<String> list = new ArrayList<>(onlinePlayers.keySet());
        list.remove(excludeUsername);
        return list;
    }
}
