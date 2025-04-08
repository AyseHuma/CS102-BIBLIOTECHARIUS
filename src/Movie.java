import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Movie extends Category{
    private static ArrayList<String> tconsts = new ArrayList<>();    

    public Movie(){
        possibleFalseGenres.add("Horror");
        possibleFalseGenres.add("Talk-Show");
        possibleFalseGenres.add("Action");
        possibleFalseGenres.add("Comedy");
        possibleFalseGenres.add("Thriller");
        possibleFalseGenres.add("Documentary");
        possibleFalseGenres.add("Adventure");
        possibleFalseGenres.add("Fantasy");
        possibleFalseGenres.add("Reality-TV");
        possibleFalseGenres.add("Music");
        possibleFalseGenres.add("Drama");
        possibleFalseGenres.add("Animation");
        possibleFalseGenres.add("Sci-Fi");
        possibleFalseGenres.add("Crime");
        possibleFalseGenres.add("Mystery");

        getRandomtConst();

        ArrayList<String> willRemove = new ArrayList<>(); 

        for(int i = 0; i<possibleFalseGenres.size(); i++){   // makes sure that the random picks something not included in c.genresarr 
            for(int j = 0; j<getGenresArr().size(); j++){
                if(getGenresArr().get(j).equalsIgnoreCase(possibleFalseGenres.get(i))){
                    willRemove.add(possibleFalseGenres.get(i));
                }
            }
        }
        for (int i = 0; i<willRemove.size(); i++) {
            possibleFalseGenres.remove(willRemove.get(i));
        }

        System.out.println(genresArr);
        System.out.println(possibleFalseGenres);
    }

    public static void fillTconstsFromIMDB(double averageRating, int numVotes){
        try
        (
        // create a database connection
        Connection connection = DriverManager.getConnection("jdbc:sqlite:lib/imdb-data (1).db");
        Statement statement = connection.createStatement();
        )
        {        
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            ResultSet rs = statement.executeQuery("select * from ratings where numVotes>" + numVotes + " and averageRating>" + averageRating);
            
            while(rs.next()){
                tconsts.add(rs.getString("tconst"));                
            }    

            // int k = tconsts.size(); 
            // System.out.println(k);
            // for(int i = k - 1; i >= 0 ; i--){
            //     rs = statement.executeQuery("select * from basics");
            //     boolean found = false; 
            //     while(!found && rs.next()){
            //         if(rs.getString("tconst").equals(tconsts.get(i)) && rs.getString("genres").equals("\\N")){    // this means that the genres is null so I remove it
            //             tconsts.remove(i);
            //         }   
            //     } 
            // }
        }
        catch(SQLException e)
        {
        // if the error message is "out of memory",
        // it probably means no database file is found
        e.printStackTrace(System.err);
        }
    }

    public String getOneRandomName(){    // so that 
        ArrayList<String> chooseRandom = new ArrayList<>(); 
        ArrayList<String> chooseRandomCreators = new ArrayList<>(); 
        int m = (int)(Math.random()*(possibleFalseCreatorsTitles.size())); 
        String willParse = possibleFalseCreatorsTitles.get(m);

        int commaNum = -1; 
        for(int i = 0; i<willParse.length(); i++){
            if(willParse.charAt(i) == ','){
                chooseRandom.add(willParse.substring(commaNum + 1, i));
                commaNum = i; 
            }
            else if(i == (willParse.length() -1)){
                chooseRandom.add(willParse.substring(commaNum + 1));
            }
        }

        m = (int)(Math.random()*(chooseRandom.size())); 
        String randomTConst = chooseRandom.get(m); 

        try
        (
        Connection connection = DriverManager.getConnection("jdbc:sqlite:lib/imdb-data (1).db");
        Statement statement = connection.createStatement();
        )
        {        
            ResultSet rs = statement.executeQuery("select * from name");

            while(rs.next()){
                if(rs.getString("knownForTitles").contains(randomTConst)){
                    chooseRandomCreators.add(rs.getString("primaryName"));
                }
            }
        }
        catch(SQLException e)
        {
        // if the error message is "out of memory",
        // it probably means no database file is found
        e.printStackTrace(System.err);
        }    
        m = (int)(Math.random()*(chooseRandomCreators.size())); 
        return chooseRandomCreators.get(m); 
    }

    public void getRandomtConst(){

        int randomIndex = (int)(Math.random()*tconsts.size());
        String randomTConst = tconsts.get(randomIndex);

        try
        (
        // create a database connection
        Connection connection = DriverManager.getConnection("jdbc:sqlite:lib/imdb-data (1).db");
        Statement statement = connection.createStatement();
        )
        {        
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            ResultSet rs = statement.executeQuery("select * from basics");
            boolean found = false; 
            while(!found && rs.next()){
                if(rs.getString("tconst").equals(randomTConst)){
                    title = rs.getString("primaryTitle");
                    releaseDate = rs.getInt("startYear");
                    genres = rs.getString("genres");
                    parseGenres(genres);
                    found = true; 
                }
            }     
            
            rs = statement.executeQuery("select * from name");
            System.out.println(randomTConst);
            while(rs.next()){
                if(rs.getString("knownForTitles").contains(randomTConst)){
                    creators.add(rs.getString("primaryName"));
                    possibleFalseCreatorsTitles.add(rs.getString("knownForTitles"));
                }
            }
            System.out.println("here are the creators : " + creators);
        }
        catch(SQLException e)
        {
        // if the error message is "out of memory",
        // it probably means no database file is found
        e.printStackTrace(System.err);
        }             
    }

    private void parseGenres(String genres){
        int k = 0; 
        for(int i = 0; i<genres.length(); i++){
            if(genres.charAt(i) == ','){
                genresArr.add(genres.substring(k, i));
                k = i + 1;
            }
        }
    }
}
