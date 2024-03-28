package GUI.Controllers.Frame.Coordinator;

import BE.Events;
import BE.Users;
import CostumException.ApplicationWideException;
import GUI.Controllers.IController;
import GUI.Model.EventsModel;
import GUI.Model.UsersModel;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


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
    }
    @FXML
    private void goToHomePage(ActionEvent actionEvent) {
       CoordinatorFrameController frameController = new CoordinatorFrameController();
       frameController.goBack();
    }

    @FXML
    private void mergeCoordAndEvent(ActionEvent actionEvent) {
    }

    @FXML
    private void getCoordinators(ActionEvent actionEvent) throws ApplicationWideException, SQLServerException, IOException {
        try {
            ObservableList<Users> coordinators = usersModel.getCoordinatorsObservable();
            btnCoordinatorsDropDown.setItems(coordinators);

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



    @Override
    public void setModel(UsersModel usersModel) throws ApplicationWideException {

    }

    @FXML
    private void getEvents(ActionEvent actionEvent) {
        System.out.println("pressed events");
        try {
            ObservableList<Events> events = eventsModel.getObsEvents();
            btnEventsDropDown.setItems(events);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            btnEventsDropDown.setConverter(new StringConverter<Events>() {
                @Override
                public String toString(Events event) {
                    if (event != null && event.getEventDate() != null) {
                        LocalDate date = LocalDate.parse(event.getEventDate());
                        String dateStr = date.format(formatter);
                        return event.getEventName() + " - " + dateStr;
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
}

