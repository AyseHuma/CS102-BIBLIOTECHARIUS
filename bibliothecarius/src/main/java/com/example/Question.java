package com.example;

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

    public abstract boolean containsAnswer(String s);
    
    public String getQuestionString() {
        return question;
    }
    public abstract String getQuestionFormatString(); // gets the format like YesNo and choices for MultipleChoice only

    public String getQuestionMessage(){
        return question + "*:" + getQuestionFormatString();     // so the question is characterized by ending with ?*: So that it does not get confused with a movie
    }

}