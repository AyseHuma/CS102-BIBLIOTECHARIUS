package com.example;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class MatchmakingManager {
    private final Queue<ClientHandler> waitingPlayersMovie = new LinkedList<>();
    private final Queue<ClientHandler> waitingPlayersBook = new LinkedList<>();
    private final Queue<ClientHandler> waitingPlayersGEOGRAPHY = new LinkedList<>();
    private final Queue<ClientHandler> waitingFriendsMovie = new LinkedList<>();
    private final Queue<ClientHandler> waitingFriendsBook = new LinkedList<>();
    private final Queue<ClientHandler> waitingFriendsGEOGRAPHY = new LinkedList<>();

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
    public synchronized void queueFriend(ClientHandler player, String category, String subcat) 
    {
        if (category.equals("MOVIE")) {
            boolean notFound = true;
            Iterator<ClientHandler> friendQueueIteratorMovie = waitingFriendsMovie.iterator();
            while (notFound && friendQueueIteratorMovie.hasNext()) {
                ClientHandler name = friendQueueIteratorMovie.next();
                try {
                    if(AccountDb.areFriends(name.getUsername(), player.getUsername())){
                        friendQueueIteratorMovie.remove();
                        startMatch(player, name, "MOVIE", subcat);
                        notFound = false;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(notFound){
                waitingFriendsMovie.add(player);
                player.sendMessage("WAITING_FOR_FRIEND");
            }
        }
        else if (category.equals("BOOK")){
            boolean notFound = true;
            Iterator<ClientHandler> friendQueueIteratorBook = waitingFriendsBook.iterator();
            while (notFound && friendQueueIteratorBook.hasNext()) {
                ClientHandler name = friendQueueIteratorBook.next();
                try {
                    if(AccountDb.areFriends(name.getUsername(), player.getUsername())){
                        friendQueueIteratorBook.remove();
                        startMatch(player, name, "BOOK", subcat);
                        notFound = false;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(notFound){
                waitingFriendsBook.add(player);
                player.sendMessage("WAITING_FOR_FRIEND");
            }
        }
        else if (category.equals("GEOGRAPHY")){
            boolean notFound = true;
            Iterator<ClientHandler> friendQueueIteratorGEOGRAPHY = waitingFriendsGEOGRAPHY.iterator();
            while (notFound && friendQueueIteratorGEOGRAPHY.hasNext()) {
                ClientHandler name = friendQueueIteratorGEOGRAPHY.next();
                try {
                    if(AccountDb.areFriends(name.getUsername(), player.getUsername())){
                        friendQueueIteratorGEOGRAPHY.remove();
                        startMatch(player, name, "GEOGRAPHY", subcat);
                        notFound = false;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(notFound){
                waitingFriendsGEOGRAPHY.add(player);
                player.sendMessage("WAITING_FOR_FRIEND");
            }
        }
        else{
            System.out.println("NO SUCH CATEGORY");
        }
    }
    public void startMatch(ClientHandler p1, ClientHandler p2, String category, String subcat) 
    {
        p1.setOpponent(p2);
        p2.setOpponent(p1);

        p1.sendMessage("MATCH_FOUND:" + p2.getUsername() + ":" + category);  // prints opponent's name
        p2.sendMessage("MATCH_FOUND:" + p1.getUsername() + ":" + category);
        GameSession gameSession = new GameSession(p1, p2, category, subcat);
        p1.setGameSession(gameSession);
        p2.setGameSession(gameSession);
        gameSession.startGame();
    }
    
    public synchronized void removePlayer(ClientHandler player) {
        waitingPlayersMovie.remove(player);
        waitingPlayersBook.remove(player);
    }
}
