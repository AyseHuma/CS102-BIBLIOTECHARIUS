import java.util.LinkedList;
import java.util.Queue;

public class MatchmakingManager {
    private final Queue<ClientHandler> waitingPlayers = new LinkedList<>();

    public synchronized void queuePlayer(ClientHandler player) 
    {
        if (waitingPlayers.isEmpty()) 
        {
            waitingPlayers.add(player);
            player.sendMessage("WAITING_FOR_OPPONENT");
        } else 
        {
            ClientHandler opponent = waitingPlayers.poll();
            startMatch(player, opponent);
        }
    }
    private void startMatch(ClientHandler p1, ClientHandler p2) 
    {
        p1.setOpponent(p2);
        p2.setOpponent(p1);

        p1.sendMessage("MATCH_FOUND:You are Player 1");
        p2.sendMessage("MATCH_FOUND:You are Player 2");

        GameSession gameSession = new GameSession(p1, p2);
        gameSession.startGame();
    }
}
