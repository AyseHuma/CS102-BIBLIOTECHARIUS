import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Player {
    private String name;
    private int Id;
    private int totalPoints;
    private ArrayList<Integer> categoryPoints;

    public Player(String name, int id, int totalPoints, ArrayList<Integer> categoryPoints) 
    {
        this.name = name;
        this.ID = id;
        this.totalPoints = totalPoints;
        this.categoryPoints = categoryPoints;
    }

    public Polygon drawStar(int x, int y, int radius) 
    {
        Polygon star = new Polygon();
        for (int i = 0; i < 5; i++) 
        {
            double angle = Math.toRadians(72 * i - 90);
            double x1 = x + radius * Math.cos(angle);
            double y1 = y + radius * Math.sin(angle);
            star.getPoints().addAll(x1, y1);
        }
        star.setFill(Color.YELLOW);
        return star;
    }

    public VBox getStarsForCategory(int categoryPoint) 
    {
        VBox starsBox = new VBox(5);

        if (categoryPoint > 60) 
        {
            starsBox.getChildren().addAll(drawStar(50, 20, 10), drawStar(50, 40, 10), drawStar(50, 60, 10));
        } 
        else if (categoryPoint > 45) 
        {
            starsBox.getChildren().addAll(drawStar(50, 20, 10), drawStar(50, 40, 10));
        } 
        else if (categoryPoint > 30) 
        {
            starsBox.getChildren().add(drawStar(50, 20, 10));
        }

        return starsBox;
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
            int categoryPoint = categoryPoints.get(i);
            Label categoryLabel = new Label("Category " + (i + 1) + " Points: " + categoryPoint);
            VBox categoryWithStars = new VBox(5);
            categoryWithStars.getChildren().addAll(categoryLabel, getStarsForCategory(categoryPoint));
            vbox.getChildren().add(categoryWithStars);
        }

        Scene scene = new Scene(vbox, 300, 350);
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

    public void setId(int id) 
    {
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



