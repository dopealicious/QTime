package Controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class DatabaseHandler {
    public Connection conn;
    private String url = "jdbc:mysql://localhost:3306/qtime";
    private String username = "root";
    private String password = "";
    private String driver = "com.mysql.cj.jdbc.Driver";

    public Connection connect(){
        try{
            Class.forName(driver).getDeclaredConstructor().newInstance();
            conn = DriverManager.getConnection(url, username, password);
        }catch(Exception e){
            System.out.println("Error while connecting to database");
        }
        return conn;
    }

    public void Register(String name, String password, String confirmPassword){
        try (Connection connection = connect()){
            if (password.equals(confirmPassword)) {
                if (!isUsernameExists(connection, name)) {
                    PreparedStatement statement = connection.prepareStatement("INSERT INTO user (username, password) VALUES (?, ?)");
                    statement.setString(1, name);
                    statement.setString(2, password);
                    int rowAffected = statement.executeUpdate();

                    if (rowAffected>0) {
                        JOptionPane.showMessageDialog(null, "Akun sukses dibuat!");                    
                    }else{
                        JOptionPane.showMessageDialog(null, "Error dalam membuat akun! Harap coba lagi.","Error",JOptionPane.ERROR_MESSAGE);                    
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Username telah terdaftar!","Warning",JOptionPane.WARNING_MESSAGE);                
                }                
            }else{
                JOptionPane.showMessageDialog(null, "Password dan Confirm Password tidak cocok!","Warning",JOptionPane.WARNING_MESSAGE);                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public int Login(String username, String password) {
        int userId = 0;
        try (Connection connection = connect()) {
        // Check if the username exists
        if (isUsernameExists(connection, username)) {
            // Verify the password
            PreparedStatement st = connection.prepareStatement("SELECT * FROM user WHERE username=? AND password=?");
            st.setString(1, username);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Login berhasil!");
                userId = rs.getInt("id");
            } else {
                JOptionPane.showMessageDialog(null, "Password salah!", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Akun anda tidak terdaftar!", "Warning", JOptionPane.WARNING_MESSAGE);
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userId;
    }
    
    private boolean isUsernameExists(Connection connection, String username) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM user WHERE username = ?");
        statement.setString(1, username);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }    
    
    public void updateUserInfo(int userId, String newUsername, String newPassword) {
        try (Connection connection = connect()) {
            PreparedStatement st = connection.prepareStatement("UPDATE user SET username=?, password=? WHERE id=?");
            st.setString(1, newUsername);
            st.setString(2, newPassword);
            st.setInt(3, userId);
            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User info updated successfully.");
            } else {
                System.out.println("Failed to update user info.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }    

    public String getPassInfo(String username) {
        String userInfo = null;
        try (Connection connection = connect()){
            PreparedStatement st = connection.prepareStatement("SELECT password FROM user WHERE username=?");
            st.setString(1, username);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                userInfo = rs.getString("password"); // Retrieve 'username' column
            }

            connection.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return userInfo;
    }

    public int getHighScore(int userId) {
        int highScore = 0;
        try (Connection connection = connect()) {
            PreparedStatement st = connection.prepareStatement("SELECT score FROM score WHERE id_user = ? ORDER BY uploadTime DESC LIMIT 1");
            st.setInt(1, userId);
    
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                highScore = rs.getInt("score");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return highScore;
    }    

    public void insertScore(int userId, int score, int time) {
        try (Connection connection = connect()) {
            PreparedStatement st = connection.prepareStatement("INSERT INTO score (id_user, score, time, uploadTime) VALUES (?, ?, ?, CURRENT_TIMESTAMP)");
            st.setInt(1, userId);
            st.setInt(2, score);
            st.setInt(3, time);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        

    public int fetchUserId(String username) {
        int userId = 0;
        try (Connection connection = connect()){
            PreparedStatement st = connection.prepareStatement("SELECT id FROM user WHERE username = ?");
            st.setString(1, username);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    userId = rs.getInt("id");
                    System.out.println("User ID found: " + userId);
                } else {
                    System.out.println("User ID not found for username: " + username);
                }
            }            

            connection.close(); // Pastikan untuk menutup koneksi setelah penggunaan
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return userId;
    }

    public int getTotalTests(int userId) {
        int totalTests = 0;
        try (Connection connection = connect()){
            PreparedStatement st = connection.prepareStatement("SELECT COUNT(*) as total FROM score WHERE id_user = ?");
            st.setInt(1, userId);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                totalTests = rs.getInt("total");
            }

            connection.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return totalTests;
    }    
    
    public int getTotalTime(int userId) {
        int totalTime = 0;
        try (Connection connection = connect()) {
            PreparedStatement st = connection.prepareStatement("SELECT time FROM score WHERE id_user= ? ORDER BY id DESC LIMIT 1");
            st.setInt(1, userId);
    
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                totalTime = rs.getInt("time");
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return totalTime;
    }    

    public List<String[]> getTotalScore() {
        List<String[]> highScores = new ArrayList<>();
        try (Connection connection = connect()){
            PreparedStatement st = connection.prepareStatement(
                "SELECT user.username AS nama, MAX(score.score) AS high_score, MIN(score.time) AS earliest_time "
                + "FROM score "
                + "JOIN user ON score.id_user = user.id "
                + "GROUP BY user.username "
                + "ORDER BY high_score DESC, earliest_time ASC LIMIT 5");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String nama = rs.getString("nama");
                int score = rs.getInt("high_score");
                int time = rs.getInt("earliest_time");
                String[] entry = {nama, Integer.toString(score), Integer.toString(time)};
                highScores.add(entry);
            }

            connection.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return highScores;
    }
}
