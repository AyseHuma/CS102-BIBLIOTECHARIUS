import java.util.ArrayList;

public class Question {
    String question;
    int answer; 
    ArrayList<Integer> choices; 

    public Question (String movie, int ansDate){
        this.question = "What is the release date of " + movie; 
        this.answer = ansDate; 
    }

    // 
    public void printQuestion(){
        System.out.println(question);
        int[] arr = shiftArr(); 
        ArrayList<Integer> choisesList = new ArrayList<Integer>(); 
        for(int i = 0; i<4; i++){
            choisesList.add(answer + arr[i]);
        }
        choisesList.add(answer);
        java.util.Collections.shuffle(choisesList);
        choices = choisesList; 
        for(int i = 0; i<4; i++){
            System.out.println(choices);
        }
    }


    //* This static method is used to get a random amount of shifting for questions that ask dates. */
    public static int[] shiftArr(){
        int[] arr = new int[3];
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
            int r = (int)(Math.random() * nums.size()); 
            arr[i] = nums.get(r);
            nums.remove(r);
        }
        return arr; 
    }
}
