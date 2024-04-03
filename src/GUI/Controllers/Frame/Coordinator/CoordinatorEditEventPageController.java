package GUI.Controllers.Frame.Coordinator;

import BE.Events; // Assuming the Event class is named Event
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
