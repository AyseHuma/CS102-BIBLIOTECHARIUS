package com.example;

import java.util.ArrayList;

public class TypeDate extends Type{

    public TypeDate (Category c, Question b){
        if(c instanceof Movie){
            if(b instanceof YesNo){
                question = "Is the release date of the movie " + c.getTitle() + " " + wrongOrRightAnswer(c)+ "?"; 
            }
            else{
                question = "What is the release date of the movie "+ c.getTitle() + "?";
            }
        }
        else{
            if(b instanceof YesNo){
                question = "Is the publication date of the book " + c.getTitle() + " " + wrongOrRightAnswer(c)+ "?"; 
            }
            else{
                question = "When was the publication date of the book " + c.getTitle() + "?";
            }
        }
        acceptableAnswers.add("" + c.getReleaseDate());
        answer = "" + c.getReleaseDate();
        question += acceptableAnswers + "?"; // TODO delete
    }

    protected String wrongOrRightAnswer(Category c){   // creates and returns a wrongAnswer String that stores the answer that is given in the question
        int k = (int)(Math.random()*2); 
        if(k == 1){
            wrongAnswer = "" + (c.getReleaseDate());
        }
        else{
            ArrayList<Integer> nums = new ArrayList<Integer>();
            nums.add(-5);
            nums.add(-4);
            nums.add(-3);
            nums.add(-2);
            nums.add(-1);
            nums.add(1);
            nums.add(2);
            nums.add(3);
            nums.add(4);
            nums.add(5);
            int r = (int)(Math.random() * nums.size()); 
            wrongAnswer = "" + (c.getReleaseDate() + nums.get(r));
        }        
        return wrongAnswer; 
    }

    public String getQuestion() {
        return question;
    }

    public ArrayList<String> getChoices(Category category) { 
        int r; 
        ArrayList<String> arr = new ArrayList<String>();
        ArrayList<Integer> nums = new ArrayList<Integer>();
        nums.add(-5);
        nums.add(-4);
        nums.add(-3);
        nums.add(-2);
        nums.add(-1);
        nums.add(1);
        nums.add(2);
        nums.add(3);
        nums.add(4);
        nums.add(5);
        for(int i = 0; i < 3; i++){
            r = (int)(Math.random() * nums.size()); 
            arr.add("" + (category.getReleaseDate() + nums.get(r)));
            nums.remove(r);
        }
        arr.add("" + category.getReleaseDate());
        return arr; 
    }
}
