package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

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
    }

    public static void fillTconstsFromIMDB(double averageRating, int numVotes){
        tconsts = new ArrayList<>();
        try
        (
            Connection connection = DriverManager.getConnection("jdbc:sqlite:lib/imdb-data (1).db");
            PreparedStatement statement = connection.prepareStatement(
                "SELECT DISTINCT ratings.tconst " +
                "FROM ratings " +
                "JOIN basics ON ratings.tconst = basics.tconst " +
                "JOIN principals ON ratings.tconst = principals.tconst " +
                "WHERE ratings.numVotes > ? " +
                "AND ratings.averageRating > ? " +
                "AND basics.genres != '\\N' " +
                "AND basics.titleType != 'tvEpisode' " +
                "AND principals.category IN ('director', 'writer') " +
                "AND principals.nconst != '\\N'"
            );
        )
        {
            statement.setQueryTimeout(30);  
            statement.setInt(1, numVotes);
            statement.setDouble(2, averageRating);
    
            ResultSet rs = statement.executeQuery();
    
            while(rs.next()){
                tconsts.add(rs.getString("tconst"));
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace(System.err);
        }     
    }

    @Override
    public ArrayList<String> getThreeRandomNames(){   
        ArrayList<String> allTitles = new ArrayList<>();

        // Split each string by commas and trim whitespace, then add to allTitles
        for (String titleGroup : possibleFalseCreatorsTitles) {
            String[] titles = titleGroup.split(",");
            for (String title : titles) {
                allTitles.add(title.trim());
            }
        }

        // Shuffle the list to randomize order

        ArrayList<String> threeTConsts = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            threeTConsts.add(allTitles.get(i));
        }

        ArrayList<String> threeTitles = new ArrayList<>();
        try
        (
        Connection connection = DriverManager.getConnection("jdbc:sqlite:lib/imdb-data (1).db");
        Statement statement = connection.createStatement();
        )
        {        
            ResultSet rs = statement.executeQuery("select * from name");
            int found = 0; 
            while(found < 3 && rs.next()){
                if(rs.getString("knownForTitles").contains(threeTConsts.get(0)) || rs.getString("knownForTitles").contains(threeTConsts.get(1)) || rs.getString("knownForTitles").contains(threeTConsts.get(2))){
                    threeTitles.add(rs.getString("primaryName"));
                }
            }
            Collections.shuffle(threeTitles, new Random());
        }
        catch(SQLException e)
        {
        // if the error message is "out of memory",
        // it probably means no database file is found
        e.printStackTrace(System.err);
        }    
        return threeTitles; 
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

    @Override
    public String toString() {
        return "Title: " + title + ", Release Date: " + releaseDate + ", Genres: " + genres + ", Genres Array: " + genresArr + ", Creators: " + creators;
    }
}
