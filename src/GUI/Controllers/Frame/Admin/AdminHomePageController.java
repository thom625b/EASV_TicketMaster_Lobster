package GUI.Controllers.Frame.Admin;

import BE.Users;
import CostumException.ApplicationWideException;
import GUI.Controllers.IController;
import GUI.Model.UsersModel;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.regex.Pattern;

public class AdminHomePageController implements IController {
    @FXML
    private PasswordField txtCreatePassword;
    @FXML
    private Button btnCreateCoordinator;
    @FXML
    private ComboBox<String> CreatecomboRole;
    @FXML
    private TextField txtCreateFirstName, txtCreateLastName, txtCreateEmail;


    private UsersModel usersModel;



    @FXML
    private void initialize() throws SQLServerException, IOException, ApplicationWideException {

        usersModel = new UsersModel();
    }


    // Setter method for usersModel, in case it needs to be set externally
    @Override
    public void setModel(UsersModel usersModel) {
        this.usersModel = usersModel;

    }


    @FXML
    void handleAddUser(ActionEvent event) {

        String firstName = txtCreateFirstName.getText();
        String lastName = txtCreateLastName.getText();
        String email = txtCreateEmail.getText();
        String password = txtCreatePassword.getText();
        String selectedRole = CreatecomboRole.getValue();
        String userPicture = ""; // Replace with get picture

        if (selectedRole != null && !firstName.isEmpty() && !lastName.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
            Users.Role role = Users.Role.valueOf(selectedRole);

            usersModel.createUser(firstName, lastName, email, password, role, userPicture);
        } else {
            // Show an error message to the user

        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }


}
