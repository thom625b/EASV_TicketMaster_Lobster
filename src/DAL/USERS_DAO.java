package DAL;

import BE.Users;
import DAL.DBConnector.DBConnector;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class USERS_DAO {

    private DBConnector dbConnector;

    public USERS_DAO() throws IOException {
        dbConnector = new DBConnector();
    }

    public void addUser(Users users) {
        String sql = "INSERT INTO Users (userFName, userLName, userEmail, hashedPassword, userRole, userPicture) VALUES (?, ?, ?, ?, ?, ?);";

        try (Connection conn = dbConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, users.getFirstName());
            pstmt.setString(2, users.getLastName());
            pstmt.setString(3, users.getEmail());
            pstmt.setString(4, users.getHashedPassword());
            pstmt.setString(5, users.getRole().toString());
            pstmt.setString(6, users.getUserPicture());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Proper exception handling goes here
        }
    }


    public Users getUserByEmail(String email) {
        String sql = "SELECT * FROM Users WHERE userEmail = ?;";

        try (Connection conn = dbConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int userId = rs.getInt("userID");
                    String firstName = rs.getString("userFName");
                    String lastName = rs.getString("userLName");
                    String userEmail = rs.getString("userEmail");
                    String hashedPassword = rs.getString("hashedPassword"); // Ensure this column name matches your DB schema
                    Users.Role role = Users.Role.valueOf(rs.getString("userRole"));
                    String userPicture = rs.getString("userPicture");

                    return new Users(userId, firstName, lastName, userEmail, hashedPassword, role, userPicture);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Proper exception handling strategy should be implemented
        }
        return null;
    }
}
