package com.example;

import java.net.Socket;
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
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ClientTester extends Application{
    public static ClientConnection client;
    public Stage primaryStage; 
    private String myUsername; 
    private String lastOpponentUsername; 
    private String lastPlayedCategory;
    private String lastChosenSubcategory; 
    
    private volatile boolean goesOn = true; // volatile so that a change in this is quickly seen by other threads and they also stop 
    public static void main(String[] args) {
        launch(args);
    }

    public VBox parseQuestionMessage(String message, Stage stage){
        int questionSeparateIndex = message.indexOf("?*:");   

        String leftString = message.substring(9, questionSeparateIndex + 1);  // has QID:questionString and plus one for the question mark 
        int index = leftString.indexOf(':'); 
        String idPart = leftString.substring(0, index);
        String questionString = leftString.substring(index + 1);  
        String ID = idPart.substring(1);  // "12"
        String rightPart = message.substring(questionSeparateIndex + 3);

        String[] rightParts = rightPart.split(":");
        String questionType = rightParts[0];

        if (questionType.equals("MultipleChoice")) {
            ArrayList<String> choices = new ArrayList<String>();
            for (int i = 1; i < 5; i++) {
                choices.add(rightParts[i]);
            }
            return askQuestionMultipleChoice(stage, questionString, choices, ID);
        }
        else if (questionType.equals("OpenEnded")){
            return askQuestionOpenEnded(stage, questionString, ID);
        }
        else if (questionType.equals("YesNo")){
            return askQuestionYesNo(stage, questionString, ID);
        }
        else{
            System.out.println("MISTAKE IN QUESTION MESSAGE " + questionType);
            return null; 
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        client = new ClientConnection();

        this.primaryStage = stage; 
        
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
        public void handle(WindowEvent we) {
                String d = "DISCONNECT";
                System.out.println("CLOSING_WINDOW");
                goesOn = false; 
                client.sendMessage(d); // else it gives an error
                client.close();                
            }
        });

        // Scene s = new Scene(new VBox(), 500, 500);
        // stage.setTitle("BIBLIOTHECARIUS");    //TODO on close, make sure the connection is discharged and if this happens during a match opponent wins
        // stage.setScene(s);
        // stage.show();

        if (client.connect("172.27.16.1", 12345)) {
            System.out.println("Connected to the server!");
            showMainPage();

            // Listen for message
            new Thread(() -> {
                try {
                    String msg;
                    while (goesOn && (msg = client.receiveMessage()) != null) {
                        System.out.println("Server says: " + msg);

                        if (msg.equals("FAILED_SIGN_IN") || msg.equals("FAILED_SIGN_UP")){
                            Platform.runLater(() -> {  
                                showMainPage();
                            });
                        }
                        // else if (msg.equals("WAITING_FOR_OPPONENT")){
                        //     Platform.runLater(() -> {  
                        //         showLoadingPage();
                        //     });
                        // }
                        else if (msg.length() > "SIGNED_UP".length() && (msg.startsWith("SIGNED_UP:") || msg.startsWith("SIGNED_IN:"))){
                            myUsername = msg.substring(10);
                            Platform.runLater(() -> {  
                                showGameStartPage();
                            });
                        }
                        else if (msg.length() > "MATCH_FOUND".length() && msg.substring(0,12).equals("MATCH_FOUND:")) {
                            String[] lastOpponentAndCategory = msg.substring(12).split(":");
                            lastOpponentUsername = lastOpponentAndCategory[0];
                            lastPlayedCategory = lastOpponentAndCategory[1];
                        }
                        else if (msg.length() > "QUESTION".length() && msg.substring(0,9).equals("QUESTION:")) {
                            int lastColon = msg.lastIndexOf(":");
                            String pointPlayerOther = msg.substring(lastColon + 1);
                            msg = msg.substring(0, lastColon);
                            lastColon = msg.lastIndexOf(":");
                            String pointPlayerMe = msg.substring(lastColon + 1);
                            msg = msg.substring(0, lastColon);
                            lastColon = msg.lastIndexOf(":");
                            int timeLeft = Integer.parseInt(msg.substring(lastColon + 1));
                            final String finalMsg = msg.substring(0,lastColon); 
                            Platform.runLater(() -> {   // this is because we are in a different thread
                                VBox questionPane = parseQuestionMessage(finalMsg, stage);
                                if (questionPane != null) {
                                    // Scene scene = new Scene(questionPane, 500, 500);
                                    // stage.setScene(scene);
                                    showQuestionPage(questionPane, timeLeft, pointPlayerMe, pointPlayerOther, myUsername, lastOpponentUsername);
                                } else {
                                    System.out.println("Failed to parse question.");
                                }
                            });
                        }
                        else if (msg.length() > "GAME_OVER".length() && msg.substring(0,10).equals("GAME_OVER:")){
                            String[] playerPoints = msg.substring(10).split(":");
                            Platform.runLater(() -> {  
                                showEndGamePage(lastPlayedCategory, playerPoints[0], playerPoints[1]);
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

    public VBox askQuestionMultipleChoice(Stage stage, String question, ArrayList<String> choices, String ID) {
        VBox vbox = new VBox(20);

        TextArea questionTextArea = new TextArea(ID + ". " + question);
        questionTextArea.setEditable(false);
        questionTextArea.setPrefHeight(100);
        
        questionTextArea.setBackground(Background.EMPTY);
        questionTextArea.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 18px; -fx-text-fill: white; -fx-control-inner-background: black;");

        GridPane choicesGrid = new GridPane();
        choicesGrid.setHgap(10); // this spacing will be adjucted
        choicesGrid.setVgap(10);

        Button[] choiceButtons = new Button[4];
        java.util.Collections.shuffle(choices);

        for (int i = 0; i < 4; i++) {
            final String choiceText = choices.get(i);  // final because class will use it
            choiceButtons[i] = new Button((char)('A' + i) + ". " + choiceText);
        }
        
        for (int i = 0; i < 4; i++){
            final String choiceText = choices.get(i);
            choiceButtons[i].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    client.sendMessage("ANSWER:" + "Q" + ID +  ":" + choiceText);
                    for (int i = 0; i < 4; i++) {
                        choiceButtons[i].setDisable(true);
                    }
                }
            });
        }

        for(int i = 0; i<4; i++){   
            choicesGrid.add(choiceButtons[i], (i%2), ((int)(i/(2.0))));
        }

        vbox.getChildren().addAll(questionTextArea, choicesGrid);

        return vbox;
    }

    public VBox askQuestionYesNo(Stage stage, String question, String ID) {
        VBox vbox = new VBox(20);

        TextArea questionTextArea = new TextArea(ID + ". " + question);
        questionTextArea.setEditable(false);
        questionTextArea.setPrefHeight(100);
        questionTextArea.setEditable(false);
        
        questionTextArea.setBackground(Background.EMPTY);
        questionTextArea.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 18px; -fx-text-fill: white; -fx-control-inner-background: black;");

        Button[] choiceButtons = new Button[2];

        choiceButtons[0] = new Button("Yes");
        choiceButtons[1] = new Button("No");
        choiceButtons[0].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                client.sendMessage("ANSWER:" + "Q" + ID + ":" + "Yes");
                choiceButtons[0].setDisable(true);
                choiceButtons[1].setDisable(true);
            }
        });
        choiceButtons[1].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                client.sendMessage("ANSWER:" + "Q" + ID + ":" + "No");
                choiceButtons[0].setDisable(true);
                choiceButtons[1].setDisable(true);
            }
        });



        vbox.getChildren().addAll( questionTextArea, choiceButtons[0], choiceButtons[1]);

        return vbox;
    }

    public VBox askQuestionOpenEnded(Stage stage, String question, String ID) {
        VBox vbox = new VBox(20);

        TextArea questionTextArea = new TextArea(ID + ". " + question);
        questionTextArea.setEditable(false);
        questionTextArea.setPrefHeight(100);
        questionTextArea.setEditable(false);
        questionTextArea.setBackground(Background.EMPTY);
        questionTextArea.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 18px; -fx-text-fill: white; -fx-control-inner-background: black;");

        TextField answerInputArea = new TextField("Answer Here");
        answerInputArea.setPrefHeight(100);
        answerInputArea.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                client.sendMessage("ANSWER:" + "Q" + ID + ":" + answerInputArea.getText());
                answerInputArea.setDisable(true);
            }
        });
    
        vbox.getChildren().addAll(questionTextArea, answerInputArea);

        return vbox;
    }  

    public void setLastChosenSubcategory(String subcat){
        lastChosenSubcategory = subcat; 
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

    public void showQuestionPage(VBox vbox, int timeLeft, String pM, String pO, String myName, String opponentName) {
        new QuestionPage(this, vbox, timeLeft, pM, pO, myName, opponentName).show(primaryStage);
    }

    public void showEndGamePage(String category, String player1Points, String player2Points){
        new EndGamePage(this).show(primaryStage, category, myUsername, Integer.parseInt(player1Points), lastOpponentUsername, Integer.parseInt(player2Points));
    }

    public void showGameStartPage(){
        new GameStartPage(this).show(primaryStage);
    }

    public void showCatalogPage(){
        new CatalogPage(this).show(primaryStage);
    }

    public void showComponentChoicePage(String category, String subcat){
        new ComponentChoicePage(this, category, subcat).show(primaryStage);
    }

    public void showLoadingPage(){
        new LoadingPage(this).show(primaryStage);
    }

    public void showMovieSubcategoryPage(){
        new MovieSubcategoryPage(this).show(primaryStage);
    }

    public void showBookSubcategoryPage(){
        new BookSubcategoryPage(this).show(primaryStage);
    }

    public void sendSignInRequest(String username, String password){
        client.sendMessage("SIGN_IN_REQUEST:" + username + ":" + password);
    }

    public void sendSignUpRequest(String username, String password){
        client.sendMessage("SIGN_UP_REQUEST:" + username + ":" + password);
    }

    public void sendMatchRequest(String category, String subcat){
        client.sendMessage("MATCH_REQUEST:" + category + ":" + subcat);
    }

    public void sendCancelMatchmakingRequest(){
        client.sendMessage("CANCEL_MATCH_REQUEST");
    }

    public void sendDisconnectRequest(){
        client.sendMessage("DISCONNECT");
        client.close();
        primaryStage.close();
        goesOn = false; 
    }
}