import java.sql.*;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class AccountDb {
    private static final String DB_URL = "jdbc:sqlite:./users.db";

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome! Type 'register', 'login', 'update points', or 'add friend':");
        String command = sc.nextLine();

        if (command.equalsIgnoreCase("register")) {
            System.out.print("Enter name: ");
            String name = sc.nextLine();
            System.out.print("Enter password: ");
            String password = sc.nextLine();
            System.out.print("Path to profile picture (optional): ");
            String path = sc.nextLine();
            byte[] profilePic = path.isEmpty() ? null : loadImageAsBytes(path);
            addUser(name, password, profilePic);
        } else if (command.equalsIgnoreCase("login")) {
            System.out.print("Enter name: ");
            String name = sc.nextLine();
            System.out.print("Enter password: ");
            String password = sc.nextLine();

            if (authenticateUser(name, password)) {
                int userId = getUserIdByName(name);
                System.out.println("Login successful!");

                displayUserInfo(userId);

                while (true) {
                    System.out.println("Type 'update points', 'add friend', 'remove friend', or 'exit':");
                    String action = sc.nextLine();

                    if (action.equalsIgnoreCase("update points")) {
                        System.out.print("Enter category: ");
                        String category = sc.nextLine();
                        System.out.print("Enter new points: ");
                        int newPoints = sc.nextInt();
                        sc.nextLine();

                        updateScore(userId, category, newPoints);
                        updateLeaderboard(category);
                        System.out.println("Points updated and leaderboard refreshed!");
                    }
                    else if (action.equalsIgnoreCase("add friend")) {
                        System.out.print("Enter friend's user ID: ");
                        int friendId = sc.nextInt();
                        sc.nextLine();

                        addFriend(userId, friendId);
                    } 

                    else if (action.equalsIgnoreCase("remove friend")) {
                        System.out.print("Enter friend's user ID to remove: ");
                        int friendId = sc.nextInt();
                        sc.nextLine();
                    
                        removeFriend(userId, friendId);
                    }

                    else if (action.equalsIgnoreCase("exit")) {
                        System.out.println("Exiting.");
                        break;
                    } else {
                        System.out.println("Unknown command.");
                    }
                }
            } else {
                System.out.println("Login failed.");
            }
        }
        sc.close();
    }

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }

    public static void addUser(String name, String password, byte[] profilePic) throws SQLException, NoSuchAlgorithmException {
        if (nameExists(name)) {
            System.out.println("Username already taken. Please choose another.");
            return;
        }
    
        String hashedPassword = hashPassword(password);
        String sql = "INSERT INTO UserAccounts(NAME, PASSWORD, ProfilePic) VALUES (?, ?, ?);";
        try (Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, hashedPassword);
            statement.setBytes(3, profilePic);
            statement.executeUpdate();
            System.out.println("User registered");
        }
    }
    
    private static boolean nameExists(String name) throws SQLException {
        String sql = "SELECT 1 FROM UserAccounts WHERE NAME = ?;";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    public static boolean authenticateUser(String name, String password) throws SQLException, NoSuchAlgorithmException {
        String hashedPassword = hashPassword(password);
        String sql = "SELECT * FROM UserAccounts WHERE NAME = ? AND PASSWORD = ?;";
        try (Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, hashedPassword);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        }
    }

    public static int getUserIdByName(String name) throws SQLException {
        String sql = "SELECT ID FROM UserAccounts WHERE NAME = ?;";
        try (Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
    
            if (!rs.next()) 
            {
                return -1; // user doesnt exist
            }
            int id = rs.getInt("ID");
    
            if (rs.next()) {
                throw new SQLException("Multiple users with the same name exist. Database integrity error.");
            }
    
            return id;
        }
    }

    public static void updateScore(int userId, String category, int newPoints) throws SQLException {
        String checkSql = "SELECT points FROM scores WHERE user_id = ? AND category = ?;";
        String updateSql = "UPDATE scores SET points = ? WHERE user_id = ? AND category = ?;";
        String insertSql = "INSERT INTO scores(user_id, category, points) VALUES (?, ?, ?);";

        try (Connection conn = connect();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setInt(1, userId);
            checkStmt.setString(2, category);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                    updateStmt.setInt(1, newPoints);
                    updateStmt.setInt(2, userId);
                    updateStmt.setString(3, category);
                    updateStmt.executeUpdate();
                }
            } else {
                try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                    insertStmt.setInt(1, userId);
                    insertStmt.setString(2, category);
                    insertStmt.setInt(3, newPoints);
                    insertStmt.executeUpdate();
                }
            }
        }
    }

    public static void addFriend(int userId, int friendId) throws SQLException {
        if (userId == friendId) {
            System.out.println("Cannot add yourself as a friend.");
            return;
        }

        if(!userExists(friendId)){
            System.out.println("That user does not exist.");
            return;
        }

        if (areAlreadyFriends(userId, friendId)) {
            System.out.println("You are already friends.");
            return;
        }

        String sql = "INSERT INTO friends(user_id, friend_id) VALUES (?, ?);";
        try (Connection conn = connect();
             PreparedStatement stmt1 = conn.prepareStatement(sql);
             PreparedStatement stmt2 = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false);

            stmt1.setInt(1, userId);
            stmt1.setInt(2, friendId);
            stmt1.executeUpdate();

            stmt2.setInt(1, friendId);
            stmt2.setInt(2, userId);
            stmt2.executeUpdate();

            conn.commit();
            System.out.println("Friend added successfully.");
        }
    }

    public static void removeFriend(int userId, int friendId) throws SQLException {
        if (userId == friendId) 
        {
            System.out.println("You cannot unfriend yourself.");
            return;
        }
    
        String sql = "DELETE FROM friends WHERE (user_id = ? AND friend_id = ?) OR (user_id = ? AND friend_id = ?);";
    
        try (Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setInt(2, friendId);
            statement.setInt(3, friendId);
            statement.setInt(4, userId);
            int affected = statement.executeUpdate();
    
            if (affected > 0) {
                System.out.println("Friend removed.");
            } else {
                System.out.println("You are not friends.");
            }
        }
    }

    private static boolean userExists(int userId) throws SQLException {
        String sql = "SELECT 1 FROM UserAccounts WHERE ID = ?;";
        try (Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        }
    }


    private static boolean areAlreadyFriends(int userId, int friendId) throws SQLException {
        String checkSql = "SELECT 1 FROM friends WHERE user_id = ? AND friend_id = ?;";
        try (Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement(checkSql)) {
            statement.setInt(1, userId);
            statement.setInt(2, friendId);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        }
    }



    public static void updateLeaderboard(String category) throws SQLException {
        String selectSql = "SELECT user_id, points FROM scores WHERE category = ? ORDER BY points DESC;";
        String deleteSql = "DELETE FROM leaderboard WHERE category = ?;";
        String insertSql = "INSERT INTO leaderboard(user_id, category, rank) VALUES (?, ?, ?);";

        try (Connection conn = connect();
             PreparedStatement selectStmt = conn.prepareStatement(selectSql);
             PreparedStatement deleteStmt = conn.prepareStatement(deleteSql);
             PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {

            deleteStmt.setString(1, category);
            deleteStmt.executeUpdate();

            selectStmt.setString(1, category);
            ResultSet rs = selectStmt.executeQuery();

            int rank = 1;
            while (rs.next()) {
                int userId = rs.getInt("user_id");
                insertStmt.setInt(1, userId);
                insertStmt.setString(2, category);
                insertStmt.setInt(3, rank++);
                insertStmt.executeUpdate();
            }
        }
    }

    public static byte[] loadImageAsBytes(String filePath) throws IOException {
        return java.nio.file.Files.readAllBytes(new File(filePath).toPath());
    }

    public static void displayUserInfo(int userId) throws SQLException {
        try (Connection conn = connect()) {
            String userSql = "SELECT NAME FROM UserAccounts WHERE ID = ?;";
            try (PreparedStatement statement = conn.prepareStatement(userSql)) {
                statement.setInt(1, userId);
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    System.out.println("User: " + rs.getString("NAME"));
                }
            }

            String scoreSql = "SELECT category, points FROM scores WHERE user_id = ?;";
            try (PreparedStatement statement = conn.prepareStatement(scoreSql)) {
                statement.setInt(1, userId);
                ResultSet rs = statement.executeQuery();
                System.out.println("Scores:");
                while (rs.next()) {
                    System.out.println(" - " + rs.getString("category") + ": " + rs.getInt("points"));
                }
            }

            String friendSql = "SELECT UA.ID, UA.NAME FROM friends F " +
                   "JOIN UserAccounts UA ON F.friend_id = UA.ID " +
                   "WHERE F.user_id = ?;";
            try (PreparedStatement statement = conn.prepareStatement(friendSql)) {
                statement.setInt(1, userId);
                ResultSet rs = statement.executeQuery();
                System.out.println("Friends:");
                while (rs.next()) {
                    System.out.println(" - " + rs.getString("NAME") + " (ID: " + rs.getInt("ID") + ")");
                }
            }
        }
    }
}