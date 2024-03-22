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

    /**
     * Creates a new event and adds it to the table.
     * This method is invoked when the "Create New Event" button is clicked.
     *
     * @param actionEvent The action event triggered by the button click.
     */
    @FXML
    public void createNewEvent(ActionEvent actionEvent) {
        // Create new placeholder event objects
        Event_Mock eventOne = new Event_Mock("001", "Second Event", LocalDate.now(), "Pending", 25, 0);
        Event_Mock eventTwo = new Event_Mock("002", "Third Event", LocalDate.now(), "Pending", 20, 0);
        Event_Mock eventThree = new Event_Mock("003", "Fourth Event", LocalDate.now(), "Pending", 15, 0);
        Event_Mock eventFour = new Event_Mock("004", "Fifth Event", LocalDate.now(), "Pending", 10, 0);
        Event_Mock eventFive = new Event_Mock("005", "Sixth Event", LocalDate.now(), "Pending", 5, 0);
        Event_Mock eventSix = new Event_Mock("006", "Seventh Event", LocalDate.now(), "Pending", 3, 0);

        // Add the new events to the TableView
        tblEventTable.getItems().addAll(eventOne, eventTwo, eventThree, eventFour, eventFive, eventSix);
    }

    @FXML
    public void initialize() {
        // Set up cell value factories for each column
        tblEventTableCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        tblEventTableEventName.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        tblEventTableStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        tblEventTableStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tblEventTableDaysLeft.setCellValueFactory(new PropertyValueFactory<>("daysLeft"));
        tblEventTableRegisteredParticipants.setCellValueFactory(new PropertyValueFactory<>("registeredParticipants"));

        editButton.setCellFactory(param -> new TableCell<Event_Mock, Void>() {
            private final Button editButton = new Button("Edit");

            {
                editButton.setOnAction(event -> {
                    // Get the selected item from the table
                    Event_Mock selectedEvent = getTableView().getItems().get(getIndex());

                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(editButton);
                }
            }
        });
    }

    @Override
    public void setModel(UsersModel usersModel) {

    }
}
