import java.util.ArrayList;

public class TypeName extends Type{

    public TypeName (Category c, Question b){
        if(c instanceof Movie){
            if(b instanceof YesNo){
                question = "Is the director of the movie " + c.getTitle() + ": " + wrongOrRightAnswer(c) + "?"; 
            }
            else{
                question = "Who is the director in the movie "+ c.getTitle() + "?";
            }
        }
        else{
            if(b instanceof YesNo){
                question = "Does this person have a hand in the book " + c.getTitle() + ": " + wrongOrRightAnswer(c) + "?"; 
            }
            else{
                question = "Who is the person in the book " + c.getTitle() + "?";
            }
        }
        acceptableAnswers = (ArrayList<String>)(c.getCreators().clone()); 
        answer = "" + c.getCreators();
    }

    @Override
    public ArrayList<String> getChoices(Category category) {
        int r; 
        ArrayList<String> arr = new ArrayList<String>();
        // ArrayList<String> clonePossible = (ArrayList<String>)(category.getPossibleFalseGenres().clone());

        // for(int i = 0; i < 3; i++){
        //     r = (int)(Math.random() * clonePossible.size()); 
        //     arr.add(clonePossible.get(r));
        //     clonePossible.remove(r);
        // }

        // r = (int)(Math.random() * category.getGenresArr().size()); 
        // arr.add(category.getGenresArr().get(r));
        arr.add(getOneRandomName());
        arr.add(getOneRandomName());
        arr.add(getOneRandomName());
        r = (int)(Math.random() * category.getGenresArr().size()); 
        arr.add(category.getGenresArr().get(r));
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
            wrongAnswer = c.getOneRandomName();
        }        
        return wrongAnswer; 
    }

}
