import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GameSession implements Runnable{
    private Socket player1;
    private Socket player2;

    public GameSession(Socket p1, Socket p2)
    {
        this.player1 = p1;
        this.player2 = p2;
    }
    
    public void run()
    {
        try 
        {
            System.out.println("Starting a new game session...");

            BufferedReader in1 = new BufferedReader(new InputStreamReader(player1.getInputStream()));
            PrintWriter out1 = new PrintWriter(player1.getOutputStream(), true);

            BufferedReader in2 = new BufferedReader(new InputStreamReader(player2.getInputStream()));
            PrintWriter out2 = new PrintWriter(player2.getOutputStream(), true);

            out1.println("Matched with a player! Game starting...");
            out2.println("Matched with a player! Game starting...");

            String question = "What is 5 + 3?";
            out1.println("QUESTION: " + question);
            out2.println("QUESTION: " + question);

            String answer1 = in1.readLine();
            String answer2 = in2.readLine();

            String correctAnswer = "8";
            String result1 = answer1.equals(correctAnswer) ? "Correct!" : "Wrong!";
            String result2 = answer2.equals(correctAnswer) ? "Correct!" : "Wrong!";

            out1.println("Your answer: " + result1);
            out2.println("Your answer: " + result2);

            player1.close();
            player2.close();

            System.out.println("Game session ended.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
