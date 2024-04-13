package GUI.Controllers.Frame.Admin;

import BE.Users;
import CostumException.ApplicationWideException;
import CostumException.ValidationException;
import GUI.Controllers.IController;
import GUI.Model.EventsModel;
import GUI.Model.UsersModel;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;

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
    private Label lblErrorText;

    @FXML
    private void initialize()  {
        listenerToTextFieldAndCombo();
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

        validateField(txtCreateFirstName, !firstName.isEmpty());
        validateField(txtCreateLastName, !lastName.isEmpty());
        validateField(txtCreateEmail, isValidEmail(email));
        validateField(txtCreatePassword, password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$"));
        validateComboBox(CreatecomboRole, selectedRole != null);


        if (!firstName.isEmpty() && !lastName.isEmpty() && isValidEmail(email) && isValidPassword(password) && selectedRole != null) {
            try {
                Users.Role role = Users.Role.valueOf(selectedRole);
                usersModel.createUser(firstName, lastName, email, password, role, "");
                updateMessageDisplay("The User Was Created", false);
            } catch (ValidationException e) {
                updateMessageDisplay(e.getMessage(), true);
            }
        } else {
            updateMessageDisplay("All fields must be filled out and a role must be selected.", true);
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
        return password.matches(passwordRegex);
    }

    private void validateField(TextField field, boolean isValid) {
        if (!isValid) {
            field.setStyle("-fx-border-color: red; -fx-border-width: 1px; -fx-border-radius: 5px;");
        } else {
            field.setStyle("");
        }
    }

    private void validateComboBox(ComboBox<String> comboBox, boolean isValid) {
        if (!isValid) {
            comboBox.setStyle("-fx-border-color: red; -fx-border-width: 1px; -fx-border-radius: 15px;");
        } else {
            comboBox.setStyle("");
        }
    }

    private void updateMessageDisplay(String message, boolean isError) {
        Platform.runLater(() -> {
            lblErrorText.setText(message);
            if (isError) {
                lblErrorText.setStyle("-fx-text-fill: red; -fx-font-size: 10px;");
            } else {
                lblErrorText.setStyle("-fx-text-fill: green; -fx-font-size: 10px;");
            }

            //Clear the message after 3 seconds
            new Timeline(new KeyFrame(
                    Duration.seconds(3),
                    ae -> lblErrorText.setText("")
            )).play();
        });
    }


    private void listenerToTextFieldAndCombo() {
        txtCreateFirstName.textProperty().addListener((observable, oldValue, newValue) -> {
            validateField(txtCreateFirstName, !newValue.isEmpty());
        });
        txtCreateLastName.textProperty().addListener((observable, oldValue, newValue) -> {
            validateField(txtCreateLastName, !newValue.isEmpty());
        });
        txtCreateEmail.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isValidEmail(newValue)) {
                validateField(txtCreateEmail, false);
                updateMessageDisplay("Please enter a valid email address.", true);  // Display error message for invalid email
            } else {
                validateField(txtCreateEmail, true);
                updateMessageDisplay("", false);  // Clear message when valid
            }
        });
        txtCreatePassword.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isValidPassword(newValue)) {
                validateField(txtCreatePassword, false);
                updateMessageDisplay("At least one digit - One lowercase and one uppercase letter - One special character and 8-20 characters long No spaces", true);  // Display error message for invalid password
            } else {
                validateField(txtCreatePassword, true);
                updateMessageDisplay("", false);  // Clear message when valid
            }
        });
        // Initialize listener for the combo box
        CreatecomboRole.valueProperty().addListener((observable, oldValue, newValue) -> {
            validateComboBox(CreatecomboRole, newValue != null);
        });
    }
}
