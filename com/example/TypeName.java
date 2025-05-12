package com.example;
import java.util.ArrayList;

public class TypeName extends Type{

    public TypeName (Category c, Question b){
        if(c instanceof Movie){
            if(b instanceof YesNo){
                question = "Is one of the creators of the movie/show " + c.getTitle() + ": " + wrongOrRightAnswer(c) + "?"; 
            }
            else{
                question = "Who is a creator in the movie/show "+ c.getTitle() + "?";
            }
        }
        else if (c instanceof Book){
            if(b instanceof YesNo){
                question = "Does this person have a hand in the book " + c.getTitle() + ": " + wrongOrRightAnswer(c) + "?"; 
            }
            else{
                question = "Who is the author of the book " + c.getTitle() + "?";
            }
        }
        else{
            if(b instanceof YesNo){
                question = "Is the name of this country this: " + wrongOrRightAnswer(c) + "?"; 
            }
            else{
                question = "What is the name of this country?";
            }
        }
        acceptableAnswers = (ArrayList<String>)(c.getCreators().clone()); 
        answer = "" + c.getCreators();
        question += acceptableAnswers + "?"; 
    }

    @Override
    public ArrayList<String> getChoices(Category category) {
        int r; 
        ArrayList<String> arr = new ArrayList<String>();
        ArrayList<String> threeTitles = category.getThreeRandomNames();
        arr.add(threeTitles.get(0));
        arr.add(threeTitles.get(1));
        arr.add(threeTitles.get(2));
        r = (int)(Math.random() * category.getCreators().size()); 
        arr.add(category.getCreators().get(r));
        return arr; 
    }

    @Override
    protected String wrongOrRightAnswer(Category c) {
        int k = (int)(Math.random()*2); 
        if(k == 1){   // it is the correct answer
            int m = (int)(Math.random()*(c.getCreators().size())); 
            wrongAnswer = (c.getCreators().get(m));
        }
        else{  
            int m = (int)(Math.random()*3); 
            wrongAnswer = c.getThreeRandomNames().get(m);
        }        
        return wrongAnswer; 
    }

}
