package com.example;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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

        TextField answerInputArea = new TextField();
        answerInputArea.setPromptText("ANSWER HERE");

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
                    System.out.println("RIGHT");
                    // App.morePoints();  
                    // App.nextQuestion(stage);  
                }
                else{
                    System.out.println("WRONG");
                    // TODO: add a way for the other participant to answer
                    // App.nextQuestion(stage);  
                }               
            }
        });
    }

    @Override
    public String getQuestionFormatString() {
        return "OpenEnded";
    }

    @Override
    public boolean containsAnswer(String s) {
        return acceptableAnswers.contains(s);
    }
}
