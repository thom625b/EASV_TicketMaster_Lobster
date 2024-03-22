package GUI.Controllers.Frame.Admin;

import BE.Users;
import CostumException.ApplicationWideException;
import GUI.Controllers.IController;
import GUI.Model.UsersModel;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AdminHomePageController implements IController {

    @FXML
    private ComboBox<String> comboRole;
    @FXML
    private TextField txtEmail,txtFirstName,txtLastName;
    @FXML
    private PasswordField txtPassword;

    private UsersModel usersModel;

    @FXML
    private void initialize() throws SQLServerException, IOException, ApplicationWideException {




        usersModel = new UsersModel();
    }


    // Setter method for usersModel, in case it needs to be set externally


    @FXML
    void handleAddUser(ActionEvent event) {
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        String selectedRole = comboRole.getValue();
        String userPicture = null; // Replace with actual logic to get picture, if applicable

        if (selectedRole != null && !firstName.isEmpty() && !lastName.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
            Users.Role role = Users.Role.valueOf(selectedRole); // Convert string back to Role enum
            // Consider adding error handling around createUser to handle any potential issues (e.g., SQL exceptions)
            usersModel.createUser(firstName, lastName, email, password, role, userPicture);
        } else {
            // Show an error message to the user
            // This can be done using a dialog or updating a status label in your UI
        }
    }

    @Override
    public void setModel(UsersModel usersModel) {
        this.usersModel = usersModel;

    }
}
