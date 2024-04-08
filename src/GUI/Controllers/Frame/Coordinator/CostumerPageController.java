package GUI.Controllers.Frame.Coordinator;

import BE.Costumers;
import CostumException.ApplicationWideException;
import GUI.Controllers.IController;
import GUI.Model.CostumersModel;
import GUI.Model.UsersModel;
import GUI.Utility.UserContext;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class CostumerPageController {
    private final CostumersModel customerModel;

    @FXML
    private TableColumn<Costumers, String> colCostumerEmail;

    @FXML
    private TableColumn<Costumers, String> colCostumerFirstname;

    @FXML
    private TableColumn<Costumers, String> colCostumerLastname;

    @FXML
    private TableColumn<Costumers, String> colCostumerId;

    @FXML
    private TableColumn<Costumers, Button> colCostumerUpdate;

    @FXML
    private TableView<Costumers> tblCostumerView;

    public CostumerPageController(CostumersModel customerModel) {
        this.customerModel = customerModel;
    }




    private void initializeColumns() {
        try {
            ObservableList<Costumers> costumersObservableList = FXCollections.observableArrayList(customerModel.getAllCostumers());

            colCostumerId.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCostumerID())));
            colCostumerFirstname.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCostumerFName()));
            colCostumerLastname.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCostumerLName()));
            colCostumerEmail.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCostumerEmail())));

            tblCostumerView.setItems(costumersObservableList);
        } catch (ApplicationWideException e) {
            showAlert("Error", "Failed to retrieve customers from the database.");
            e.printStackTrace();
        } catch (SQLServerException e) {
            throw new RuntimeException(e);
        }
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public void initialize() {
        initializeColumns();
    }
}
