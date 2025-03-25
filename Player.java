import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Player {
    // Player sınıfının özellikleri
    private String name;
    private int ID;
    private int totalPoints;
    private ArrayList<Integer> categoryPoints;

    public Player(String name, int id, int totalPoints, ArrayList<Integer> categoryPoints) 
    {
        this.name = name;
        this.ID = id;
        this.totalPoints = totalPoints;
        this.categoryPoints = categoryPoints;
    }

    public String getStarsForCategory(int categoryPoint) 
    {
        if (categoryPoint > 60) 
        {
            return "★★★";
        } 
        else if (categoryPoint > 45) 
        {
            return "★★"; 
        } 
        else if (categoryPoint > 30) 
        {
            return "★"; 
        }
        else 
        {
            return "No Stars"; 
        }
    }

    public Stage displayPlayerInfo() 
    {
        Stage stage = new Stage();
        VBox vbox = new VBox(10);

        Label nameLabel = new Label("Player Name: " + this.name);
        Label idLabel = new Label("Player ID: " + this.ID);
        Label totalPointsLabel = new Label("Total Points: " + this.totalPoints);

        vbox.getChildren().addAll(nameLabel, idLabel, totalPointsLabel);

        for (int i = 0; i < categoryPoints.size(); i++) 
        {
            int categoryPoint = this.categoryPoints.get(i);
            String stars = getStarsForCategory(categoryPoint);
            Label categoryLabel = new Label("Category " + (i + 1) + " Points: " + categoryPoint + " " + stars);
            vbox.getChildren().add(categoryLabel);
        }

        Scene scene = new Scene(vbox, 300, 250);
        stage.setTitle("Player Information");
        stage.setScene(scene);
        stage.show();

        return stage; 
    }

    // Getter and Setter Methods
    public String getName() 
    {
        return this.name;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public int getId() 
    {
        return this.ID;
    }

    public void setId(int id) {
        this.ID = id;
    }

    public int getTotalPoints() 
    {
        return this.totalPoints;
    }

    public void setTotalPoints(int totalPoints) 
    {
        this.totalPoints = totalPoints;
    }

    public ArrayList<Integer> getCategoryPoints() 
    {
        return this.categoryPoints;
    }

    public void setCategoryPoints(ArrayList<Integer> categoryPoints) 
    {
        this.categoryPoints = categoryPoints;
    }

    public static void main(String[] args) {
        Application.launch(PlayerApp.class, args);
    }
}


