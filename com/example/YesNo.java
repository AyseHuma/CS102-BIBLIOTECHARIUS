package com.example;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class YesNo extends Question{

    private String answerInQuestion; 

    public YesNo(){
        question = "yesno has no parameters"; 
        answer = "yesno has no parameters";
    }

    public YesNo (Category category, Type type){  // also input the type object
        super(type, category);    // if this were a YESNO I would also use the wrongAnswer 
        answerInQuestion = type.getWrongAnswer(); 
    }

    @Override
    public VBox askQuestion(Stage stage) {
        VBox vbox = new VBox(20);

        TextArea timerTextArea = new TextArea("Timer here");
        timerTextArea.setPrefHeight(25);

        TextArea usernamesTextArea = new TextArea("Usernames here");
        usernamesTextArea.setPrefHeight(25);

        TextArea questionTextArea = new TextArea(question);
        questionTextArea.setEditable(false);
        questionTextArea.setPrefHeight(100);

        Button[] choiceButtons = new Button[4];

        choiceButtons[0] = new Button("Yes");
        choiceButtons[1] = new Button("No");

        System.out.println(answer);
        System.out.println(answerInQuestion);

        if(acceptableAnswers.contains(answerInQuestion)){    // if the answer is yes
            choiceButtons[0].setOnAction(createRightAction(stage));
            choiceButtons[1].setOnAction(createWrongAction(stage));
        } 
        else{   // if the answer is no 
            choiceButtons[0].setOnAction(createWrongAction(stage));
            choiceButtons[1].setOnAction(createRightAction(stage));
        }    

        if (category instanceof Geography) {
    Geography geo = (Geography) category;
    try {
        Image flag = new Image(getClass().getResource(geo.getFlagImagePath()).toExternalForm());
        ImageView flagView = new ImageView(flag);
        flagView.setFitWidth(150);
        flagView.setPreserveRatio(true);
        vbox.getChildren().add(flagView);
    } catch (Exception e) {
        System.err.println("Flag not found: " + geo.getFlagImagePath());
    }
}

    vbox.getChildren().addAll(timerTextArea, usernamesTextArea, questionTextArea, choiceButtons[0], choiceButtons[1]);


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

    @Override
    public String getQuestionFormatString() {  // format
        return "YesNo";
    }

    @Override
    public boolean containsAnswer(String s) {
        if(acceptableAnswers.contains(answerInQuestion)){
            if(s.equalsIgnoreCase("Yes")){
                return true; 
            }
            return false; 
        }
        else{
            if(s.equalsIgnoreCase("No")){
                return true; 
            }
            return false; 
        }
    }
}
