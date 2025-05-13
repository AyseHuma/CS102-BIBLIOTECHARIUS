// package com.example;

// import javafx.application.Application;
// import javafx.geometry.Insets;
// import javafx.scene.Scene;
// import javafx.scene.control.*;
// import javafx.scene.layout.*;
// import javafx.stage.Stage;
// import java.sql.SQLException;
// import java.util.List;

// public class AddFriendPage extends Application {

//     private static int currentUserId;

//     private static TextField friendNameField;
//     private static Label requestStatusLabel;
//     private static ListView<String> requestListView;
//     private static ListView<String> friendListView;

//     public static void main(String[] args) {
//         addFriend(15); 
//     }

//     public static void addFriend(int userId) {
//         currentUserId = userId;
//         launch();
//     }

//     @Override
//     public void start(Stage primaryStage) {
//         primaryStage.setTitle("Friend List");

//         Label instruction = new Label("Send Friend Request:");
//         friendNameField = new TextField();
//         friendNameField.setPromptText("Enter friend's username");

//         Button sendRequestButton = new Button("Send Request");
//         requestStatusLabel = new Label();

//         //sendRequestButton.setOnAction(e -> sendFriendRequest());

//         VBox sendBox = new VBox(5, instruction, friendNameField, sendRequestButton, requestStatusLabel);
//         sendBox.setPadding(new Insets(10));
//         sendBox.setStyle("-fx-border-color: gray; -fx-border-radius: 5; -fx-padding: 10;");

//         Label requestsLabel = new Label("Waiting Friend Requests:");
//         requestListView = new ListView<>();
//         Button acceptButton = new Button("Accept Selected Request");
//         Label acceptStatusLabel = new Label();

//         acceptButton.setOnAction(e -> acceptSelectedRequest(acceptStatusLabel));

//         Button rejectButton = new Button("Reject Selected Request");
//         rejectButton.setOnAction(e -> rejectSelectedRequest(acceptStatusLabel));

//         VBox requestBox = new VBox(5, requestsLabel, requestListView, acceptButton, rejectButton, acceptStatusLabel);
//         requestBox.setPadding(new Insets(10));
//         requestBox.setStyle("-fx-border-color: gray; -fx-border-radius: 5; -fx-padding: 10;");

//         Label friendListLabel = new Label("Your Friends:");
//         friendListView = new ListView<>();

//         Button removeFriendButton = new Button("Remove Selected Friend");
//         Label removeStatusLabel = new Label();
//         removeFriendButton.setOnAction(e -> removeSelectedFriend(removeStatusLabel));

//         VBox friendBox = new VBox(5, friendListLabel, friendListView, removeFriendButton, removeStatusLabel);
//         friendBox.setPadding(new Insets(10));
//         friendBox.setStyle("-fx-border-color: gray; -fx-border-radius: 5; -fx-padding: 10;");

//         refreshRequestsAndFriends();

//         HBox layout = new HBox(15, sendBox, requestBox, friendBox);
//         layout.setPadding(new Insets(20));

//         primaryStage.setScene(new Scene(layout, 800, 400));
//         primaryStage.show();
//     }

//     public static void sendFriendRequest(int userId, String friendName) {

//         try
//         {
//             int result = AccountDb.sendFriendRequest(userId, friendName);
//             switch (result) {
//                 case 1 -> requestStatusLabel.setText("Friend request sent.");
//                 case 0 -> requestStatusLabel.setText("Already friends.");
//                 case -1 -> requestStatusLabel.setText("User not found.");
//                 case -2 -> requestStatusLabel.setText("Cannot add yourself.");
//                 case -3 -> requestStatusLabel.setText("Request already sent.");
//                 default -> requestStatusLabel.setText("Error sending request.");
//             }
//             refreshRequestsAndFriends();
//         }
//         catch (SQLException e)
//         {
//             requestStatusLabel.setText("Database error: " + e.getMessage());
//         }
//     }

//     private static void acceptSelectedRequest(Label statusLabel)
//     {
//         String selectedUsername = requestListView.getSelectionModel().getSelectedItem();
//         if (selectedUsername == null)
//         {
//             statusLabel.setText("Select a request first.");
//             return;
//         }

//         try
//         {
//             int requesterId = AccountDb.getUserIdByName(selectedUsername);
//             boolean accepted = AccountDb.acceptFriendRequest(currentUserId, requesterId);
//             statusLabel.setText(accepted ? "Friend request accepted." : "Error accepting request.");
//             refreshRequestsAndFriends();
//         }
//         catch (SQLException e)
//         {
//             statusLabel.setText("Database error: " + e.getMessage());
//         }
//     }



//     private static void rejectSelectedRequest(Label statusLabel) {
//         String selectedUsername = requestListView.getSelectionModel().getSelectedItem();
//         if (selectedUsername == null)
//         {
//             statusLabel.setText("Select a request first.");
//             return;
//         }

//         try
//         {
//             int requesterId = AccountDb.getUserIdByName(selectedUsername);
//             boolean rejected = AccountDb.rejectFriendRequest(currentUserId, requesterId);
//             statusLabel.setText(rejected ? "Request rejected." : "Error rejecting request.");
//             refreshRequestsAndFriends();
//         }
//         catch (SQLException e)
//         {
//             statusLabel.setText("Database error: " + e.getMessage());
//         }
//     }



//     private static void removeSelectedFriend(Label statusLabel) {
//         String selected = friendListView.getSelectionModel().getSelectedItem();
//         if (selected == null || selected.isBlank()) {
//             statusLabel.setText("Select a friend first.");
//             return;
//         }

//         try {
//             int friendId = AccountDb.getUserIdByName(selected);
//             boolean removed = AccountDb.removeFriend(currentUserId, friendId);
//             if (removed)
//             {
//                 statusLabel.setText("Friend removed.");
//             }
//             else
//             {
//                 statusLabel.setText("Could not remove friend.");
//             }
//             refreshRequestsAndFriends();
//         }
//         catch (SQLException e) {
//             statusLabel.setText("Database error: " + e.getMessage());
//         }
//     }





//     public static void refreshRequestsAndFriends() {
//         try
//         {
//             requestListView.getItems().clear();
//             friendListView.getItems().clear();

//             List<String> requests = AccountDb.getPendingRequests(currentUserId);
//             List<String> friends = AccountDb.getFriendList(currentUserId);

//             requestListView.getItems().addAll(requests);
//             friendListView.getItems().addAll(friends);
//         }
//         catch (SQLException e)
//         {
//             requestListView.getItems().add("Error loading requests.");
//             friendListView.getItems().add("Error loading friends.");
//         }
//     }
// }

