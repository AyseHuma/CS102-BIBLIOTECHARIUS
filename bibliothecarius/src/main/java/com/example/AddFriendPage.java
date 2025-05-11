package com.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddFriendPage extends Application {

    private static int currentUserId;

    private ListView<String> friendListView = new ListView<>();

    public static void main(String[] args) 
    {
        addFriend(8);
    }

    public static void addFriend(int userId) 
    {
        currentUserId = userId;
        launch();
    }

    @Override
    public void start(Stage primaryStage) 
    {
        primaryStage.setTitle("Add a Friend");

        Label instruction = new Label("Enter friend's username:");
        TextField friendNameField = new TextField();
        Button addButton = new Button("Add Friend");
        Button refreshButton = new Button("Refresh Friend List");
        Label resultLabel = new Label();

        addButton.setOnAction(e -> {
            String friendName = friendNameField.getText().trim();
            if (friendName.isEmpty()) 
            {
                resultLabel.setText("Please enter a valid name.");
                return;
            }

            try 
            {
                int friendId = AccountDb.getUserIdByName(friendName);
                if (friendId == -1) 
                {
                    resultLabel.setText("No user with that name exists.");
                }
                else
                {
                    int result = AccountDb.addFriend(currentUserId, friendId);
                    switch (result) {
                        case -2 -> resultLabel.setText("Cannot add yourself as a friend.");
                        case -1 -> resultLabel.setText("That user does not exist.");
                        case 0 -> resultLabel.setText("You are already friends with " + friendName);
                        case 1 -> {
                            resultLabel.setText("Friend added successfully.");
                            refreshFriendList();
                        }
                    }
                }
            }
            catch (Exception ex) 
            {
                resultLabel.setText("An error occurred: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
        refreshButton.setOnAction(e -> refreshFriendList());

        VBox layout = new VBox(10,
                instruction,
                friendNameField,
                addButton,
                refreshButton,
                resultLabel,
                new Label("Your Friends:"),
                friendListView
        );
        layout.setPadding(new Insets(20));
        Scene scene = new Scene(layout, 350, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
        refreshFriendList();
    }

    private void refreshFriendList() {
        try {
            String[] friends = AccountDb.loadFriendList(currentUserId);
            friendListView.getItems().setAll(friends);
        } catch (Exception e) {
            friendListView.getItems().setAll("Error loading friends.");
            e.printStackTrace();
        }
    }
}
