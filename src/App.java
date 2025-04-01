import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class App
{
    public static void main(String[] args)
    {
        ArrayList<String> tconsts = new ArrayList<>();
        //       Don't forget to close them both in order to avoid leaks.
        try
        (
        // create a database connection
        Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
        Statement statement = connection.createStatement();
        )
        {
        
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            ResultSet rs = statement.executeQuery("select * from ratings where averageRating>9.5");
            
            while(rs.next()){
                if (rs.getInt("numVotes") > 1000) {
                    tconsts.add(rs.getString("tconst"));
                }
            }

            rs = statement.executeQuery("select * from basics");

            int i = 0;
            while(rs.next()){
                if(rs.getString("tconst").equals(tconsts.get(i)) && !rs.getString("titleType").equals("tvEpisode")){
                    i ++;
                    System.out.println(rs.getString("primaryTitle"));
                }
            }        
        }
        catch(SQLException e)
        {
        // if the error message is "out of memory",
        // it probably means no database file is found
        e.printStackTrace(System.err);
        }
    }
}