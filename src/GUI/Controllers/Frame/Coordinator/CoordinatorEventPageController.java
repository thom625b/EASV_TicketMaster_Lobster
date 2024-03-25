package GUI.Controllers.Frame.Coordinator;

import GUI.Controllers.IController;
import GUI.Model.UsersModel;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.time.LocalDate;

public class CoordinatorEventPageController implements IController {

    @FXML
    private TableView<Event> tblEventTable;
    @FXML
    private TableColumn<Event, String> tblEventTableCode;
    @FXML
    private TableColumn<Event, String> tblEventTableEventName;
    @FXML
    private TableColumn<Event, LocalDate> tblEventTableStartDate;
    @FXML
    private TableColumn<Event, String> tblEventTableStatus;
    @FXML
    private TableColumn<Event, Integer> tblEventTableDaysLeft;
    @FXML
    private TableColumn<Event, Integer> tblEventTableRegisteredParticipants;
    @FXML
    private TableColumn<Event, Void> editButton;

    @FXML
    public void goToCreateNewEvent(ActionEvent actionEvent) {
        CoordinatorFrameController.getInstance().loadPageCoordinatorCreateEventPage();
    }

    @FXML
    public void initialize() {

    }

    @Override
    public void setModel(UsersModel usersModel) {

    }
}
