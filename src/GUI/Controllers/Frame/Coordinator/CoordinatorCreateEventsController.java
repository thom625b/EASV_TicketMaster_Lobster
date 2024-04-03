package GUI.Controllers.Frame.Coordinator;

import BE.Events;
import CostumException.ApplicationWideException;
import GUI.Controllers.IController;
import GUI.Model.EventsModel;
import GUI.Model.UsersModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class CoordinatorCreateEventsController implements IController {

    private final EventsModel eventsModel;
    private CoordinatorFrameController coordinatorFrameController;

    public CoordinatorCreateEventsController() throws IOException, ApplicationWideException {
        eventsModel = new EventsModel();
        coordinatorFrameController = new CoordinatorFrameController();
    }

    @FXML
    public TextField txtEventPicture;
    @FXML
    public TextField txtEventTitle;
    @FXML
    public TextField txtEventZipCode;
    @FXML
    public TextField txtEventCity;
    @FXML
    public TextField txtEventAddress;
    @FXML
    public Button btnUploadImageToEvent;
    @FXML
    public Button btnAddNewEvent;
    @FXML
    public DatePicker dpEventStartDate;
    @FXML
    public TextArea txtEventDescription;

    @FXML
    public void EventStartDate(ActionEvent actionEvent) {

    }

    @FXML
    public void UploadImageToEvent(ActionEvent actionEvent) {
    }

    @FXML
    private void AddNewEvent(ActionEvent actionEvent) {
        // Check if any of the required fields are empty
        if (txtEventTitle.getText().isEmpty() || dpEventStartDate.getValue() == null || txtEventAddress.getText().isEmpty() ||
                txtEventZipCode.getText().isEmpty() || txtEventCity.getText().isEmpty() || txtEventDescription.getText().isEmpty()) {
            showAlert("Missing Information", "Please fill in all required fields.");
            return; // Stop execution if any required field is empty
        }

        // Parse input values
        String eventName = txtEventTitle.getText();
        LocalDate eventDate = dpEventStartDate.getValue();
        int eventStatus = 1; // Set default status or retrieve from UI
        int eventParticipants = 0; // Initialize participants count
        String eventAddress = txtEventAddress.getText();
        String eventCity = txtEventCity.getText();
        String eventDescription = txtEventDescription.getText(); // Retrieve description from UI

        int eventZipCode;
        try {
            eventZipCode = Integer.parseInt(txtEventZipCode.getText());
        } catch (NumberFormatException e) {
            // Show popup for invalid zip code
            showAlert("Invalid Zip Code", "Please enter a valid number for the zip code.");
            return; // Stop execution if the zip code is invalid
        }

        // Calculate remaining days until the event date
        long remainingDays = ChronoUnit.DAYS.between(LocalDate.now(), eventDate);
        int eventRemainingDays = (int) remainingDays;

        // Create new event
        Events newEvent = new Events(eventName, eventDate, eventStatus, eventRemainingDays, eventParticipants, eventAddress, eventZipCode, eventCity, eventDescription);

        try {
            // Create the event
            eventsModel.createEvent(newEvent);

            // Show success message
            showSuccessAlert("Event Created", "The event has been successfully created.");

            // Clear input fields
            clearInputFields();

            // Optionally, you can go back to the previous page or perform other actions here

        } catch (ApplicationWideException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccessAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearInputFields() {
        txtEventTitle.clear();
        dpEventStartDate.setValue(null);
        txtEventAddress.clear();
        txtEventZipCode.clear();
        txtEventCity.clear();
        txtEventDescription.clear();
    }

    @Override
    public void setModel(UsersModel usersModel) {

    }

}
