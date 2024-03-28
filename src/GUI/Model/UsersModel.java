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
import javafx.scene.control.Alert;

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

    public Users verifyLoginWithRole(String email, String password, Users.Role expectedRole) throws ValidationException, ApplicationWideException {
        return usersManager.verifyLoginWithRole(email, password, expectedRole);
    }


    public void createUser(String userFName, String userLName, String userEmail, String password, Users.Role role, String userPicture) throws ValidationException {
        usersManager.createUser(userFName, userLName, userEmail, password, role, userPicture);
    }

    public void updateFirstName(Users user) throws ApplicationWideException{
        try{
            usersManager.updateFirstName(user);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateLastName(Users user) {
        try{
            usersManager.updateLastName(user);
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

    public void updateEmail (Users user){
            try {
                usersManager.updateEmail(user);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
    }

    public void updateRole (Users user){
            try {
                usersManager.updateRole(user);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
    }


    public void updateUserImage(int userId, String imagePath) {
        try {
            usersManager.updateUserImage(userId, imagePath);
        } catch (ApplicationWideException e) {
            showError("Could not update the user's image: " + e.getMessage());
        }
    }

    public void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("error");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
    public ObservableList<Users> getCoordinatorsObservable() throws ApplicationWideException {
        List<Users> coordinators = usersManager.getCoordinators();
        return FXCollections.observableArrayList(coordinators);
    }
}
