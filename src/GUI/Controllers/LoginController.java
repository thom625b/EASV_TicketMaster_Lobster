package GUI.Controllers;

import BE.Users;
import CostumException.ValidationException;
import GUI.Controllers.Frame.Admin.AdminFrameController;
import GUI.Controllers.Frame.Coordinator.CoordinatorFrameController;
import GUI.Model.UsersModel;
import GUI.Utility.UserContext;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class LoginController implements Initializable {

    private UsersModel usersModel;
    @FXML
    private ImageView ImgLogin;

    @FXML
    private TextField adminMail;

    @FXML
    private PasswordField adminPassword;

    @FXML
    private BorderPane backBorder;

    @FXML
    private BorderPane borderInside;

    @FXML
    private Button btnSubmitAdmin;

    @FXML
    private Button btnSubmitEvent;

    @FXML
    private HBox hBoxBottom;

    @FXML
    private HBox hBoxTop;

    @FXML
    private Label lblAdmin;

    @FXML
    private Label lblLoginAdmin;

    @FXML
    private Label lblLoginEventCoordinator;

    @FXML
    private Label lblUser;

    @FXML
    private Label lblstatus;

    @FXML
    private Pane slidingPane;

    @FXML
    private StackPane stackImg;

    @FXML
    private Tab tabAdmin;

    @FXML
    private Tab tabEventCordinator;

    @FXML
    private TabPane tabPaneLogin;

    @FXML
    private TextField userMail;

    @FXML
    private PasswordField userPassword;

    @FXML
    private VBox vBoxLeft;

    @FXML
    private VBox vBoxRight;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            usersModel = new UsersModel();
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize UsersModel.", e);
        }
        setupFieldListeners();

        adminMail.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                submitLoginPageAdmin(new ActionEvent());
            }
        });

        adminPassword.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                submitLoginPageAdmin(new ActionEvent());
            }
        });


        userMail.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                submitLoginPageCoordinator(new ActionEvent());
            }
        });

        userPassword.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                submitLoginPageCoordinator(new ActionEvent());
            }
        });


    }



    @FXML
    public void submitLoginPageAdmin(ActionEvent actionEvent) {
        String email = adminMail.getText();
        String password = adminPassword.getText();

        // Check if the fields are empty and apply red border if they are
        boolean isEmailEmpty = email.isEmpty();
        boolean isPasswordEmpty = password.isEmpty();

        if (isEmailEmpty || isPasswordEmpty) {
            if (isEmailEmpty) {
                updateFieldStyle(adminMail, false); // Apply red border for empty email field
            }
            if (isPasswordEmpty) {
                updateFieldStyle(adminPassword, false); // Apply red border for empty password field
            }
            updateLoginMessage(adminMail, "Email and password are required.", true); // Display error message
            return;
        }

        // Attempt to log in
        try {
            Users.Role expectedRole = Users.Role.ADMIN;
            Users user = usersModel.verifyLoginWithRole(email, password, expectedRole);
            if (user != null) {
                UserContext.getInstance().setCurrentUser(user);
                loadAdminDashboard();
                updateFieldStyle(adminMail, true); // Clear any red border if login is successful
                updateFieldStyle(adminPassword, true);
                updateLoginMessage(adminMail, "Login successful.", false); // Update login message to successful
            } else {
                updateFieldStyle(adminMail, false); // Keep red border if credentials are incorrect
                updateFieldStyle(adminPassword, false);
                updateLoginMessage(adminMail, "Login Failed. Please check your credentials.", true); // Error message for failed login
            }
        } catch (Exception e) {
            updateFieldStyle(adminMail, false); // Apply red border if an exception occurs
            updateFieldStyle(adminPassword, false);
            updateLoginMessage(adminMail, "An unexpected error occurred: " + e.getMessage(), true); // Display error for exception
        }

    }

    private void loadAdminDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Admin/AdminFrameWindow.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("");
            stage.show();
            stage.setResizable(false);
            AdminFrameController frameController = loader.getController();
            frameController.setModel(usersModel);
            frameController.loadpage("/fxml/Admin/AdminHomePage");
            Stage currentStage = (Stage) adminMail.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Could not load Admin Dashboard.");
            alert.showAndWait();
        }
    }


    @FXML
    private void submitLoginPageCoordinator(ActionEvent actionEvent) {
        String email = userMail.getText();
        String password = userPassword.getText();

        boolean isEmailEmpty = email.isEmpty();
        boolean isPasswordEmpty = password.isEmpty();

        if (isEmailEmpty || isPasswordEmpty) {
            if (isEmailEmpty) {
                updateFieldStyle(userMail, false);
            }
            if (isPasswordEmpty) {
                updateFieldStyle(userPassword, false);
            }
            updateLoginMessage(userMail, "Email and password are required.", true);
            return;
        }

        try {
            Users.Role expectedRole = Users.Role.COORDINATOR;
            Users user = usersModel.verifyLoginWithRole(email, password, expectedRole);
            if (user != null) {
                UserContext.getInstance().setCurrentUser(user);
                loadCoordinatorDashboard();
                updateFieldStyle(userMail, true);
                updateFieldStyle(userPassword, true);
                updateLoginMessage(userMail, "Login successful.", false);
            } else {
                updateFieldStyle(userMail, false);
                updateFieldStyle(userPassword, false);
                updateLoginMessage(userMail, "Login Failed. Please check your credentials.", true);
            }
        } catch (Exception e) {
            updateFieldStyle(userMail, false);
            updateFieldStyle(userPassword, false);
            updateLoginMessage(userMail, "An unexpected error occurred: " + e.getMessage(), true);
        }
    }


    private void loadCoordinatorDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Coordinator/CoordinatorFrameWindow.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Coordinator Dashboard");
            stage.show();
            stage.setResizable(false);
            CoordinatorFrameController frameController = loader.getController();
            frameController.setModel(usersModel);
            frameController.loadpage("/fxml/Coordinator/CoordinatorTickets");
            Stage currentStage = (Stage) userMail.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Could not load Coordinator Dashboard.");
            alert.showAndWait();
        }
    }


    //Change label between Coordinator and Admin
    @FXML
    void openAdminTab(Event event) {
        TranslateTransition toLeftTransition = new TranslateTransition(new Duration(500), lblstatus);
        toLeftTransition.setToX(slidingPane.getTranslateX());
        toLeftTransition.play();
        toLeftTransition.setOnFinished((ActionEvent event2) -> {
            lblstatus.setText("ADMINISTRATOR");
        });
        tabPaneLogin.getSelectionModel().select(tabAdmin);
    }

    //Change label between Admin and Coordinator
    @FXML
    void openUserTab(Event event) {
        TranslateTransition toRightAnimation = new TranslateTransition(new Duration(500) ,lblstatus);
        toRightAnimation.setToX(slidingPane.getTranslateX()+(slidingPane.getPrefWidth()-lblstatus.getPrefWidth()));
        toRightAnimation.play();
        toRightAnimation.setOnFinished((ActionEvent event1) -> {
            lblstatus.setText("EVENT COORDINATOR");
        });
        tabPaneLogin.getSelectionModel().select(tabEventCordinator);
    }


    private void updateLoginMessage(TextField textField, String message, boolean isError) {
        Label targetLabel = (textField == adminMail || textField == adminPassword) ? lblLoginAdmin : lblLoginEventCoordinator;
        Platform.runLater(() -> {
            targetLabel.setText(message);
            if (isError) {
                targetLabel.setStyle("-fx-text-fill: red; -fx-font-size: 10px;");
            } else {
                targetLabel.setStyle("-fx-text-fill: green; -fx-font-size: 10px;");
            }
        });
    }

    private void setupFieldListeners() {
        // Add listeners to both admin and coordinator login fields
        addValidationListeners(adminMail, true);
        addValidationListeners(adminPassword, false);
        addValidationListeners(userMail, true);
        addValidationListeners(userPassword, false);
    }

    private void addValidationListeners(TextField textField, boolean isEmail) {
        textField.focusedProperty().addListener((observable, oldFocus, newFocus) -> {
            if (newFocus) {
                textField.getStyleClass().removeAll("field-error", "field-normal"); // removes all unnecessary focus
                textField.getStyleClass().add("field-selected");
                updateLoginMessage(textField, "Field cannot be empty.", true);
            } else {
                textField.getStyleClass().remove("field-selected");
                boolean isValid = isEmail ? isValidEmail(textField.getText()) : isValidPassword(textField.getText());
                updateFieldStyle(textField, isValid);
                if (!isValid) {
                    updateLoginMessage(textField, isEmail ? "Invalid email format." : "Password does not meet requirements.", true);
                } else {
                    updateLoginMessage(textField, "", false);
                }
            }
        });

        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean isValid = isEmail ? isValidEmail(newValue) : isValidPassword(newValue);
            if (newValue.trim().isEmpty()) {
                updateFieldStyle(textField, false);
                updateLoginMessage(textField, "Field cannot be empty.", true);
            } else if (!isValid) {
                updateFieldStyle(textField, false);
               updateLoginMessage(textField, isEmail ? "Invalid email format." : "Password does not meet requirements.", true);
            } else {
                updateFieldStyle(textField, true);
                updateLoginMessage(textField, "", false);
            }
        });
    }

    private void updateFieldStyle(Control field, boolean isValid) {
        Platform.runLater(() -> {
            field.getStyleClass().removeAll("field-error", "field-normal", "field-selected");
            if (!isValid) {
                field.getStyleClass().add("field-error");
            } else {
                field.getStyleClass().add("field-selected");
            }
        });
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

    private void validateField(Control field, boolean isValid) {
        Platform.runLater(() -> {
            if (!isValid) {
                field.setStyle("-fx-border-color: red; -fx-border-width: 1px; -fx-border-radius: 15px;");
            } else {
                field.setStyle("");
            }
        });
    }
}


