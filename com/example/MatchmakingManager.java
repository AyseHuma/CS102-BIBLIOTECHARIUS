package com.example;

import java.util.LinkedList;
import java.util.Queue;

public class MatchmakingManager {
    private final Queue<ClientHandler> waitingPlayersMovie = new LinkedList<>();
    private final Queue<ClientHandler> waitingPlayersBook = new LinkedList<>();
    private final Queue<ClientHandler> waitingPlayersGEOGRAPHY = new LinkedList<>();

    public synchronized void queuePlayer(ClientHandler player, String category, String subcat) 
    {
        if (category.equals("MOVIE")) {
            if (waitingPlayersMovie.isEmpty()) {
                waitingPlayersMovie.add(player);
                player.sendMessage("WAITING_FOR_OPPONENT");
            } 
            else {
                ClientHandler opponent = waitingPlayersMovie.poll();
                startMatch(player, opponent, "MOVIE", subcat);
            }
        }
        else if (category.equals("BOOK")){
            if (waitingPlayersBook.isEmpty()) {
                waitingPlayersBook.add(player);
                player.sendMessage("WAITING_FOR_OPPONENT");
            } 
            else {
                ClientHandler opponent = waitingPlayersBook.poll();
                startMatch(player, opponent, "BOOK", subcat);
            }
        }
        else if (category.equals("GEOGRAPHY")){
            if (waitingPlayersGEOGRAPHY.isEmpty()) {
                waitingPlayersGEOGRAPHY.add(player);
                player.sendMessage("WAITING_FOR_OPPONENT");
            } 
            else {
                ClientHandler opponent = waitingPlayersGEOGRAPHY.poll();
                startMatch(player, opponent, "GEOGRAPHY", subcat);
            }
        }
        else{
            System.out.println("NO SUCH CATEGORY");
        }
    }
    private void startMatch(ClientHandler p1, ClientHandler p2, String category, String subcat) 
    {
        p1.setOpponent(p2);
        p2.setOpponent(p1);

        p1.sendMessage("MATCH_FOUND:" + p2.getUsername() + ":" + category);  // prints opponent's name
        p2.sendMessage("MATCH_FOUND:" + p1.getUsername() + ":" + category);
        System.out.println("1");
        GameSession gameSession = new GameSession(p1, p2, category, subcat);
        p1.setGameSession(gameSession);
        p2.setGameSession(gameSession);
        System.out.println("2");
        gameSession.startGame();
    }
    
    public synchronized void removePlayer(ClientHandler player) {
        waitingPlayersMovie.remove(player);
        waitingPlayersBook.remove(player);
    }
}
