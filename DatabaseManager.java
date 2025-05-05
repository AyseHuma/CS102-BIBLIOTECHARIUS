import java.sql.*;
import java.util.Random;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:game.db";

    // Veritabanı tablolarını oluştur
    public static void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            String usersTable = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "username TEXT UNIQUE," +
                    "password TEXT);";

            String friendsTable = "CREATE TABLE IF NOT EXISTS friends (" +
                    "user_id INTEGER," +
                    "friend_id INTEGER," +
                    "FOREIGN KEY(user_id) REFERENCES users(id)," +
                    "FOREIGN KEY(friend_id) REFERENCES users(id));";

            String userPointsTable = "CREATE TABLE IF NOT EXISTS user_points (" +
                    "user_id INTEGER," +
                    "category_index INTEGER," +
                    "point INTEGER," +
                    "FOREIGN KEY(user_id) REFERENCES users(id));";

            String questionsTable = "CREATE TABLE IF NOT EXISTS questions (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "question_text TEXT," +
                    "correct_answer TEXT," +
                    "incorrect_answer_1 TEXT," +
                    "incorrect_answer_2 TEXT," +
                    "incorrect_answer_3 TEXT);";

            stmt.execute(usersTable);
            stmt.execute(friendsTable);
            stmt.execute(userPointsTable);
            stmt.execute(questionsTable);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Yeni kullanıcı kaydı
    public static boolean registerUser(String username, String password) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO users(username, password) VALUES(?, ?)")) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Creating account failed: " + e.getMessage());
            return false;
        }
    }

    // Kullanıcı girişi
    public static int login(String username, String password) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement("SELECT id FROM users WHERE username=? AND password=?")) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            return rs.next() ? rs.getInt("id") : -1;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    // Arkadaş ekleme
    public static boolean addFriend(int userId, String friendUsername) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            PreparedStatement findFriend = conn.prepareStatement("SELECT id FROM users WHERE username = ?");
            findFriend.setString(1, friendUsername);
            ResultSet rs = findFriend.executeQuery();

            if (rs.next()) {
                int friendId = rs.getInt("id");

                PreparedStatement insert = conn.prepareStatement("INSERT INTO friends(user_id, friend_id) VALUES(?, ?)");
                insert.setInt(1, userId);
                insert.setInt(2, friendId);
                insert.executeUpdate();
                return true;
            } else {
                System.out.println("There is no friend.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Could not add friend: " + e.getMessage());
            return false;
        }
    }

    // Arkadaşları göster
    public static void showFriends(int userId) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String query = "SELECT username FROM users WHERE id IN " +
                    "(SELECT friend_id FROM friends WHERE user_id=?)";

            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("Friends:");
            while (rs.next()) {
                System.out.println("- " + rs.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Soruları veritabanına ekle
    public static void addQuestionToDatabase(String questionText, String correctAnswer, String[] incorrectAnswers) {
        String query = "INSERT INTO questions (question_text, correct_answer, incorrect_answer_1, incorrect_answer_2, incorrect_answer_3) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL); 
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, questionText);
            pstmt.setString(2, correctAnswer);
            pstmt.setString(3, incorrectAnswers[0]);
            pstmt.setString(4, incorrectAnswers[1]);
            pstmt.setString(5, incorrectAnswers[2]);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        // Soruları veritabanına ekle
        public static void addQuestionToDatabase(String questionText, String correctAnswer, String[] incorrectAnswers) {
            String query = "INSERT INTO questions (question_text, correct_answer, incorrect_answer_1, incorrect_answer_2, incorrect_answer_3) VALUES (?, ?, ?, ?, ?)";
            try (Connection conn = DriverManager.getConnection(DB_URL); 
                 PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, questionText);
                pstmt.setString(2, correctAnswer);
                pstmt.setString(3, incorrectAnswers[0]);
                pstmt.setString(4, incorrectAnswers[1]);
                pstmt.setString(5, incorrectAnswers[2]);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    
        // Rastgele bir soru getir
        public static Question getRandomQuestion() {
            String query = "SELECT * FROM questions ORDER BY RANDOM() LIMIT 1";
            try (Connection conn = DriverManager.getConnection(DB_URL); 
                 PreparedStatement pstmt = conn.prepareStatement(query);
                 ResultSet rs = pstmt.executeQuery()) {
    
                if (rs.next()) {
                    String questionText = rs.getString("question_text");
                    String correctAnswer = rs.getString("correct_answer");
                    String[] incorrectAnswers = {
                        rs.getString("incorrect_answer_1"),
                        rs.getString("incorrect_answer_2"),
                        rs.getString("incorrect_answer_3")
                    };
                    return new Question(questionText, correctAnswer, incorrectAnswers);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    

