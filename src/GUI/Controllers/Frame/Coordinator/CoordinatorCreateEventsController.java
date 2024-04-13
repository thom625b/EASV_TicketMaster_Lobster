package GUI.Controllers.Frame.Coordinator;

import BE.Events;
import CostumException.ApplicationWideException;
import GUI.Controllers.IController;
import GUI.Model.EventsModel;
import GUI.Model.UsersModel;
import GUI.Utility.UserContext;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

public class CoordinatorCreateEventsController implements IController, Initializable {

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
    private final CoordinatorFrameController coordinatorFrameController;
    @FXML
    private TextField txtEventStartTime;
    @FXML
    private TextField txtEventEndTime;
    @FXML
    private Label lblErrorText;

    public CoordinatorCreateEventsController()  throws IOException, ApplicationWideException {
        eventsModel = new EventsModel();
        coordinatorFrameController = new CoordinatorFrameController();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupValidationControl();
    }

    @FXML
    public void AddNewEvent(ActionEvent actionEvent) {
        if (!validateAllFields()) {
            updateMessageDisplay("All fields must be filled correctly.", true); // Show error inline
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
            coordinatorFrameController.getInstance().goBack();
        } catch (ApplicationWideException e) {
            e.printStackTrace();
        }
    }


    private void setupValidationControl() {
        setupControlValidation(txtEventTitle);
        setupControlValidation(txtEventAddress);
        setupControlValidation(txtEventCity);
        setupControlValidation(txtEventZipCode);
        setupControlValidation(txtEventStartTime);
        setupControlValidation(txtEventEndTime);
        setupControlValidation(dpEventStartDate);
    }

    private void setupControlValidation(Control control) {
        if (control instanceof TextField) {
            TextField textField = (TextField) control;
            textField.textProperty().addListener((observable, oldValue, newValue) -> {
                validateField(textField, !newValue.trim().isEmpty());
            });
        } else if (control instanceof DatePicker) {
            DatePicker datePicker = (DatePicker) control;
            datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
                validateDatePicker(datePicker, newValue != null);
            });
        }
    }

    private boolean validateAllFields() {
        boolean hasErrors = false;

        // Validate each required field and apply red border if empty
        hasErrors |= !validateField(txtEventTitle, !txtEventTitle.getText().trim().isEmpty());
        hasErrors |= !validateField(txtEventAddress, !txtEventAddress.getText().trim().isEmpty());
        hasErrors |= !validateField(txtEventCity, !txtEventCity.getText().trim().isEmpty());
        hasErrors |= !validateField(txtEventZipCode, !txtEventZipCode.getText().trim().isEmpty());
        hasErrors |= !validateField(txtEventStartTime, !txtEventStartTime.getText().trim().isEmpty());
        hasErrors |= !validateField(txtEventEndTime, !txtEventEndTime.getText().trim().isEmpty());
        hasErrors |= !validateDatePicker(dpEventStartDate, dpEventStartDate.getValue() != null);

        return !hasErrors;
    }

    private boolean validateDatePicker(DatePicker datePicker, boolean isValid) {
        if (!isValid) {
            datePicker.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
        } else {
            datePicker.setStyle("");
        }
        return isValid;
    }

    private boolean validateField(TextField field, boolean isValid) {
        if (!isValid) {
            field.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
            return false;
        } else {
            field.setStyle("");
        }
        return true;
    }



    private void updateMessageDisplay(String message, boolean isError) {
        Platform.runLater(() -> {
            lblErrorText.setText(message);
            if (isError) {
                lblErrorText.setStyle("-fx-text-fill: red; -fx-font-size: 10px;");
            } else {
                lblErrorText.setStyle("-fx-text-fill: green; -fx-font-size: 10px;");
            }

            // Clear the message after 3 seconds
            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(event -> lblErrorText.setText(""));
            pause.play();
        });
    }

    private void showAlert(String title, String message) {
        updateMessageDisplay(message, true);
    }

    private void showSuccessAlert(String title, String message) {
        updateMessageDisplay(message, false);
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
