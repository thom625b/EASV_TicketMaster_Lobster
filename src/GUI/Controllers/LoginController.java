package GUI.Controllers;

import BE.Users;
import CostumException.ValidationException;
import GUI.Controllers.Frame.Admin.AdminFrameController;
import GUI.Controllers.Frame.Coordinator.CoordinatorFrameController;
import GUI.Model.UsersModel;
import GUI.Utility.LoginResult;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private UsersModel usersModel;
    @FXML
    private ImageView ImgLogin;
    @FXML
    private TextField adminMail, userMail;
    @FXML
    private PasswordField adminPassword, userPassword;
    @FXML
    private BorderPane backBorder, borderInside;
    @FXML
    private Button btnSubmitAdmin, btnSubmitEvent;
    @FXML
    private HBox hBoxBottom, hBoxTop;
    @FXML
    private VBox vBoxLeft, vBoxRight;
    @FXML
    private Label lblAdmin,lblLoginAdmin, lblLoginEventCoordinator, lblUser, lblstatus;
    @FXML
    private Pane slidingPane;
    @FXML
    private StackPane stackImg;
    @FXML
    private Tab tabAdmin, tabEventCordinator;
    @FXML
    private TabPane tabPaneLogin;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            usersModel = new UsersModel();
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize UsersModel.", e);
        }
    }

    @FXML
    public void SubmitLoginPageAdmin(ActionEvent actionEvent) {
        try {
            String email = adminMail.getText();
            String password = adminPassword.getText();
            Users.Role expectedRole = Users.Role.ADMIN;

            if (usersModel.verifyLoginWithRole(email, password,expectedRole)) {
                loadAdminDashboard();
            } else {
                lblLoginAdmin.setText("Login Failed. Please check your credentials.");
            }
        } catch (ValidationException e) {
            lblLoginAdmin.setText(e.getMessage());
        } catch (Exception e) {
            lblLoginAdmin.setText("An unexpected error occurred.");
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

            Stage currentStage = (Stage) adminMail.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Could not load Admin Dashboard.");
            alert.showAndWait();
        }
    }

    @FXML
    private void SubmitLoginPageCoordinator(ActionEvent actionEvent) {
        try {
            String email = userMail.getText();
            String password = userPassword.getText();
            Users.Role expectedRole = Users.Role.COORDINATOR;

            if (usersModel.verifyLoginWithRole(email, password,expectedRole)) {
                loadCoordinatorDashboard();
            } else {
                lblLoginEventCoordinator.setText("Login Failed. Please check your credentials.");
            }
        } catch (ValidationException e) {
            lblLoginEventCoordinator.setText(e.getMessage());
        } catch (Exception e) {
            lblLoginEventCoordinator.setText("An unexpected error occurred.");
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

            Stage currentStage = (Stage) userMail.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Could not load Coordinator Dashboard.");
            alert.showAndWait();
        }
    }


    //skifter label mellem Coordinator og Admin
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

    //skifter label mellem Admin og Coordinator
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

    }


