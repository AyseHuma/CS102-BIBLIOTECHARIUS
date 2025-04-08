import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application{
    public int points = 0; 
    public static ArrayList<Category> questionCat = new ArrayList<Category>();
    public Question[] questionTypes = {new MultipleChoice(), new OpenEnded(), new YesNo()};

    public static void main(String[] args)
    {
        Movie.fillTconstsFromIMDB(8.5, 25000);
        launch(args);

        // Movie m = new Movie();
        // Type d = new TypeDate(m, new MultipleChoice());
        // Question q = new MultipleChoice(m, d);
        // q.askQuestion();
        // Movie movie2 = new Movie(); 
        // Type type2 = new TypeGenre(movie2, new MultipleChoice());
        // Question question2 = new MultipleChoice(movie2, type2);
        // question2.askQuestion();
        // Type type25 = new TypeGenre(movie2, new YesNo());
        // Question question3 = new YesNo(movie2, type25);
        // question3.askQuestion();
    }

    @Override
    public void start(Stage stage) throws Exception {
        // for(int i = 0; i<2; i++){
        //     questionCat.add(new Movie());   
        // }

        // Movie m = new Movie();
        // Type d = new TypeDate(m, new MultipleChoice());
        // Question q = new MultipleChoice(m, d);

        // Type d = new TypeDate(questionCat.get(0), new MultipleChoice());
        // Question q = new MultipleChoice(questionCat.get(0), d);
        // questionCat.remove(0);

        Movie m = new Movie();
        Type d = new TypeName(m, new OpenEnded());
        Question q = new OpenEnded(m, d);

        // Type d = new TypeDate(questionCat.get(0), new OpenEnded());
        // Question q = new OpenEnded(questionCat.get(0), d);
        // questionCat.remove(0);

        Scene s = new Scene(q.askQuestion(stage), 500, 500);
        stage.setTitle("BIBLIOTHECARIUS");
        stage.setScene(s);
        stage.show();
    }
}