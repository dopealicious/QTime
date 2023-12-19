package Controller;

import java.sql.*;
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
}
