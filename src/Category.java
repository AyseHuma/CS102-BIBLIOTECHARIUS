import java.util.ArrayList;

public abstract class Category {
    // this class is either a movie or book that stores all info about that! Such as title release date etc. 
    public String genres; 
    public String title; 
    public int releaseDate;
    public ArrayList<String> genresArr = new ArrayList<>(); 
    public ArrayList<String> creators = new ArrayList<>();

    public ArrayList<String> possibleFalseGenres = new ArrayList<>();
    public ArrayList<String> possibleFalseCreatorsTitles = new ArrayList<>();
    public ArrayList<String> possibleFalseCreators = new ArrayList<>();

    public abstract String getOneRandomName();

    public String getTitle() {
        return title;
    }

    public int getReleaseDate() {
        return releaseDate;
    }

    public ArrayList<String> getCreators() {
        return creators;
    }

    public ArrayList<String> getGenresArr() {
        return genresArr;
    }

    public String getGenres() {
        return genres;
    }

    public ArrayList<String> getPossibleFalseGenres() {
        return possibleFalseGenres;
    }
}
