package GUI.Controllers.Frame.Admin;

import BE.Users;
import BLL.UsersManager;
import CostumException.ApplicationWideException;
import GUI.Controllers.IController;
import GUI.Model.UsersModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.HashMap;
import java.util.Map;

public class AdminUpdateCoordinatorController implements IController {
    @FXML
    private Button updateAdminCoordinatorbtn, btnDeleteUser;
    @FXML
    private PasswordField txtUpdatePassword;
    @FXML
    private TextField txtUpdateEmail,  txtUpdateLastName,  txtUpdateFirstName;
    @FXML
    private ComboBox<String> UpdatecomboRole;

    private UsersModel usersModel;

    private Users selectedUsers;
    private Map<ComboBox, Users> usersMap = new HashMap<>();


    private Users user;

    public void setUser(Users user) {
        this.user = user;
        updateFields(user);
    }

    @FXML
    private void updateCoordinator(ActionEvent actionEvent) {
        String newPassword = txtUpdateFirstName.getText();
        if (usersModel == null || selectedUsers == null){
            return;
        }
        if (newPassword.isEmpty()){
            return;
        }
        try {
            selectedUsers.getFirstName();
            usersModel.updateFirstName(selectedUsers);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private void updateFields(Users user) {
        if(user != null) {
            txtUpdateFirstName.setText(user.getFirstName());
            txtUpdateLastName.setText(user.getLastName());
            txtUpdateEmail.setText(user.getEmail());



            ObservableList<String> roles = FXCollections.observableArrayList(
                    Users.Role.ADMIN.toString(),
                    Users.Role.COORDINATOR.toString()
            );
            UpdatecomboRole.setItems(roles);
            UpdatecomboRole.getSelectionModel().select(user.getRole().toString());
        }
    }

    @FXML
    private void initialize() throws Exception{
        usersModel = new UsersModel();
    }

    @Override
    public void setModel(UsersModel usersModel) {
        this.usersModel = usersModel;
    }


    @FXML
    void deleteUser(ActionEvent actionEvent) throws ApplicationWideException {
        if (user == null) {
            showAlert("No user selected", "Please select a user to delete.", Alert.AlertType.ERROR);
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this user: " + user.getFirstName() + "?", ButtonType.YES, ButtonType.NO);
        confirmAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    usersModel.deleteUser(user);
                    showAlert("User Deleted", "The user has been deleted successfully.", Alert.AlertType.INFORMATION);
                } catch (Exception e) {
                    e.printStackTrace();
                    showAlert("Error", "Could not delete the user.", Alert.AlertType.ERROR);
                }
            }
        });

        AdminFrameController.getInstance().goBack();

    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

}




