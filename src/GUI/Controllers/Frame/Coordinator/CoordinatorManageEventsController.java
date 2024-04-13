package GUI.Controllers.Frame.Coordinator;

import BE.Events;
import BE.Users;
import CostumException.ApplicationWideException;
import GUI.Controllers.Frame.Admin.AdminFrameController;
import GUI.Controllers.IController;
import GUI.Model.EventsModel;
import GUI.Model.UsersModel;
import GUI.Utility.UserContext;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.util.Duration;
import javafx.util.StringConverter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class CoordinatorManageEventsController implements IController {
    UsersModel usersModel;
    EventsModel eventsModel;
    @FXML
    private ComboBox<Users> btnCoordinatorsDropDown;
    @FXML
    private ComboBox<Events> btnEventsDropDown;
    @FXML
    private Label lblErrorText;

    @FXML
    public void initialize() throws ApplicationWideException, SQLServerException, IOException {
        usersModel = new UsersModel();
        eventsModel = new EventsModel();
        populateEventButton();
        populateCoordinatorButton();
        setupComboBoxValidation();
    }
    @FXML
    private void linkCoordinatorAndEvent(ActionEvent actionEvent) {
        boolean isValidCoordinator = btnCoordinatorsDropDown.getSelectionModel().getSelectedItem() != null;
        boolean isValidEvent = btnEventsDropDown.getSelectionModel().getSelectedItem() != null;

        validateComboBox(btnCoordinatorsDropDown, isValidCoordinator);
        validateComboBox(btnEventsDropDown, isValidEvent);

        if (!isValidCoordinator && !isValidEvent) {
            updateMessageDisplay("Please select a coordinator and an event", true);
            return;
        } else if (!isValidCoordinator) {
            updateMessageDisplay("Please select a coordinator", true);
            return;
        } else if (!isValidEvent) {
            updateMessageDisplay("Please select an event", true);
            return;
        }

        try {
            int coordinatorID = btnCoordinatorsDropDown.getSelectionModel().getSelectedItem().getUserId();
            int eventID = btnEventsDropDown.getSelectionModel().getSelectedItem().getEventID();
            eventsModel.addCoordinatorToEvents(coordinatorID, eventID);
            updateMessageDisplay("The coordinator has been added to the event", false);
        } catch (ApplicationWideException e){
            updateMessageDisplay("Couldn't add the coordinator to the event: " + e.getMessage(), true);
        }
    }

    private void populateCoordinatorButton() throws ApplicationWideException {
        int currentCoordinator = UserContext.getInstance().getCurrentUserId();
        ObservableList<Users> allCoordinator = usersModel.getCoordinatorsObservable();
        ObservableList<Users> filteredCoordinators = allCoordinator.filtered(coordinator -> coordinator.getUserId() != currentCoordinator);
        btnCoordinatorsDropDown.setItems(filteredCoordinators);
        btnCoordinatorsDropDown.setConverter(new StringConverter<Users>() {
            @Override
            public String toString(Users user) {
                return user == null ? "" : user.getFirstName() + " " + user.getLastName();
            }

            @Override
            public Users fromString(String string) {
                return btnCoordinatorsDropDown.getItems().stream()
                        .filter(user -> (user.getFirstName() + " " + user.getLastName()).equals(string))
                        .findFirst().orElse(null);
            }
        });
    }
    private void populateEventButton() throws ApplicationWideException {
        int currentCoordinator = UserContext.getInstance().getCurrentUserId();
        ObservableList<Events> allEvents = eventsModel.getEventsByCoordinator(currentCoordinator);
        btnEventsDropDown.setItems(allEvents);
        btnEventsDropDown.setConverter(new StringConverter<Events>() {


            @Override
            public String toString(Events event) {
                try {
                    if (event != null && event.getEventDate() != null) {
                        LocalDate date = event.getEventDate();
                        String formattedDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                        return event.getEventName() + " - " + formattedDate;
                    }
                } catch (DateTimeParseException e) {
                    // handle if the date cannot be parsed or is in a different format
                    System.out.println("Error parsing date: " + e.getMessage());
                    return event != null ? event.getEventName() + " - Invalid date" : "";
                }
                return "";
            }

            @Override
            public Events fromString(String string) {
                return null;
            }
        });
    }


    private void updateMessageDisplay(String message, boolean isError) {
        Platform.runLater(() -> {
            lblErrorText.setText(message);
            if (isError) {
                lblErrorText.setStyle("-fx-text-fill: red; -fx-font-size: 10px;");
            } else {
                lblErrorText.setStyle("-fx-text-fill: green; -fx-font-size: 10px;");
            }

            // Clear the message after 5 seconds
            new Timeline(new KeyFrame(
                    Duration.seconds(5),
                    ae -> lblErrorText.setText("")
            )).play();
        });
    }

    private void validateComboBox(ComboBox<?> comboBox, boolean isValid) {
        if (!isValid) {
            comboBox.setStyle("-fx-border-color: red; -fx-border-width: 1px; -fx-border-radius: 15px;");
        } else {
            comboBox.setStyle("");
        }
    }

    private void setupComboBoxValidation() {
        // Validate Coordinator ComboBox
        btnCoordinatorsDropDown.valueProperty().addListener((obs, oldVal, newVal) -> {
            validateComboBox(btnCoordinatorsDropDown, newVal != null);
        });

        // Validate Events ComboBox
        btnEventsDropDown.valueProperty().addListener((obs, oldVal, newVal) -> {
            validateComboBox(btnEventsDropDown, newVal != null);
        });
    }

    @Override
    public void setModel(UsersModel usersModel) throws ApplicationWideException {
    }

    @FXML
    private void closeCoordinatorManageEventPage(ActionEvent actionEvent) {
        try {

            CoordinatorFrameController.getInstance().loadpage("/fxml/Coordinator/CoordinatorTickets");
        } catch (IOException e) {
            // Handle IOException show an error dialog)
            e.printStackTrace(); //TODO
        }
    }
}

