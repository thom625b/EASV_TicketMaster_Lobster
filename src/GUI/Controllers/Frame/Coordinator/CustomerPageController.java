package GUI.Controllers.Frame.Coordinator;

import BE.Costumers;
import BE.Users;
import CostumException.ApplicationWideException;
import GUI.Controllers.Frame.Admin.AdminFrameController;
import GUI.Model.CustomersModel;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class CustomerPageController implements Initializable {

    @FXML
    private TableColumn<Costumers, String> colCostumerEmail;

    @FXML
    private TableColumn<Costumers, String> colCostumerFirstname;

    @FXML
    private TableColumn<Costumers, String> colCostumerLastname;

    @FXML
    private TableColumn<Costumers, String> colCostumerId;

    @FXML
    private TableColumn<Costumers, Void> colCostumerUpdate;

    @FXML
    private TableView<Costumers> tblCostumerView;

    private CustomersModel customerModel;
    @FXML
    private TextField txtCustomerFirstname;
    @FXML
    private TextField txtCustomerLastname;
    @FXML
    private TextField txtCustomerEmail;
    private Costumers currentCustomer;

    public CustomerPageController() {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            customerModel = new CustomersModel();
            initializeColumns();
            initializeButtonColumn();
            System.out.println("Initializing: txtCustomerFirstname = " + txtCustomerFirstname);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ApplicationWideException e) {
            throw new RuntimeException(e);
        } catch (SQLServerException e) {
            throw new RuntimeException(e);
        }


    }

    private void initializeColumns() throws ApplicationWideException, SQLServerException {


        tblCostumerView.setItems(customerModel.getAllCostumers());
        colCostumerId.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCostumerID())));
        colCostumerFirstname.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCostumerFName()));
        colCostumerLastname.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCostumerLName()));
        colCostumerEmail.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCostumerEmail())));


    }

    private void initializeButtonColumn() {
        colCostumerUpdate.setCellFactory(param -> new TableCell<>() {
            private final Button btn = createButtonWithIcon("/pictures/pen.png");

            {
                btn.setOnAction(event -> {
                    Costumers selectedCustomer = getTableView().getItems().get(getIndex());
                    System.out.println("Selected customer: " + selectedCustomer);

                    if (selectedCustomer != null) {
                        openNewFxmlFile(selectedCustomer);
                    } else {
                        showAlert("Selection Error", "No customer selected.");
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        });
    }

    private Button createButtonWithIcon(String imagePath) {
        Button button = new Button();
        try {
            Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
            button.setGraphic(new ImageView(img));
            button.setStyle("-fx-background-color: transparent;");
        } catch (NullPointerException | IllegalArgumentException e) {
            System.err.println("Error loading image: " + imagePath + " - " + e.getMessage());
            // Optionally set a default icon or handle the error appropriately
        }
        return button;
    }

    private void openNewFxmlFile(Costumers Customer) {
        CoordinatorFrameController.getInstance().loadAdminUpdateCustomer(Customer);
    }

    private void showAlert(String title, String message) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
    }

    public void setCurrentCustomer(Costumers customer) {
        this.currentCustomer = customer;
        updateTextFields(customer);
    }


    private void updateTextFields(Costumers customer) {
        txtCustomerFirstname.setText(customer.getCostumerFName());
        txtCustomerLastname.setText(customer.getCostumerLName());
        txtCustomerEmail.setText(customer.getCostumerEmail());
    }

    @FXML
    private void updateCostumerToDatabase(ActionEvent actionEvent) throws ApplicationWideException, SQLServerException {
        if (currentCustomer != null) {
            currentCustomer.setCostumerFName(txtCustomerFirstname.getText());
            currentCustomer.setCostumerLName(txtCustomerLastname.getText());
            currentCustomer.setCostumerEmail(txtCustomerEmail.getText());

            customerModel.updateCustomer(currentCustomer);
            showAlert("Success", "Customer updated successfully.");
        }
    }


}
