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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.function.Consumer;


public class CoordinatorManageEventsController implements IController {
    UsersModel usersModel;
    EventsModel eventsModel;
    @FXML
    private ComboBox<Users> btnCoordinatorsDropDown;
    @FXML
    private ComboBox<Events> btnEventsDropDown;

    @FXML
    public void initialize() throws ApplicationWideException, SQLServerException, IOException {
        usersModel = new UsersModel();
        eventsModel = new EventsModel();
        populateEventButton();
        populateCoordinatorButton();
    }
    @FXML
    private void linkCoordinatorAndEvent(ActionEvent actionEvent) {
        Users selectedCoordinatorID = btnCoordinatorsDropDown.getSelectionModel().getSelectedItem();
        Events selectedEventID = btnEventsDropDown.getSelectionModel().getSelectedItem();

        if (selectedCoordinatorID == null && selectedEventID == null){
            showAlert("Selection trouble", "Please select a coordinator and an event");
        } else if (selectedCoordinatorID == null) {
            showAlert("Selection trouble", "Please select a coordinator");
        } else if (selectedEventID == null) {
            showAlert("Selection trouble", "Please select an event");
            return;
        }

        int coordinatorID = selectedCoordinatorID.getUserId();
        int eventID = selectedEventID.getEventID();

        try {
            eventsModel.addCoordinatorToEvents(coordinatorID, eventID);
            showAlert("Succes", "The coordinator has been added to the event");
        } catch (ApplicationWideException e){
            showAlert("Error", "Coldn't add the coordinator to the event");
            e.printStackTrace();
            new ApplicationWideException("Error communicating with the database");
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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void setModel(UsersModel usersModel) throws ApplicationWideException {
    }

    @FXML
    private void closeCoordinatorManageEventPage(ActionEvent actionEvent) {
        try {

            AdminFrameController.getInstance().loadpage("/fxml/Coordinator/CoordinatorHomePage");
        } catch (IOException e) {
            // Handle IOException show an error dialog)
            e.printStackTrace(); //TODO
        }
    }
}

