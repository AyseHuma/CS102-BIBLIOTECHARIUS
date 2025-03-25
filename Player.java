import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Player {
    private String name;
    private int ID;
    private int totalPoints;
    private int categoryPoints;

    public Player(String name, int id, int totalPoints, int categoryPoints) 
    {
        this.name = name;
        this.ID = id;
        this.totalPoints = totalPoints;
        this.categoryPoints = categoryPoints;
    }
  
    public Stage displayPlayerInfo() 
    {
        Stage stage = new Stage();
        VBox vbox = new VBox(10);
      
        Label nameLabel = new Label("Player Name: " + name);
        Label idLabel = new Label("Player ID: " + ID);
        Label totalPointsLabel = new Label("Total Points: " + totalPoints);
        Label categoryPointsLabel = new Label("Category Points: " + categoryPoints);

        vbox.getChildren().addAll(nameLabel, idLabel, totalPointsLabel, categoryPointsLabel);

        Scene scene = new Scene(vbox, 300, 200);
        stage.setTitle("Player Information");
        stage.setScene(scene);
        stage.show();

        return stage; 
    }

    // Getter and Setter Methods
    public String getName() 
    {
        return name;
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

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public int getCategoryPoints() 
    {
        return this.categoryPoints;
    }

    public void setCategoryPoints(int categoryPoints) 
    {
        this.categoryPoints = categoryPoints;
    }

    public static void main(String[] args) 
    {
        Application.launch(PlayerApp.class, args);
    }
}




