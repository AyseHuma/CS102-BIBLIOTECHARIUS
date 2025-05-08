package com.example;

import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ClientTester extends Application{
    public static ClientConnection client;
    public Stage primaryStage; 
    private String myUsername; 
    private static String lastOpponentUsername; 
    public static void main(String[] args) {
        launch(args);
    }

    public static VBox parseQuestionMessage(String message, Stage stage){
        int questionSeparateIndex = message.indexOf("?*:");   // plus one so it includes the question mark

        String questionString = message.substring(9, questionSeparateIndex + 1); // plus one so it includes the question mark
        String rightPart = message.substring(questionSeparateIndex + 3);  // skip ?*:

        String[] rightParts = rightPart.split(":");
        String questionType = rightParts[0];

        if (questionType.equals("MultipleChoice")) {
            ArrayList<String> choices = new ArrayList<String>();
            for (int i = 1; i < 5; i++) {
                choices.add(rightParts[i]);
            }
            return askQuestionMultipleChoice(stage, questionString, choices);
        }
        else if (questionType.equals("OpenEnded")){
            return askQuestionOpenEnded(stage, questionString);
        }
        else if (questionType.equals("YesNo")){
            return askQuestionYesNo(stage, questionString);
        }
        else{
            System.out.println("MISTAKE IN QUESTION MESSAGE " + questionType);
            return null; 
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage; 

        // Scene s = new Scene(new VBox(), 500, 500);
        // stage.setTitle("BIBLIOTHECARIUS");    //TODO on close, make sure the connection is discharged and if this happens during a match opponent wins
        // stage.setScene(s);
        // stage.show();
        showMainPage();


        client = new ClientConnection();
        
        if (client.connect("172.27.16.1", 12345)) {
            System.out.println("Connected to the server!");

            // Scanner in = new Scanner(System.in);
            // String username = in.nextLine();
            // String pass = in.nextLine();
            // client.sendMessage("SIGN_IN_REQUEST:" + username + ":" + pass);

            // Listen for the welcome message
            new Thread(() -> {
                try {
                    String msg;
                    while ((msg = client.receiveMessage()) != null) {
                        System.out.println("Server says: " + msg);

                        if (msg.equals("SIGNED_UP") || msg.equals("SIGNED_IN")) {
                            client.sendMessage("MATCH_REQUEST");
                        }
                        else if (msg.equals("FAILED_SIGN_IN") || msg.equals("FAILED_SIGN_UP")){
                            Platform.runLater(() -> {  
                                showMainPage();
                            });
                        }
                        if (msg.length() > "MATCH_FOUND".length() && msg.substring(0,12).equals("MATCH_FOUND:")) {
                            lastOpponentUsername = msg.substring(12);
                        }
                        else if (msg.length() > "QUESTION".length() && msg.substring(0,9).equals("QUESTION:")) {
                            final String finalMsg = msg; 
                            Platform.runLater(() -> {   // this is because we are in a different thread
                                VBox questionPane = parseQuestionMessage(finalMsg, stage);
                                if (questionPane != null) {
                                    Scene scene = new Scene(questionPane, 500, 500);
                                    stage.setScene(scene);
                                } else {
                                    System.out.println("Failed to parse question.");
                                }
                            });
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();

        } else {
            System.out.println("Failed to connect to the server.");
        }
    }

    public static VBox askQuestionMultipleChoice(Stage stage, String question, ArrayList<String> choices) {
        VBox vbox = new VBox(20);

        TextArea timerTextArea = new TextArea();
        timerTextArea.setPrefHeight(25);

        TextArea usernamesTextArea = new TextArea("Playing against: " + lastOpponentUsername);
        usernamesTextArea.setPrefHeight(25);

        TextArea questionTextArea = new TextArea(question);
        questionTextArea.setEditable(false);
        questionTextArea.setPrefHeight(100);

        GridPane choicesGrid = new GridPane();
        choicesGrid.setHgap(10); // this spacing will be adjucted
        choicesGrid.setVgap(10);

        Button[] choiceButtons = new Button[4];
        java.util.Collections.shuffle(choices);

        for (int i = 0; i < 4; i++) {
            final String choiceText = choices.get(i);  // final because class will use it
            choiceButtons[i] = new Button((char)('A' + i) + ". " + choiceText);
    
            choiceButtons[i].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    client.sendMessage("ANSWER:" + choiceText);
                }
            });
        }

        for(int i = 0; i<4; i++){   
            choicesGrid.add(choiceButtons[i], (i%2), ((int)(i/(2.0))));
        }

        vbox.getChildren().addAll(timerTextArea, usernamesTextArea, questionTextArea, choicesGrid);

        return vbox;
    }

    public static VBox askQuestionYesNo(Stage stage, String question) {
        VBox vbox = new VBox(20);

        TextArea timerTextArea = new TextArea("Timer here");
        timerTextArea.setPrefHeight(25);

        TextArea usernamesTextArea = new TextArea("Playing against: " + lastOpponentUsername);
        usernamesTextArea.setPrefHeight(25);

        TextArea questionTextArea = new TextArea(question);
        questionTextArea.setEditable(false);
        questionTextArea.setPrefHeight(100);

        Button[] choiceButtons = new Button[2];

        choiceButtons[0] = new Button("Yes");
        choiceButtons[0].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                client.sendMessage("ANSWER:Yes");
            }
        });
        choiceButtons[1] = new Button("No");
        choiceButtons[1].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                client.sendMessage("ANSWER:No");
            }
        });



        vbox.getChildren().addAll(timerTextArea, usernamesTextArea, questionTextArea, choiceButtons[0], choiceButtons[1]);

        return vbox;
    }

    public static VBox askQuestionOpenEnded(Stage stage, String question) {
        VBox vbox = new VBox(20);

        TextArea timerTextArea = new TextArea();
        timerTextArea.setPrefHeight(25);

        TextArea usernamesTextArea = new TextArea("Playing against: " + lastOpponentUsername);
        usernamesTextArea.setPrefHeight(25);

        TextArea questionTextArea = new TextArea(question);
        questionTextArea.setEditable(false);
        questionTextArea.setPrefHeight(100);

        TextField answerInputArea = new TextField("Answer Here");
        answerInputArea.setPrefHeight(100);
        answerInputArea.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                client.sendMessage("ANSWER:" + answerInputArea.getText());
            }
        });
    
        vbox.getChildren().addAll(timerTextArea, usernamesTextArea, questionTextArea, answerInputArea);

        return vbox;
    }  

    public void showMainPage(){
        new MainPage(this).show(primaryStage);
    }
    
    public void showSignInPage() {
        new SignInPage(this).show(primaryStage);
    }    

    public void showSignUpPage() {
        new SignUpPage(this).show(primaryStage);
    }

    public void sendSignInRequest(String username, String password){
        client.sendMessage("SIGN_IN_REQUEST:" + username + ":" + password);
    }

    public void sendSignUpRequest(String username, String password){
        client.sendMessage("SIGN_UP_REQUEST:" + username + ":" + password);
    }
}