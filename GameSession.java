import java.util.ArrayList;

public class GameSession {
    private ClientHandler player1;
    private ClientHandler player2;
    private int currentQuestionIndex = 0;

    private ArrayList<String> questions; // Placeholder for trivia questions
    private ArrayList<String> correctAnswers;

    public GameSession(ClientHandler player1, ClientHandler player2) 
    {
        this.player1 = player1;
        this.player2 = player2;
        loadQuestions();  // Load questions (you can replace this with a database or an API)
    }

    private void loadQuestions()
    {

    }

    public void startGame() 
    {
        sendNextQuestion();
    }

    private void sendNextQuestion() {
        if (currentQuestionIndex < questions.size()) 
        {
            String question = questions.get(currentQuestionIndex);
            player1.sendMessage("QUESTION:" + question);
            player2.sendMessage("QUESTION:" + question);
        } else 
        {
            endGame();  // No more questions, end the game
        }
    }
    public void checkAnswer(ClientHandler player, String answer)
    {
        boolean isCorrect = false;

        if (currentQuestionIndex < correctAnswers.size()) 
        {
            if (answer.equalsIgnoreCase(correctAnswers.get(currentQuestionIndex))) 
            {
                isCorrect = true;
            }
        }
        String resultMessage = isCorrect ? "Correct!" : "Incorrect!";
        player.sendMessage(resultMessage);

        currentQuestionIndex++;
        sendNextQuestion();
    }
    private void endGame() 
    {
        player1.sendMessage("GAME_OVER: ");
        player2.sendMessage("GAME_OVER: ");

        // Optionally: Send final scores to the leaderboard
    }
}
