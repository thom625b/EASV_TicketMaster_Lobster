package GUI.Controllers.Frame.Admin;

import BE.Users;
import CostumException.ApplicationWideException;
import CostumException.ValidationException;
import GUI.Controllers.IController;
import GUI.Model.EventsModel;
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
    private void initialize()  {

    }


    // Setter method for usersModel
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
            if (isValidEmail(email)) {
                Users.Role role = Users.Role.valueOf(selectedRole);
                try {
                    usersModel.createUser(firstName, lastName, email, password, role, userPicture);
                    showAlert("Succes", "The User Was Created", Alert.AlertType.INFORMATION);
                } catch (ValidationException e) {
                    showAlert("Validation Error", e.getMessage(), Alert.AlertType.ERROR);
                }
            } else {
                showAlert("Input Error", "Please enter a valid email address.", Alert.AlertType.WARNING);
            }
        } else {
            showAlert("Input Error", "All fields must be filled out and a role must be selected.", Alert.AlertType.WARNING);
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
