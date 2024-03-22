package GUI.Controllers.Frame.Coordinator;

import BE.MOCK.Event_Mock;
import GUI.Controllers.IController;
import GUI.Model.UsersModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class CoordinatorEventPageController implements IController {

    @FXML
    private TableView<Event_Mock> tblEventTable;
    @FXML
    private TableColumn<Event_Mock, String> tblEventTableCode;
    @FXML
    private TableColumn<Event_Mock, String> tblEventTableEventName;
    @FXML
    private TableColumn<Event_Mock, LocalDate> tblEventTableStartDate;
    @FXML
    private TableColumn<Event_Mock, String> tblEventTableStatus;
    @FXML
    private TableColumn<Event_Mock, Integer> tblEventTableDaysLeft;
    @FXML
    private TableColumn<Event_Mock, Integer> tblEventTableRegisteredParticipants;
    @FXML
    private TableColumn<Event_Mock, Void> editButton;

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
