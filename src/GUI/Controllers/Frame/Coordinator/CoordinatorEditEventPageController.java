package GUI.Controllers.Frame.Coordinator;

import BE.Events; // Assuming the Event class is named Event
import GUI.Controllers.IController;
import GUI.Model.UsersModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class CoordinatorEditEventPageController implements IController {

    @FXML
    public TextField txtEditEventPicture;
    @FXML
    public TextField txtEditEventCity;
    @FXML
    public TextField txtEditEventTitle;
    @FXML
    public TextField txtEditEventZipCode;
    @FXML
    public TextField txtEditEventAddress;
    @FXML
    public TextArea txtEditEventDescription;

    @FXML
    public DatePicker dpEditEventStartDate;
    @FXML
    public Button btnEditUploadImageToEvent;
    @FXML
    public Button btnEditSaveEvent;
    @FXML
    public Button btnEditDeleteEvent;



    // Method to initialize the fields with the current event details
    public void initData(Events selectedEvent) {
        try {
            // Extract data from the selected event
            String eventName = selectedEvent.getEventName();
            String startDate = String.valueOf(selectedEvent.getEventDate()); // Assuming getStartDate returns a String
            String city = selectedEvent.getEventCity();
            String zipCode = String.valueOf(selectedEvent.getEventZipCode());
            String address = selectedEvent.getEventAddress();
            String description = selectedEvent.getEventDescription();

            // Populate the text fields with the extracted data
            txtEditEventTitle.setText(eventName);
            dpEditEventStartDate.setValue(LocalDate.parse(startDate));
            txtEditEventCity.setText(city);
            txtEditEventZipCode.setText(zipCode);
            txtEditEventAddress.setText(address);
            txtEditEventDescription.setText(description);
        } catch (DateTimeParseException e) {
            System.err.println("Error parsing start date: " + e.getMessage());
            // Handle the error appropriately, e.g., show an error message to the user
        }
    }

    public void EditUploadImageToEvent(ActionEvent actionEvent) {
    }

    public void EditEventStartDate(ActionEvent actionEvent) {
    }

    public void editSaveEvent(ActionEvent actionEvent) {
    }

    public void editDeleteEvent(ActionEvent actionEvent) {
    }

    @Override
    public void setModel(UsersModel usersModel) {

    }
}
