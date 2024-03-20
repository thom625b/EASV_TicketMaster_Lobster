package GUI.Controllers;

import BE.Users;
import GUI.Controllers.Frame.Admin.AdminFrameController;
import GUI.Controllers.Frame.Coordinator.CoordinatorFrameController;
import GUI.Model.UsersModel;
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
        } catch (SQLServerException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    @FXML
    public void SubmitLoginPageAdmin(ActionEvent actionEvent) {

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
                Alert alert = new Alert(Alert.AlertType.ERROR, "Could not load App.fxml");
                alert.showAndWait();
            }
    }


    @FXML
    private void SubmitLoginPageCoordinator(ActionEvent actionEvent) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Coordinator/CoordinatorFrameWindow.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("");
                stage.show();
                stage.setResizable(false);
                CoordinatorFrameController frameController = loader.getController();
                frameController.setModel(usersModel);

                Stage currentStage = (Stage) userMail.getScene().getWindow();
                currentStage.close();

            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Could not load App.fxml");
                alert.showAndWait();
            }
        }





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


