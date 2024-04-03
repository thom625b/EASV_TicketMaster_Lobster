package BLL;

import BE.Users;
import CostumException.ApplicationWideException;
import CostumException.ValidationException;
import DAL.USERS_DAO;
import GUI.Utility.PasswordUtils;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class UsersManager {

    private List<Users> usersList = new ArrayList<>();
    private USERS_DAO usersDao;

    public UsersManager() throws SQLServerException, IOException {
        usersDao = new USERS_DAO();
    }


    public void createUser(String userFName, String userLName, String userEmail, String password, Users.Role role, String userPicture) throws ValidationException {
        Users existingUser = usersDao.getUserByEmail(userEmail);
        if (existingUser != null) {
            throw new ValidationException("Email already in use.");
        }


        String hashedPassword = PasswordUtils.hashPassword(password);
        Users newUser = new Users(userFName, userLName, userEmail, hashedPassword, role, userPicture);
        usersDao.addUser(newUser);
    }


    public List<Users> getCoordinators() throws ApplicationWideException {
        List<Users> allUsers = usersDao.getAllUsers();
        List<Users> coordinators = allUsers.stream()
                .filter(user -> user.getRole() == Users.Role.COORDINATOR)
                .collect(Collectors.toList());
        return coordinators;
    }
    public Users verifyLoginWithRole(String email, String password, Users.Role expectedRole) throws ValidationException {
        if (!isValidEmail(email)) {
            throw new ValidationException("Invalid email format.");
        }

        Users user = usersDao.getUserByEmail(email);
        if (user != null && PasswordUtils.checkPassword(password, user.getHashedPassword())) {

            if(user.getRole().equals(expectedRole)) {
                return user;
            }
        }
        return null;
    }


    private boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    public void updateFirstName(Users user) {
        try {
            usersDao.updateUsers(user);
        } catch (ApplicationWideException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Users> getAllUsers() throws ApplicationWideException{
        try{
            return usersDao.getAllUsers();
        } catch (ApplicationWideException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteUser(Users user) throws ApplicationWideException {
        usersDao.deleteUser(user.getUserId());
    }

    public void updateLastName(Users user) {
        try {
            usersDao.updateUsers(user);
        } catch (ApplicationWideException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateEmail(Users user) {
        try {
            usersDao.updateUsers(user);
        } catch (ApplicationWideException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateRole(Users user) {
        try {
            usersDao.updateUsers(user);
        } catch (ApplicationWideException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateUserImage(int userId, String imagePath) throws ApplicationWideException {
        try {
            usersDao.updateUserImage(userId, imagePath);
        } catch (ApplicationWideException e) {
            throw e;
        }
    }

    public String getUserImageName(int userId) throws ApplicationWideException {
        try {
            return usersDao.getUserImageName(userId);
        } catch (ApplicationWideException e) {
            // Here you can handle or re-throw the exception based on your error handling strategy
            throw new ApplicationWideException("Error fetching image name for user with ID " + userId, e);
        }
    }
}
