package GUI.Controllers.Frame.Admin;

import BE.Users;
import BLL.UsersManager;
import GUI.Controllers.IController;
import GUI.Model.UsersModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.HashMap;
import java.util.Map;

public class AdminUpdateCoordinatorController implements IController {
    @FXML
    private Button updateAdminCoordinatorbtn;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private TextField txtEmail, txtLastName, txtFirstName;
    @FXML
    private ComboBox comboRole;

    private UsersModel usersModel;
    private UsersManager usersManager;
    private Users selectedUsers;
    private Map<ComboBox, Users> usersMap = new HashMap<>();

    @FXML
    private void updateCoordinator(ActionEvent actionEvent) {
        String newPassword = txtFirstName.getText();
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

    @FXML
    private void initialize() throws Exception{
        usersManager = new UsersManager();
    }

    @Override
    public void setModel(UsersModel usersModel) {
        this.usersModel = usersModel;
    }
}




