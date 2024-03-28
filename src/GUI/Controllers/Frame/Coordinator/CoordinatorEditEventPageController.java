package GUI.Controllers.Frame.Coordinator;

import GUI.Controllers.IController;
import GUI.Model.EventsModel;
import GUI.Model.UsersModel;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


public class CoordinatorEditEventPageController implements IController {

    @FXML
    private TextField txtEventName;
    @FXML
    private TextField txtStartDate;
    @FXML
    private TextField txtStatus;

    // Method to initialize the fields with the current event details
    public void initData(String eventName, String startDate, String status) {
        txtEventName.setText(eventName);
        txtStartDate.setText(startDate);
        txtStatus.setText(status);
    }

    // Method to save the edited event details
    @FXML
    private void saveEdit() {
        // Get the edited event details from the text fields
        String eventName = txtEventName.getText();
        String startDate = txtStartDate.getText();
        String status = txtStatus.getText();

        // Update the event details
        // You can perform any necessary validation and update operations here
        // For now, let's just print the updated details
        System.out.println("Edited Event Name: " + eventName);
        System.out.println("Edited Start Date: " + startDate);
        System.out.println("Edited Status: " + status);

        // Close the edit window
        txtEventName.getScene().getWindow().hide();
    }

    @Override
    public void setModel(UsersModel usersModel) {

    }

}
