import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
public class OpenEnded extends Question{

    public OpenEnded (Category category, Type type){  // also input the type object
        super(type, category);   // if this were a YESNO I would also use the wrongAnswer 

        // here the question is contrusted using the type object that initializes all choices too 
    }

    public OpenEnded(){ 
        answer = " ";
    }

    @Override
    public VBox askQuestion(Stage stage) {
        VBox vbox = new VBox(20);

        TextArea timerTextArea = new TextArea();
        timerTextArea.setPrefHeight(25);

        TextArea usernamesTextArea = new TextArea();
        usernamesTextArea.setPrefHeight(25);

        TextArea questionTextArea = new TextArea(question);
        questionTextArea.setEditable(false);
        questionTextArea.setPrefHeight(100);

        TextField answerInputArea = new TextField("Answer Here");
        answerInputArea.setPrefHeight(100);
        System.out.println(answer);
        answerInputArea.setOnAction(createAction(stage, answerInputArea));
    
        vbox.getChildren().addAll(timerTextArea, usernamesTextArea, questionTextArea, answerInputArea);

        return vbox;
    }  

    private EventHandler<ActionEvent> createAction(Stage stage, TextField answerInpuTextArea){
        return (new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (containsAnswer(answerInpuTextArea.getText())){
                    System.out.println("right");
                }
                else{
                    System.out.println("wrong!");
                }
                // if(answerInpuTextArea.getText().equalsIgnoreCase(answer)){
                //     System.out.println("right");
                // }
                // else{
                //     System.out.println("Wrong");
                // }
                // Type d = new TypeDate(App.questionCat.get(0), new MultipleChoice());
                // Question q = new MultipleChoice(App.questionCat.get(0), d);
                // App.questionCat.remove(0);
                // Scene s = new Scene(q.askQuestion(stage), 500, 500);
                // stage.setScene(s);                
            }
        });
    }
}
