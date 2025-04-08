import java.util.ArrayList;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public abstract class Question {
    protected String question; 
    protected Type type; 
    protected Category category; 
    protected int correctInt; 
    protected String answer; 
    protected ArrayList<String> acceptableAnswers; 

    public abstract VBox askQuestion(Stage stage);   // this will return a scene in actuality
    public Question(Type t, Category c){
        type = t; 
        category = c; 
        answer = type.getAnswer();
        question = type.getQuestion();
        acceptableAnswers = t.getAcceptableAnswers();
    }
    public Question(){ }
    protected VBox createNewQuestion(Category m){

    }

    public boolean containsAnswer(String s){
        return acceptableAnswers.contains(s);
    }
}