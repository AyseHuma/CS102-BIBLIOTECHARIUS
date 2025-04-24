// This class is only for trying code
public class PlayerScore {
    String name;
    int score;

    PlayerScore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    @Override
    public String toString() {
        return name + " - " + score + " pts";
    }
}
