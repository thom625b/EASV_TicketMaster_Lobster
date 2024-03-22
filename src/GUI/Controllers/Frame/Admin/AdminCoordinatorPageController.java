package GUI.Controllers.Frame.Admin;

import BE.Users;
import GUI.Controllers.IController;
import GUI.Model.UsersModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminCoordinatorPageController implements Initializable, IController {

    private UsersModel usersModel;

    @FXML
    private TableColumn<Users, String> colName, colEmail;

    @FXML
    private TableView<Users> tblViewCoorAdmin;


    public void showAllUsersInTable() {
        if (usersModel != null) {
            tblViewCoorAdmin.setItems(usersModel.getObservableUsers());
            colName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()));
            colEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        } else {
            System.err.println("UsersModel is not initialized.");
        }
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tblViewCoorAdmin.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        });
    }

    @Override
    public void setModel(UsersModel usersModel) {
        this.usersModel = usersModel;
        showAllUsersInTable();
    }
}
