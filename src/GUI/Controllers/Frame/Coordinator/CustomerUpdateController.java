package GUI.Controllers.Frame.Coordinator;

import BE.Costumers;
import CostumException.ApplicationWideException;
import GUI.Model.CustomersModel;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomerUpdateController implements Initializable {

    @FXML
    private TextField txtCustomerFirstname;
    @FXML
    private TextField txtCustomerLastname;
    @FXML
    private TextField txtCustomerEmail;

    CustomersModel customerModel;
    private Costumers currentCustomer;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            customerModel = new CustomersModel();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ApplicationWideException e) {
            throw new RuntimeException(e);
        } catch (SQLServerException e) {
            throw new RuntimeException(e);
        }
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


            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setTitle("Success");
            success.setHeaderText(null);
            success.setContentText("Customer updated successfully.");
            success.showAndWait();

            CoordinatorFrameController.getInstance().goBack();
        } else {
            // Handle case where currentCustomer is null
            System.err.println("Current customer is null.");
        }
    }
}
