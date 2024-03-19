package GUI.Controllers;

import GUI.Controllers.Frame.WindowFrameController;
import GUI.Model.UsersModel;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Button SubmitBtn;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField userId;
    @FXML
    private Label loginLabel;

    private UsersModel usersModel;


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




    public void SubmitLoginPage(ActionEvent actionEvent) {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FrameWindow.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("");
                stage.show();
                stage.setResizable(false);
                WindowFrameController frameController = loader.getController();
                frameController.setModel(usersModel);

                Stage currentStage = (Stage) userId.getScene().getWindow();
                currentStage.close();

            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Could not load App.fxml");
                alert.showAndWait();
            }



    }}

