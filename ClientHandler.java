import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ClientHandler(Socket socket) 
    {
        this.socket = socket;
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
            while ((inputLine = in.readLine()) != null) 
            {
                System.out.println("Received: " + inputLine);
                if (inputLine.equals("MATCH_REQUEST")) 
                {
                    matchmakingManager.queuePlayer(this);
                }else
                {
                    out.println("Server: " + inputLine);
                }
            }

        } catch (IOException e) 
        {
            e.printStackTrace();
        } finally 
        {
            try { socket.close(); } catch (IOException e) {}
        }
    }
    private static MatchmakingManager matchmakingManager = new MatchmakingManager();

    private ClientHandler opponent;
    private PrintWriter out2;

    public void setOpponent(ClientHandler opponent) 
    {
        this.opponent = opponent;
    }
    public void sendMessage(String msg) 
    {
        out2.println(msg);
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
}   
