package GUI.Model;

import BE.Users;
import BLL.UsersManager;
import CostumException.ApplicationWideException;
import CostumException.ValidationException;
import DAL.IUserDataAccess;
import GUI.Utility.PasswordUtils;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.List;

public class UsersModel {
    private UsersManager usersManager;
    private ObservableList<Users> usersObservableList;

    public UsersModel() throws SQLServerException, IOException, ApplicationWideException {
        usersManager = new UsersManager();
        usersObservableList = FXCollections.observableArrayList();
        usersObservableList.addAll(usersManager.getAllUsers());
    }

    public boolean verifyLoginWithRole(String email, String password, Users.Role expectedRole) throws ValidationException {
        return usersManager.verifyLoginWithRole(email, password,expectedRole);
    }

    public void createUser(String userFName, String userLName, String userEmail, String password, Users.Role role, String userPicture) {
        usersManager.createUser(userFName, userLName, userEmail, password, role, userPicture);
    }

    public void updateFirstName(Users users) throws ApplicationWideException{
        try{
            usersManager.updateFirstName(users);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<Users> getObservableUsers() {
        return usersObservableList;
    }


    public void deleteUser(Users user) throws ApplicationWideException {
        usersManager.deleteUser(user);
        usersObservableList.remove(user);

    }
}
