package BLL;

import BE.Users;
import CostumException.ApplicationWideException;
import CostumException.ValidationException;
import DAL.USERS_DAO;
import GUI.Utility.PasswordUtils;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EventsManager {


    private USERS_DAO usersDao;

    public EventsManager() throws SQLServerException, IOException {
        usersDao = new USERS_DAO();
    }


    public void createUser(String userFName, String userLName, String userEmail, String password, Users.Role role, String userPicture) {
        String hashedPassword = PasswordUtils.hashPassword(password);

        Users newUser = new Users(userFName, userLName, userEmail, hashedPassword, role, userPicture);
        usersDao.addUser(newUser);
    }



    public boolean verifyLoginWithRole(String email, String password, Users.Role expectedRole) throws ValidationException {
        if (!isValidEmail(email)) {
            throw new ValidationException("Invalid email format.");
        }

        Users user = usersDao.getUserByEmail(email);
        if (user != null && PasswordUtils.checkPassword(password, user.getHashedPassword())) {
            // Check if the user's role matches the expected role
            return user.getRole().equals(expectedRole);
        }
        return false;
    }

    private boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

/*
    public void updateFirstName(Users users) {
        try {
            usersDao.updateUsers(users);
        } catch (ApplicationWideException e) {
            throw new RuntimeException(e);
        }
    } */

    public List<Users> getAllUsers() throws ApplicationWideException{
        try{
            return usersDao.getAllUsers();
        } catch (ApplicationWideException e) {
            throw new RuntimeException(e);
        }
    }



}
