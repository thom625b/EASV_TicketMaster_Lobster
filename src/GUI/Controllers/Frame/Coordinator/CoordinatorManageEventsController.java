package GUI.Controllers.Frame.Coordinator;

import BE.Events;
import BE.Users;
import CostumException.ApplicationWideException;
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
        getCoordinators(null);
        getEvents(null);
        btnCoordinatorsDropDown.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
            }
        });
        btnEventsDropDown.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
            }
        });
    }
    @FXML
    private void linkCoordinatorAndEvent(ActionEvent actionEvent) {
        Users selectedCoordinatorID = getCoordinatorID();
        //System.out.println(selectedCoordinatorID.getUserId());
        Events selectedEventID = getEventID();
        //System.out.println(selectedEventID.getEventID());

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
            eventsModel.addCoordinatorToEvents(eventID, coordinatorID);
            showAlert("Succes", "The coordinator has been added to the event");
        } catch (ApplicationWideException e){
            showAlert("Error", "Coldn't add the coordinator to the event");
            e.printStackTrace();
            new ApplicationWideException("Error communicating with the database");
        }
    }

    private Users getCoordinatorID(){
         return btnCoordinatorsDropDown.getSelectionModel().getSelectedItem();
    }

    private Events getEventID(){
        return btnEventsDropDown.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void getCoordinators(ActionEvent actionEvent) throws ApplicationWideException, SQLServerException, IOException {
        try {
            ObservableList<Users> allCoordinators = usersModel.getCoordinatorsObservable();
            int currentCoordinator = UserContext.getInstance().getCurrentUserId();
            ObservableList<Users> filteredCoordinators = allCoordinators.filtered(coordinator -> coordinator.getUserId() != currentCoordinator);
            btnCoordinatorsDropDown.setItems(filteredCoordinators);

            // laver BE om til en læsbar tekst
            btnCoordinatorsDropDown.setConverter(new StringConverter<Users>() {
                @Override
                public String toString(Users user) {
                    return user != null ? user.getFirstName() + " " + user.getLastName() : "";
                }
                @Override
                public Users fromString(String string) {
                    return null;
                }
            });
        } catch (ApplicationWideException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void getEvents(ActionEvent actionEvent) {
        try {
            int coordinatorsEvents = UserContext.getInstance().getCurrentUserId();
            ObservableList<Events> events = eventsModel.getEventsByCoordinator(coordinatorsEvents);
            btnEventsDropDown.setItems(events);
            //System.out.println("number of events in the list: " +events.size());
            DateTimeFormatter oldFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter newFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            btnEventsDropDown.setConverter(new StringConverter<Events>() {
                @Override
                public String toString(Events event) {
                    try {
                        if (event != null && event.getEventDate() != null) {
                            LocalDate date = LocalDate.parse(event.getEventDate(), oldFormat);
                            //System.out.println("Events aften formatting: " + events.size());
                            return event.getEventName() + " - " + date.format(newFormat);
                        }
                    } catch (DateTimeParseException e) {
                        // håndtere hvis datoen ikke kan læses eller den står i et andet format
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
        } catch (ApplicationWideException e) {
            new ApplicationWideException("Error loading events", e);
        }
    }
    @Override
    public void setModel(UsersModel usersModel) throws ApplicationWideException {
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

