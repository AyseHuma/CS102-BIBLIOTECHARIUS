package com.example;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AccountDb {

    
    private static final String DB_URL = "jdbc:sqlite:lib/users.db";

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

    public static boolean addUser(String name, String password, byte[] profilePic) throws SQLException, NoSuchAlgorithmException {
        if (nameExists(name)) {
            System.out.println("Username already taken. Please choose another.");
            return false;
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
        return true;
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

       public static String getLeaderboard(String category) throws SQLException {

        String result = new String();

        String leaderboardSQL = """
            SELECT ua.NAME, lb.rank, sc.points
            FROM leaderboard lb
            JOIN UserAccounts ua ON lb.user_id = ua.ID
            JOIN scores sc ON lb.user_id = sc.user_id AND lb.category = sc.category
            WHERE lb.category = ?
            ORDER BY lb.rank ASC; """;

        try (Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement(leaderboardSQL)) {

            statement.setString(1, category);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) 
            {
                String name = rs.getString("NAME");
                int rank = rs.getInt("rank");
                int points = rs.getInt("points");
                result += rank + ". " + name + " - " + points + " points\n";
            }
        }
        return result;
    }

    public static String[] loadFriendList(int userId) throws SQLException {
        String sql = """
            SELECT ua.NAME
            FROM friends F
            JOIN UserAccounts ua ON F.friend_id = ua.ID
            WHERE F.user_id = ?;
        """;

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            java.util.List<String> friendNames = new java.util.ArrayList<>();
            while (rs.next()) {
                friendNames.add(rs.getString("NAME"));
            }

            return friendNames.toArray(new String[0]);
        }
    }


    public static int sendFriendRequest(int senderId, String receiverName) throws SQLException {
        String getReceiverIdQuery = "SELECT ID FROM UserAccounts WHERE NAME = ?";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(getReceiverIdQuery))
             {

            ps.setString(1, receiverName);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) return -1; // cant find receiver

            int receiverId = rs.getInt("ID");
            if (receiverId == senderId) return -2; // self

            if (areFriends(senderId, receiverId)) return 0; // already friends

            String check = "SELECT status FROM friends WHERE user_id = ? AND friend_id = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(check))
            {
                checkStmt.setInt(1, senderId);
                checkStmt.setInt(2, receiverId);
                ResultSet checkRs = checkStmt.executeQuery();
                if (checkRs.next())
                {
                    String status = checkRs.getString("status");
                    if (status.equals("pending")) return -3; // request already sent
                    if (status.equals("rejected"))
                    {
                        String update = "UPDATE friends SET status = 'pending' WHERE user_id = ? AND friend_id = ?";
                        try (PreparedStatement updateStmt = conn.prepareStatement(update))
                        {
                            updateStmt.setInt(1, senderId);
                            updateStmt.setInt(2, receiverId);
                            updateStmt.executeUpdate();
                            return 1;
                        }
                    }
                }
            }

            String insert = "INSERT INTO friends(user_id, friend_id, status) VALUES (?, ?, 'pending')";
            try (PreparedStatement insertStmt = conn.prepareStatement(insert))
            {
                insertStmt.setInt(1, senderId);
                insertStmt.setInt(2, receiverId);
                insertStmt.executeUpdate();
            }

            return 1;
        }
    }

    public static boolean acceptFriendRequest(int receiverId, int senderId) throws SQLException {
    String updateSql = "UPDATE friends SET status = 'accepted' WHERE user_id = ? AND friend_id = ?";
    String insertSql = "INSERT INTO friends (user_id, friend_id, status) VALUES (?, ?, 'accepted')";
    try (Connection conn = connect();
        PreparedStatement updateStmt = conn.prepareStatement(updateSql);
        PreparedStatement insertStmt = conn.prepareStatement(insertSql))
        {
            updateStmt.setInt(1, senderId);
            updateStmt.setInt(2, receiverId);
            int rowsUpdated = updateStmt.executeUpdate();
            if (rowsUpdated > 0)
            {
                insertStmt.setInt(1, receiverId);
                insertStmt.setInt(2, senderId);
                insertStmt.executeUpdate();
                return true;
            }
            return false;
        }

    }

    public static boolean rejectFriendRequest(int userId, int requesterId) throws SQLException {
        String sql = "DELETE FROM friends WHERE user_id = ? AND friend_id = ? AND status = 'pending'";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, requesterId); 
            stmt.setInt(2, userId);      
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }


    public static List<String> getPendingRequests(int userId) throws SQLException {
        String query = """
            SELECT UA.ID, UA.NAME
            FROM friends F
            JOIN UserAccounts UA ON F.user_id = UA.ID
            WHERE F.friend_id = ? AND F.status = 'pending'
            """;
        List<String> list = new ArrayList<>();
        try (Connection conn = connect();
            PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                list.add(rs.getString("NAME"));
            }
        }
        return list;
    }

    public static List<String> getFriendList(int userId) throws SQLException {
        Set<String> friendSet = new HashSet<>();

        String sql = """
            SELECT u.name
            FROM friends f
            JOIN UserAccounts u ON u.id = (
                CASE 
                    WHEN f.user_id = ? THEN f.friend_id
                    ELSE f.user_id
                END
            )
            WHERE (f.user_id = ? OR f.friend_id = ?) AND f.status = 'accepted'
        """;

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, userId);
            stmt.setInt(3, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                friendSet.add(rs.getString("name"));
            }
        }

        return new ArrayList<>(friendSet);
    }


    public static boolean areFriends(int userId1, int userId2) throws SQLException {
        String query = """
            SELECT 1 FROM friends 
            WHERE (
                (user_id = ? AND friend_id = ?) OR
                (user_id = ? AND friend_id = ?)
            ) AND status = 'accepted'
            """;
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userId1);
            ps.setInt(2, userId2);
            ps.setInt(3, userId2);
            ps.setInt(4, userId1);
            return ps.executeQuery().next();
        }
    }

    public static boolean removeFriend(int userId1, int userId2) throws SQLException {
        String sql = """
            DELETE FROM friends
            WHERE ((user_id = ? AND friend_id = ?) OR (user_id = ? AND friend_id = ?))
            AND status = 'accepted'
        """;

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId1);
            stmt.setInt(2, userId2);
            stmt.setInt(3, userId2);
            stmt.setInt(4, userId1);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

}
