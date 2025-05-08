package com.example;

import java.util.ArrayList;

public abstract class Type {
    protected String question; 
    protected String wrongAnswer = null; // this is only used for YESNO questions
    protected String answer; 
    protected ArrayList<String> acceptableAnswers = new ArrayList<>(); 

    public ArrayList<String> getAcceptableAnswers() {
        return acceptableAnswers;
    }

    public boolean containsAnswer(String s){
        return acceptableAnswers.contains(s);
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public String getWrongAnswer() {
        return wrongAnswer;
    }

    public abstract ArrayList<String> getChoices(Category category);    // this is only useful for multiplechoices and the last index is always the correct answer!!!
    protected abstract String wrongOrRightAnswer(Category c);
}
