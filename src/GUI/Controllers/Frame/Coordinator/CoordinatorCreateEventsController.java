package GUI.Controllers.Frame.Coordinator;

import BE.Events;
import CostumException.ApplicationWideException;
import GUI.Controllers.IController;
import GUI.Model.EventsModel;
import GUI.Model.UsersModel;
import GUI.Utility.UserContext;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class CoordinatorCreateEventsController implements IController {

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

    private final EventsModel eventsModel;
    @FXML
    private TextField txtEventStartTime;
    @FXML
    private TextField txtEventEndTime;

    public CoordinatorCreateEventsController() throws IOException, ApplicationWideException {
        eventsModel = new EventsModel();
    }

    @FXML
    public void AddNewEvent(ActionEvent actionEvent) {
        if (txtEventTitle.getText().isEmpty() || dpEventStartDate.getValue() == null || txtEventAddress.getText().isEmpty() ||
                txtEventZipCode.getText().isEmpty() || txtEventCity.getText().isEmpty() || txtEventDescription.getText().isEmpty() || txtEventStartTime.getText().isEmpty()
                || txtEventEndTime.getText().isEmpty()) {
            showAlert("Missing Information", "Please fill in all required fields.");
            return;
        }

        String eventName = txtEventTitle.getText();
        LocalDate eventDate = dpEventStartDate.getValue();
        int eventStatus = 1;
        int eventParticipants = 0;
        String eventAddress = txtEventAddress.getText();
        String eventCity = txtEventCity.getText();
        String eventDescription = txtEventDescription.getText();
        String eventStatTime = txtEventStartTime.getText();
        String eventEndTime = txtEventEndTime.getText();
        int eventZipCode;

        try {
            eventZipCode = Integer.parseInt(txtEventZipCode.getText());
        } catch (NumberFormatException e) {
            showAlert("Invalid Zip Code", "Please enter a valid number for the zip code.");
            return;
        }

        if (!isValidTimeFormat(eventStatTime) || !isValidTimeFormat(eventEndTime)) {
            showAlert("Invalid Time Format", "Please enter the event start time and end time in the format HH:mm.");
            return;
        }

        long remainingDays = ChronoUnit.DAYS.between(LocalDate.now(), eventDate);
        int eventRemainingDays = (int) remainingDays;

        int currentUserId = UserContext.getInstance().getCurrentUserId();

        Events newEvent = new Events(eventName, eventDate, eventStatus, eventRemainingDays, eventParticipants, eventAddress, eventZipCode, eventCity, eventDescription, currentUserId, eventStatTime, eventEndTime);
        try {
            eventsModel.createEvent(newEvent);
            showSuccessAlert("Event Created", "The event has been successfully created.");
            clearInputFields();
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
        txtEventStartTime.clear();
        txtEventEndTime.clear();
    }

    private boolean isValidTimeFormat(String time) {
        // Regular expression to match HH:mm format
        String regex = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$";
        return time.matches(regex);
    }

    @Override
    public void setModel(UsersModel usersModel) {

    }
}
