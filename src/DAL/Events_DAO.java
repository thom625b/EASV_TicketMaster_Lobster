package DAL;

import BE.Users;
import CostumException.ApplicationWideException;
import DAL.DBConnector.DBConnector;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Events_DAO  {

    private DBConnector dbConnector;

    public Events_DAO() throws IOException {
        dbConnector = new DBConnector();
    }
/*
    @Override
    public List<Users> getAllUsers() throws ApplicationWideException {
        ArrayList<Users> allUsers = new ArrayList<>();
        try(Connection conn = dbConnector.getConnection();
            Statement stmt = conn.createStatement()) {
            String sql =
                    """
                    Select * FROM Users
                    """;
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                int userId = rs.getInt("userID");
                String firstName = rs.getString("userFName");
                String lastName = rs.getString("userLName");
                String email = rs.getString("userEmail");
                String hashedPassword = rs.getString("hashedPassword");
                String role = rs.getString("userRole");
                Users usersAll = new Users(userId, firstName, lastName, email, Users.Role.valueOf(role), hashedPassword);
                allUsers.add(usersAll);
            }
        } catch (SQLServerException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allUsers;
    }


    public Users addUser(Users users) {
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
        }
        return users;
    }

    @Override
    public void deleteUser(Users users) throws ApplicationWideException {

    }

    @Override
    public void updateUsers(Users users) throws ApplicationWideException {
        String sql = "UPDATE Users SET userFName = ? WHERE userID = ?;";
        try(Connection conn = dbConnector.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, users.getFirstName());
            pstmt.executeUpdate();
        } catch (SQLServerException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
                    String hashedPassword = rs.getString("hashedPassword");
                    Users.Role role = Users.Role.valueOf(rs.getString("userRole"));
                    String userPicture = rs.getString("userPicture");

                    return new Users(userId, firstName, lastName, userEmail, hashedPassword, role, userPicture);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }*/
}