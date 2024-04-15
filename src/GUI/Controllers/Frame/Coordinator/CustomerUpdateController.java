package GUI.Controllers.Frame.Coordinator;

import BE.Costumers;
import CostumException.ApplicationWideException;
import GUI.Model.CustomersModel;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.util.Duration;

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
    @FXML
    private Label lblErrorText;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            customerModel = new CustomersModel();
            setupFieldListeners();
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
        if (!validateFields()) {
            showError("Please check the input fields.");
            return;
        }

        if (currentCustomer != null) {
            updateCustomerDetails();
        } else {
            showError("Current customer is null.");
        }
            CoordinatorFrameController.getInstance().goBack();
        }

    private void updateCustomerDetails() {
        try {
            currentCustomer.setCostumerFName(txtCustomerFirstname.getText());
            currentCustomer.setCostumerLName(txtCustomerLastname.getText());
            currentCustomer.setCostumerEmail(txtCustomerEmail.getText());
            customerModel.updateCustomer(currentCustomer);
            showSuccess("Customer updated successfully.");
        } catch (Exception e) {
            showError("Update failed: " + e.getMessage());
        }
    }


    private void showError(String message) {
        lblErrorText.setTextFill(Color.RED);
        lblErrorText.setText(message);
        clearMessageAfterDelay(lblErrorText);
    }

    private void showSuccess(String message) {
        lblErrorText.setTextFill(Color.GREEN);
        lblErrorText.setText(message);
        clearMessageAfterDelay(lblErrorText);
    }

    private void clearMessageAfterDelay(Label label) {
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(event -> label.setText(""));
        pause.play();
    }
    private void setupFieldListeners() {
        setupTextFieldListener(txtCustomerFirstname);
        setupTextFieldListener(txtCustomerLastname);
        setupTextFieldListener(txtCustomerEmail);
    }

    private void setupTextFieldListener(TextField textField) {
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.trim().isEmpty()) {
                    textField.setStyle("-fx-border-color: red;");
                } else {
                    textField.setStyle("");  // Remove the border if the input is valid
                }
            }
        });
    }
    private boolean validateFields() {
        boolean isValid = true;
        if (txtCustomerFirstname.getText().trim().isEmpty()) {
            txtCustomerFirstname.setStyle("-fx-border-color: red;");
            isValid = false;
        } else {
            txtCustomerFirstname.setStyle("");
        }

        if (txtCustomerLastname.getText().trim().isEmpty()) {
            txtCustomerLastname.setStyle("-fx-border-color: red;");
            isValid = false;
        } else {
            txtCustomerLastname.setStyle("");
        }

        if (txtCustomerEmail.getText().trim().isEmpty()) {
            txtCustomerEmail.setStyle("-fx-border-color: red;");
            isValid = false;
        } else {
            txtCustomerEmail.setStyle("");
        }

        return isValid;
    }
}
