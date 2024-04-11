package GUI.Controllers.Frame.Coordinator;

import BE.Events;
import CostumException.ApplicationWideException;
import GUI.Controllers.IController;
import GUI.Model.EventsModel;
import GUI.Model.UsersModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
    @FXML
    private TextField txtEditEventStartTime;
    @FXML
    private TextField txtEditEventEndTime;
    private EventsModel eventsModel;
    private Events selectedEvent;
    private CoordinatorEventPageController eventPageController;



    public CoordinatorEditEventPageController() throws ApplicationWideException {
        try {
            eventsModel = new EventsModel();
        } catch (Exception e) {
            throw new ApplicationWideException("Error initializing EventsModel", e);
        }
    }

    // Method to initialize the fields with the current event details
    public void initData(Events selectedEvent) {
        try {
            // Assign selectedEvent to the instance variable
            this.selectedEvent = selectedEvent;

            // Extract data from the selected event
            String eventName = selectedEvent.getEventName();
            String startDate = String.valueOf(selectedEvent.getEventDate()); // Assuming getStartDate returns a String
            String city = selectedEvent.getEventCity();
            String zipCode = String.valueOf(selectedEvent.getEventZipCode());
            String address = selectedEvent.getEventAddress();
            String description = selectedEvent.getEventDescription();
            String eventStartTime = selectedEvent.getEventStartTime();
            String eventEndTime = selectedEvent.getEventEndTime();

            // Populate the text fields with the extracted data
            txtEditEventTitle.setText(eventName);
            dpEditEventStartDate.setValue(LocalDate.parse(startDate));
            txtEditEventCity.setText(city);
            txtEditEventZipCode.setText(zipCode);
            txtEditEventAddress.setText(address);
            txtEditEventDescription.setText(description);
            txtEditEventStartTime.setText(eventStartTime);
            txtEditEventEndTime.setText(eventEndTime);
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

        if (txtEditEventTitle.getText().isEmpty() || dpEditEventStartDate.getValue() == null || txtEditEventAddress.getText().isEmpty() ||
                txtEditEventZipCode.getText().isEmpty() || txtEditEventCity.getText().isEmpty() || txtEditEventStartTime.getText().isEmpty()
                || txtEditEventEndTime.getText().isEmpty()) {
            showAlert("Missing Information", "Please fill in all required fields.");
            return;
        }

        try {
            if (selectedEvent != null) {
                // Retrieve the updated values from the text fields
                String eventName = txtEditEventTitle.getText();
                LocalDate eventDate = dpEditEventStartDate.getValue();
                String eventCity = txtEditEventCity.getText();
                String eventZipCode = txtEditEventZipCode.getText();
                String eventAddress = txtEditEventAddress.getText();
                String eventDescription = txtEditEventDescription.getText();
                String eventStartTime = txtEditEventStartTime.getText();
                String eventEndTime = txtEditEventEndTime.getText();

                // Validate zip code format
                int zipCode;
                try {
                    zipCode = Integer.parseInt(eventZipCode);
                } catch (NumberFormatException e) {
                    // Show error message for invalid zip code format
                    showAlert("Error", "Invalid zip code format. Please enter a valid number.");
                    return;
                }

                // Validate start and end time format
                if (!isValidTime(eventStartTime) || !isValidTime(eventEndTime)) {
                    // Show error message for invalid time format
                    showAlert("Error", "Invalid time format. Please enter a time in HH:MM format.");
                    return;
                }

                // Set the updated values to the selected event
                selectedEvent.setEventName(eventName);
                selectedEvent.setEventDate(eventDate);
                selectedEvent.setEventCity(eventCity);
                selectedEvent.setEventZipCode(zipCode);
                selectedEvent.setEventAddress(eventAddress);
                selectedEvent.setEventDescription(eventDescription);
                selectedEvent.setEventStartTime(eventStartTime);
                selectedEvent.setEventEndTime(eventEndTime);

                // Call the updateEvent method in the EventsModel to persist the changes
                eventsModel.updateEvent(selectedEvent);

                // Show success message to the user
                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setTitle("Success");
                success.setHeaderText(null);
                success.setContentText("Event updated successfully.");
                success.showAndWait();
                CoordinatorFrameController.getInstance().goBack();

            } else {
                // If the selected event is null, show an error message
                showAlert("Error", "Selected event is null. Cannot save changes.");
            }
        } catch (ApplicationWideException e) {
            // If there is an application-wide exception, show an error message
            showAlert("Error", "An error occurred while updating the event: " + e.getMessage());
        }
    }

    // Method to validate time format
    private boolean isValidTime(String time) {
        // Validate time format (HH:MM)
        return time.matches("^([01]\\d|2[0-3]):([0-5]\\d)$");
    }

    // Method to show an alert dialog
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void editDeleteEvent(ActionEvent actionEvent) throws ApplicationWideException {

        if (selectedEvent != null) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Delete Event");
            confirmation.setHeaderText(null);
            confirmation.setContentText("Are you sure you want to delete this event?");
            confirmation.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        eventsModel.deleteEvent(selectedEvent);
                        Alert success = new Alert(Alert.AlertType.INFORMATION);
                        success.setTitle("Success");
                        success.setHeaderText(null);
                        success.setContentText("Event deleted successfully.");
                        success.showAndWait();

                        if (eventPageController != null) {
                            eventPageController.refreshEventData();
                        }

                        CoordinatorFrameController.getInstance().goBack();

                    } catch (ApplicationWideException e) {
                        // Handle exceptions
                        Alert error = new Alert(Alert.AlertType.ERROR);
                        error.setTitle("Error");
                        error.setHeaderText(null);
                        error.setContentText("An error occurred while deleting the event: " + e.getMessage());
                        error.showAndWait();
                    }
                }
            });
        } else {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText(null);
            error.setContentText("Selected event is null. Cannot delete.");
            error.showAndWait();
        }
    }


    @Override
    public void setModel(UsersModel usersModel) {

    }

}
