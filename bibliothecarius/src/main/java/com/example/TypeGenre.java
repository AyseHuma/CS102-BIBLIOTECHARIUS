package com.example;
import java.util.ArrayList;

public class TypeGenre extends Type{
    
    public TypeGenre (Category c, Question b){
        if(c instanceof Movie){
            if(b instanceof YesNo){
                question = "Is the genre of the movie " + c.getTitle() + " " + wrongOrRightAnswer(c) + "?"; 
            }
            else{
                question = "What is the genre of the movie "+ c.getTitle() + "?";
            }
        }
        else{
            if(b instanceof YesNo){
                question = "Is the genre of the book " + c.getTitle() + " " + wrongOrRightAnswer(c) + "?"; 
            }
            else{
                question = "What is the book " + c.getTitle() + "?";
            }
        }
        acceptableAnswers = (ArrayList<String>)(c.getGenresArr().clone()); 
        answer = "" + c.getGenres();
        question += acceptableAnswers + "?"; 
    }

    protected String wrongOrRightAnswer(Category c){   // creates and returns a wrongAnswer String that stores the answer that is given in the question
        int k = (int)(Math.random()*2); 
        if(k == 1){   // it is the correct answer
            int m = (int)(Math.random()*(c.getGenresArr().size())); 
            wrongAnswer = (c.getGenresArr().get(m));
        }
        else{  
            int r = (int)(Math.random() * c.getPossibleFalseGenres().size()); 
            wrongAnswer = c.getPossibleFalseGenres().get(r);
        }        
        return wrongAnswer; 
    }

    @Override
    public ArrayList<String> getChoices(Category category) { // the correct answer is again the last index
        int r; 
        ArrayList<String> arr = new ArrayList<String>();
        ArrayList<String> clonePossible = (ArrayList<String>)(category.getPossibleFalseGenres().clone());

        for(int i = 0; i < 3; i++){
            r = (int)(Math.random() * clonePossible.size()); 
            arr.add(clonePossible.get(r));
            clonePossible.remove(r);
        }

        if(category.getGenresArr().size() > 0){
            r = (int)(Math.random() * category.getGenresArr().size()); 
            arr.add(category.getGenresArr().get(r));
        }
        else{  // sometimes it selects null genres even when filtered for it
            arr.add("General");
        }
        return arr; 
    }
}
