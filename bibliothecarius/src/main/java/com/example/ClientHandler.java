package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class ClientHandler implements Runnable{
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    private String username = null; 
    private ClientHandler opponent = null;
    private static MatchmakingManager matchmakingManager = new MatchmakingManager();
    private GameSession gameSession; 
    public int gamePoint; 
    public String lastCategory; 
    public String myUsername; 
    private boolean goOn; 

    public ClientHandler(Socket socket) 
    {
        this.socket = socket;
        goOn = true; 
    }
    public void run()
    {
        try 
        {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            out.println("Welcome to Bibliothecarius Server!");
            
            // Wait for matchmaking requests or other messages
            String inputLine;
            while (goOn && (inputLine = in.readLine()) != null) 
            {
                System.out.println("Received: " + inputLine);
                if (inputLine.length() > "MATCH_REQUEST".length() && inputLine.substring(0,14).equals("MATCH_REQUEST:")) 
                {
                    int lastColon = inputLine.lastIndexOf(":");
                    String subcat = inputLine.substring(lastColon + 1);
                    String category = inputLine.substring(14, lastColon);
                    matchmakingManager.queuePlayer(this, category, subcat);  // TODO separate methods would work better
                }
                else if(inputLine.length() > "SIGN_IN_REQUEST".length() && inputLine.substring(0,16).equals("SIGN_IN_REQUEST:")){
                    String[] list = inputLine.split(":");
                    String name = list[1];
                    String pass = list[2];
                    if(AccountDb.authenticateUser(name, pass))
                    {
                        int userID = AccountDb.getUserIdByName(name);
                        username = name; 
                        out.println("SIGNED_IN:" + username);
                    }
                    else{
                        out.println("FAILED_SIGN_IN");
                    }
                }  
                else if(inputLine.length() > "SIGN_UP_REQUEST".length() && inputLine.substring(0,16).equals("SIGN_UP_REQUEST:")){
                    String[] list = inputLine.split(":");
                    String name = list[1];
                    String pass = list[2];
                    if(AccountDb.addUser(name,pass,null)){
                        username = name; 
                        out.println("SIGNED_UP:" + username);
                    }
                    else{
                        out.println("FAILED_SIGNED_UP");
                    }
                }
                else if (inputLine.length() > "ANSWER".length() && inputLine.substring(0,7).equals("ANSWER:")) {
                    int index = inputLine.substring(7).indexOf(":") + 7; // this finds the colon that separates Q12 from Yes etc
                    String answer = inputLine.substring(index + 1);
                    String ID = inputLine.substring(8, index);
                    if (gameSession != null) {   // checks this if the client is in a battle
                        gameSession.checkAnswer(this, answer, ID);
                    }
                }
                else if (inputLine.length() > "SEND_LEADERBOARD".length() && inputLine.substring(0,17).equals("SEND_LEADERBOARD:")){
                    int colonInt = inputLine.indexOf(":");
                    String leaderboardString = AccountDb.getLeaderboard(inputLine.substring(colonInt + 1)); 
                    out.println("LEADERBOARD:" + leaderboardString);
                }
                else if(inputLine.equals("CANCEL_MATCH_REQUEST")){
                    matchmakingManager.removePlayer(this);
                } 
                else if(inputLine.equals("DISCONNECT")){
                    goOn = false; 
                    disconnect(); 
                }             
                else
                {
                    out.println("Server: " + inputLine);
                }
            }

        } catch (IOException e) 
        {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("xd");
            e.printStackTrace();
        } finally 
        {
            disconnect();
        }
    }

    public void disconnect() {
        try {
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ServerMain.connectedClients.remove(this);
        matchmakingManager.removePlayer(this);
        if(gameSession != null){
            gameSession.playerDisconnected(this);
        }
        System.out.println("Player " + getUsername() + " disconnected.");
    }

    public void setOpponent(ClientHandler opponent) 
    {
        this.opponent = opponent;
    }
    public void setGameSession(GameSession gameSession) {  
        this.gameSession = gameSession;
    }
    public String getUsername() 
    {
        return username;
    }
    public void sendMessage(String msg) 
    {
        out.println(msg);
    }
    public void handleQuestion(String question) 
    {
        // Show the question to the player (this can be through GUI)
        System.out.println("Question: " + question);
        // Wait for the player's answer (GUI event or text input)
    }
    public void sendAnswer(String answer) 
    {
        out.println("ANSWER:" + answer);  // Send answer to server
    }
    public void morePoints() {  //TODO add points to whoever answered first
        gamePoint++;
    }
}   
