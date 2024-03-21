package GUI.Model;

import BE.Users;
import BLL.UsersManager;
import CostumException.ValidationException;
import GUI.Utility.PasswordUtils;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.IOException;

public class UsersModel {
    private UsersManager usersManager;

    public UsersModel() throws SQLServerException, IOException {
        usersManager = new UsersManager();
    }

    public boolean verifyLoginWithRole(String email, String password, Users.Role expectedRole) throws ValidationException {
        return usersManager.verifyLoginWithRole(email, password,expectedRole);
    }

    public void createUser(String userFName, String userLName, String userEmail, String password, Users.Role role, String userPicture) {
        usersManager.createUser(userFName, userLName, userEmail, password, role, userPicture);
    }

}
