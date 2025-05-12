package com.example;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;
import java.util.Timer; 

public class GameSession {
    private ClientHandler player1;
    private ClientHandler player2;
    private int currentQuestionIndex = 0; 
    private GameTimer gameTimer; 
    private int falseAnswered; 
    private String subcat; 

    private ArrayList<Question> questions = new ArrayList<Question>();

    public GameSession(ClientHandler player1, ClientHandler player2, String category, String subcat) 
    {
        this.player1 = player1;
        this.player2 = player2;
        player1.gamePoint = 0; 
        player2.gamePoint = 0; 
        this.subcat = subcat; 
        if (category.equals("BOOK")){
            loadQuestionsBook();
        }
        else if (category.equals("MOVIE")){
            loadQuestionMovie();
        }
        else if (category.equals("GEOGRAPHY")){
            loadQuestionsGeography();
        }
        else{
            System.out.println("No such category");
        }  
    }

    private void loadQuestionsBook()
    {
        double minRating;
        int minVotes;

        switch (subcat) {
            case "Top_100_Books":
                minRating = 4.2;
                minVotes = 50000	;
                break;

            case "Top_250_Books":
                minRating = 4.0;
                minVotes = 25000;
                break;

            default:
                System.out.println("WRONG SUBCAT");
                minRating = 4.0;
                minVotes = 25000;
                break;
        }
        Book.fillBookIDFromGoodbooks(minRating, minVotes);
        fillQuestions("BOOK");
    }
    private void loadQuestionsGeography() {
        // You could later add filters based on subcat (like "European Flags", etc.)
        fillQuestions("GEOGRAPHY");
    }
    

    private void loadQuestionMovie(){
        double minRating;
        int minVotes;
        boolean isMovie;

        switch (subcat) {
            case "Top_100_Movies":
                minRating = 8.0;
                minVotes = 50000;
                isMovie = true;
                break;

            case "Top_250_Movies":
                minRating = 7.5;
                minVotes = 25000;
                isMovie = true;
                break;

            case "Top_100_Series":
                minRating = 8.2;
                minVotes = 20000;
                isMovie = false;
                break;

            case "Top_250_Series":
                minRating = 7.5;
                minVotes = 10000;
                isMovie = false;
                break;
            default:
                System.out.println("WRONG SUBCAT");
                minRating = 8.0;
                minVotes = 50000;
                isMovie = true;
                break;
        }
        Movie.fillTconstsFromIMDB(minRating, minVotes, isMovie);
        fillQuestions("MOVIE");
    }

    public void fillQuestions(String category){ // TODO inside here filter with categories by parameters maybe. Filter the movie and books. Filter top 200 etc inside the constructor
        if (category.equals("BOOK")){
            for(int i = 0; i<5; i++){
                questions.add(makeRandomTypeQuestion(new Book()));
            }  
        }
        else if (category.equals("MOVIE")){
            for(int i = 0; i<5; i++){
                questions.add(makeRandomTypeQuestion(new Movie()));
            } 
        }
        else if (category.equals("GEOGRAPHY")){
            System.out.println("FILL QUESTION");
            for(int i = 0; i<5; i++){
                questions.add(makeRandomTypeQuestion(new Geography()));
            } 
        }
        else{
            System.out.println("No such category");
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
        
        if (c instanceof Geography){
            random = r.nextInt(2);  // so that no population type questions are asked
        }
        else{
            random = r.nextInt(3);
        }
        
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
                d = new TypeGenre(c, q);
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

        gameTimer = new GameTimer(60);
        gameTimer.startTimer();
        sendNextQuestion();
    }

    private void sendNextQuestion() {
        System.out.println("SENT QUESTION");
        falseAnswered = 0; 
        if (currentQuestionIndex < questions.size()) 
        {
            String q = questions.get(currentQuestionIndex).getQuestionMessage();
            player1.sendMessage("QUESTION:Q" + currentQuestionIndex + ":" + q + ":" + gameTimer.getTimeLeft() + ":" + player1.gamePoint + ":" + player2.gamePoint);
            player2.sendMessage("QUESTION:Q" + currentQuestionIndex + ":" + q + ":" + gameTimer.getTimeLeft() + ":" + player2.gamePoint + ":" + player1.gamePoint);
        } else 
        {
            endGame();  // No more questions, end the game
        }
    }
    public synchronized void checkAnswer(ClientHandler player, String answer, String ID)  // THIS SYNCHRONIZED IS KEY!!! IT ENSURES THAT THE CLIENTHANDLERS CALL IT INDIVIDUALLY
    {
        boolean isCorrect = false;

        if (currentQuestionIndex < questions.size()) 
        {
            if ((currentQuestionIndex + "").equals(ID) && questions.get(currentQuestionIndex).containsAnswer(answer)) 
            {
                isCorrect = true;
                player.morePoints();
                falseAnswered = 0;
            }
            else if ((currentQuestionIndex + "").equals(ID) && !questions.get(currentQuestionIndex).containsAnswer(answer)){
                falseAnswered++; 
            }
            else{
                return; // this means that the question index does not match the ID, so this checkAnswer is ignored. 
            }
        }
        String resultMessage = isCorrect ? "CORRECT" : "INCORRECT";
        player1.sendMessage("ANSWERED_" + resultMessage + ":" + player.getUsername());
        player2.sendMessage("ANSWERED_" + resultMessage + ":" + player.getUsername());

        if(falseAnswered != 1){  // this can either be 0 or 2. If it is 0, the person got it first correctly. If it is 2, both tried and got incorrect. If it is 1, one of them answered incorrectly. 
            currentQuestionIndex++;
            sendNextQuestion();
        }
    }
    private void endGame() 
    {
        player1.setGameSession(null);
        player2.setGameSession(null);
        player1.sendMessage("GAME_OVER:" + player1.gamePoint + ":" + player2.gamePoint);
        player2.sendMessage("GAME_OVER:" + player2.gamePoint + ":" + player1.gamePoint);
        gameTimer.stopTimer();
        // Optionally: Send final scores to the leaderboard
    }

    public void playerDisconnected(ClientHandler disconnectedPlayer){
        player1.sendMessage("OPPONENT_DISCONNECTED");
        player2.sendMessage("OPPONENT_DISCONNECTED");
        endGame();
    }

    private class GameTimer {

        private int timeLeft; 
        private Timer timer;

        public GameTimer(int i){
            timer = new Timer();
            timeLeft = i; 
        }

        public void startTimer(){
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    timeLeft --;
                    if(timeLeft > 0 && timeLeft == 5){
                        player1.sendMessage("GAME_TIMER:" + timeLeft);
                        player2.sendMessage("GAME_TIMER:" + timeLeft);
                    }
                    else if (timeLeft <= 0){
                        endGame();   // endGame already closes the timer
                    }
                }  
            }, 0, 1000);
        }

        public void stopTimer(){
            timeLeft = 0; 
            timer.cancel();
        }

        public int getTimeLeft(){
            return timeLeft;
        }
    }
}
