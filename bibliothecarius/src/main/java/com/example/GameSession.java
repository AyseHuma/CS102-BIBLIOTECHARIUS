package com.example;

import java.util.ArrayList;
import java.util.Random;

public class GameSession {
    private ClientHandler player1;
    private ClientHandler player2;
    private int currentQuestionIndex = 0; 

    private ArrayList<Question> questions = new ArrayList<Question>();
    private ArrayList<String> correctAnswers;

    public GameSession(ClientHandler player1, ClientHandler player2) 
    {
        this.player1 = player1;
        this.player2 = player2;
        player1.gamePoint = 0; 
        player2.gamePoint = 0; 
        loadQuestions();  // Load questions (you can replace this with a database or an API)
    }

    private void loadQuestions()
    {
        Book.fillBookIDFromGoodbooks(3.2, 1000);
        fillQuestions();
    }

    public void fillQuestions(){ // TODO inside here filter with categories by parameters maybe. Filter the movie and books. Filter top 200 etc inside the constructor
        for(int i = 0; i<5; i++){
            questions.add(makeRandomTypeQuestion(new Book()));
        }                
    }

    public static Question makeRandomTypeQuestion(Category c){
        Random r = new Random();
        int random = r.nextInt(3);
        Type d; 
        Question q; 

        switch (random) {
            case 0:
                q = new MultipleChoice();
                break;
            case 1:
                q = new YesNo();
                break;
            case 2:
                q = new OpenEnded();
                break;
            default:
                q = new YesNo();
                break;
        }
        
        random = r.nextInt(3);
        
        // Second switch — determines d
        switch (random) {
            case 0:
                d = new TypeName(c, q);
                break;
            case 1:
                d = new TypeGenre(c, q);
                break;
            case 2:
                d = new TypeDate(c, q);
                break;
            default:
                d = new TypeDate(c, q);
                break;
        }

        if(q instanceof YesNo){
            return new YesNo(c, d);
        }
        else if(q instanceof MultipleChoice){
            return new MultipleChoice(c, d);
        }
        else if(q instanceof OpenEnded){
            return new OpenEnded(c, d);
        }
        return null; 
    }

    public void startGame() 
    {
        sendNextQuestion();
    }

    private void sendNextQuestion() {
        if (currentQuestionIndex < questions.size()) 
        {
            String q = questions.get(currentQuestionIndex).getQuestionMessage();
            player1.sendMessage("QUESTION:" + q);
            player2.sendMessage("QUESTION:" + q);
        } else 
        {
            endGame();  // No more questions, end the game
        }
    }
    public void checkAnswer(ClientHandler player, String answer)
    {
        boolean isCorrect = false;

        if (currentQuestionIndex < questions.size()) 
        {
            if (questions.get(currentQuestionIndex).containsAnswer(answer)) 
            {
                isCorrect = true;
                player.morePoints();
            }
        }
        String resultMessage = isCorrect ? "CORRECT" : "INCORRECT";
        player1.sendMessage("ANSWERED_" + resultMessage + ":" + player.getUsername());
        player2.sendMessage("ANSWERED_" + resultMessage + ":" + player.getUsername());

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
