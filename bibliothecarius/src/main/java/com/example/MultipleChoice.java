package com.example;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MultipleChoice extends Question{
    
    private ArrayList<String> choices; 

    @Override
    public VBox askQuestion(Stage stage) {
        // System.out.println(question);
        // for (String s : choices) {
        //     System.out.println(s);   // this will actually return 4 buttons, one right and 3 wrong. right one will be checked by comparing the answer to ANSWER.
        // }

        VBox vbox = new VBox(20);

        TextArea timerTextArea = new TextArea();
        timerTextArea.setPrefHeight(25);

        TextArea usernamesTextArea = new TextArea();
        usernamesTextArea.setPrefHeight(25);

        TextArea questionTextArea = new TextArea(question);
        questionTextArea.setEditable(false);
        questionTextArea.setPrefHeight(100);

        GridPane choicesGrid = new GridPane();
        choicesGrid.setHgap(10); // this spacing will be adjucted
        choicesGrid.setVgap(10);

        Button[] choiceButtons = new Button[4];

        choiceButtons[0] = new Button("A " + choices.get(0));
        choiceButtons[1] = new Button("B " + choices.get(1));
        choiceButtons[2] = new Button("C " + choices.get(2));
        choiceButtons[3] = new Button("D " + choices.get(3));

        int r = (int)(Math.random()*4);  // chooses where the correct option will be 

        int regular = 0; 
        for(int i = 0; i<4; i++){   // randomly assigns the correct answer
            if(i == r){
                choiceButtons[i] = new Button((i + 1) + ". " + choices.get(3));
                choiceButtons[i].setOnAction(createRightAction(stage));
            }
            else{
                choiceButtons[i] = new Button((i + 1) + ". " + choices.get(regular));
                choiceButtons[i].setOnAction(createWrongAction(stage));
                regular ++;  
            }
            choicesGrid.add(choiceButtons[i], (i%2), ((int)(i/(2.0))));
        }

        vbox.getChildren().addAll(timerTextArea, usernamesTextArea, questionTextArea, choicesGrid);

        return vbox;
    }

    private EventHandler<ActionEvent> createRightAction(Stage stage) {
        return new EventHandler<ActionEvent>() {   //TODO understand what this line does
            @Override
            public void handle(ActionEvent event) {
                System.out.println("RIGHT");
                // App.morePoints();  
                // App.nextQuestion(stage);  
            }
        };
    }
    
    private EventHandler<ActionEvent> createWrongAction(Stage stage) {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("WRONG");
                // TODO: add a way for the other participant to answer
                // App.nextQuestion(stage);  
            }
        };
    }

    public MultipleChoice (Category category, Type type){  // also input the type object
        super(type, category);
        choices = type.getChoices(category);
        // here the question is contrusted using the type object that initializes all choices too 
    }

    public MultipleChoice(){ 
        answer = " ";
        choices = new ArrayList<>();
    }   

    @Override
    public String getQuestionFormatString() {  //format as well as the answers in multiple choice
        return "MultipleChoice:" + choices.get(0) + ":" + choices.get(1) + ":" + choices.get(2) + ":" + choices.get(3);
    }

    @Override
    public boolean containsAnswer(String s) {
        return acceptableAnswers.contains(s);
    }
}
