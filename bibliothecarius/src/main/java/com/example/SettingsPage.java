// package com.example;

// import javafx.beans.value.ChangeListener;
// import javafx.beans.value.ObservableValue;
// import javafx.geometry.Pos;
// import javafx.scene.Scene;
// import javafx.scene.control.Button;
// import javafx.scene.control.Slider;
// import javafx.scene.image.Image;
// import javafx.scene.layout.*;
// import javafx.scene.paint.Color;
// import javafx.scene.text.Font;
// import javafx.scene.text.Text;
// import javafx.stage.Stage;

// public class SettingsPage {  // TODO maybe add a timer so that matchmaking times out
//     private final ClientTester app;

//     public SettingsPage(ClientTester app) {
//         this.app = app;
//     }

//     public void show(Stage stage) {
//         Text title = new Text("SETTINGS");
//         Text t = new Text("Adjust volume");
//         Text l = new Text("No change");
//         title.setFont(Font.font("Georgia", 50));
//         t.setFont(Font.font("Georgia", 50));
//         l.setFont(Font.font("Georgia", 50));
//         title.setFill(Color.WHEAT);
//         t.setFill(Color.WHITE);
//         l.setFill(Color.WHITE);
//         title.setStyle("-fx-effect: dropshadow(gaussian, black, 10, 0, 0, 2)");
//         t.setStyle("-fx-effect: dropshadow(gaussian, black, 10, 0, 0, 2)");
//         l.setStyle("-fx-effect: dropshadow(gaussian, black, 10, 0, 0, 2)");

//         Button returnButton = new Button("Return");
//         returnButton.setOnAction(e -> app.showGameStartPage());
//         style(returnButton);

//         //Slider slider = new Slider(0, 100, app.getMediaPlayerVolume());
//         slider.setBlockIncrement(10);
//         slider.valueProperty().addListener( new ChangeListener<Number>() {  // 
//             public void changed(ObservableValue <? extends Number > observable, Number oldValue, Number newValue){
//                 l.setText("New value is: " + slider.getValue());
//                 app.setMediaPlayerVolume(slider.getValue());
//             }
//         });

//         VBox vbox = new VBox(20, title, t, l, slider, returnButton);
//         vbox.setAlignment(Pos.CENTER);
//         StackPane root = new StackPane(vbox);
//         setBackground(root, getClass().getResource("/images/2025-04-07_23-11-34.jpg").toString());
//         stage.setScene(new Scene(root, 800, 600));
//     }

//     private void style(Button... buttons) {
//         for (Button b : buttons) {
//             b.setStyle("-fx-font-size: 16px; -fx-background-color: rgba(50, 50, 50, 0.8); -fx-text-fill: white; -fx-padding: 10px 20px; -fx-background-radius: 10;");
//         }
//     }

//     private void setBackground(Pane pane, String imagePath) {
//         pane.setBackground(new Background(new BackgroundImage(new Image(imagePath), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, false, true))));
//     }
// }

