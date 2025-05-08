package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class Book extends Category{

    private static ArrayList<String> BookIds = new ArrayList<>();    

    @Override
    public ArrayList<String> getThreeRandomNames() {
        ArrayList<String> allTitles = new ArrayList<>(); 
        ArrayList<String> cloneIt = (ArrayList<String>)(possibleFalseCreators.clone()); 
        System.out.println(possibleFalseCreators);
        int r; 
        for(int i = 0; i<3; i++){
            r = (int)(Math.random() * cloneIt.size());
            allTitles.add(cloneIt.get(r));
            cloneIt.remove(r);
        }
        return allTitles;
    }

    public static void fillBookIDFromGoodbooks(double minAverageRating, int minNumRatings){
        BookIds = new ArrayList<>(); 
        
        try (
            Connection connection = DriverManager.getConnection("jdbc:sqlite:lib/goodbooks.db");
            PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT \"Book Id\" FROM Goodreads_books_with_genres WHERE average_rating >= ? AND ratings_count >= ?"
                // it is in quotes because of the space
            )
        ) {
            preparedStatement.setQueryTimeout(30);
            preparedStatement.setDouble(1, minAverageRating);
            preparedStatement.setInt(2, minNumRatings);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                BookIds.add(rs.getString("Book Id"));
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    public void getRandomBookFromBookTable() {
        if (BookIds.isEmpty()) {   //TODO implement this to movies as well
            System.err.println("No BookIds available. Call fillBookIDsFromNewTable first.");
            return;
        }

        int randomIndex = (int) (Math.random() * BookIds.size());
        String randomBookId = BookIds.get(randomIndex);

        try (
            Connection connection = DriverManager.getConnection("jdbc:sqlite:lib/goodbooks.db");
            PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT Title, Author, publication_date, genres FROM Goodreads_books_with_genres WHERE \"Book Id\" = ?"
            )
        ) {
            preparedStatement.setQueryTimeout(30);
            preparedStatement.setString(1, randomBookId);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                title = rs.getString("Title");

                String authorBeforeSplit = rs.getString("Author");
                creators = new ArrayList<>();               
                for (String string : authorBeforeSplit.split("/")) {
                    creators.add(string);
                }

                String publicationDate = rs.getString("publication_date");
                releaseDate = extractYearFromDate(publicationDate);

                genres = rs.getString("genres");
                System.out.println(genres);
                genresArr = new ArrayList<>();
                for (String string : genres.split(";")) {
                    genresArr.add(string);
                }
            }

            
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        String randomGenre = genresArr.get((int)(genresArr.size() * Math.random()));

        try
        (
        // create a database connection
        Connection connection2 = DriverManager.getConnection("jdbc:sqlite:lib/goodbooks.db");
        Statement statement2 = connection2.createStatement();
        )
        {        
            statement2.setQueryTimeout(30);  // set timeout to 30 sec.
            ResultSet rs2 = statement2.executeQuery("SELECT Author FROM Goodreads_books_with_genres WHERE genres LIKE '%" + randomGenre + "%'");
            System.out.println(randomGenre);
            while(rs2.next()){
                possibleFalseCreators.add(rs2.getString("Author"));
            }
        }
        catch(SQLException e)
        {
        e.printStackTrace(System.err);
        }             

    }

    private int extractYearFromDate(String date) {  // gests year from M/D/Y string and returns int --- very prone to errors
        String[] parts = date.split("/");
        return Integer.parseInt(parts[2]);
    }

    public Book(){
        possibleFalseGenres.add("Fiction");
        possibleFalseGenres.add("Classics");
        possibleFalseGenres.add("Mystery");
        possibleFalseGenres.add("Crime");
        possibleFalseGenres.add("Historical");
        possibleFalseGenres.add("Humor");
        possibleFalseGenres.add("Poetry");
        possibleFalseGenres.add("Religion");
        possibleFalseGenres.add("Biography");
        possibleFalseGenres.add("Economics");
        possibleFalseGenres.add("Nonfiction");
        possibleFalseGenres.add("Food and Drink");
        possibleFalseGenres.add("Science Fiction");
        possibleFalseGenres.add("Paranormal");
        possibleFalseGenres.add("Fantasy");

        getRandomBookFromBookTable();

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

}