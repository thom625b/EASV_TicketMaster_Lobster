package GUI.Controllers.Frame.Coordinator;

import BE.Events;
import CostumException.ApplicationWideException;
import GUI.Controllers.IController;
import GUI.Model.EventsModel;
import GUI.Model.UsersModel;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class CoordinatorEventPageController implements IController {

    @FXML
    private TableView<Events> tblEventTable;
    @FXML
    private TableColumn<Events, String> tblEventTableCode;
    @FXML
    private TableColumn<Events, String> tblEventTableEventName;
    @FXML
    private TableColumn<Events, LocalDate> tblEventTableStartDate;
    @FXML
    private TableColumn<Events, String> tblEventTableStatus;
    @FXML
    private TableColumn<Events, Integer> tblEventTableDaysLeft;
    @FXML
    private TableColumn<Events, Integer> tblEventTableRegisteredParticipants;
    @FXML
    private TableColumn<Events, Void> editButton;

    private final EventsModel eventsModel;

    public CoordinatorEventPageController() throws IOException {
        eventsModel = new EventsModel();
    }

    @FXML
    public void goToCreateNewEvent(ActionEvent actionEvent) {
        CoordinatorFrameController.getInstance().openPageCoordinatorCreateEventPage();
    }

    @FXML
    public void initialize() {
        initializeColumns();
        initializeEditButtonColumn();
    }

    private void initializeEditButtonColumn() {
        editButton.setCellFactory(getEditButtonCellFactory());
    }

    private Callback<TableColumn<Events, Void>, TableCell<Events, Void>> getEditButtonCellFactory() {
        return new Callback<>() {
            @Override
            public TableCell<Events, Void> call(final TableColumn<Events, Void> param) {
                return new TableCell<>() {
                    private final Button btnEdit = new Button("Edit");

                    {
                        btnEdit.setOnAction((ActionEvent event) -> {
                            Events selectedEvent = getTableView().getItems().get(getIndex());
                            CoordinatorFrameController.getInstance().openEditEventPage(selectedEvent);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btnEdit);
                        }
                    }
                };
            }
        };
    }

    private void initializeColumns() {
        try {
            List<Events> events = eventsModel.getAllEvents();
            ObservableList<Events> eventList = FXCollections.observableArrayList(events);

            tblEventTableCode.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getEventID())));
            tblEventTableEventName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEventName()));
            tblEventTableStartDate.setCellValueFactory(cellData -> new SimpleObjectProperty<>(LocalDate.parse(cellData.getValue().getEventDate())));
            tblEventTableStatus.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getEventStatus())));
            tblEventTableDaysLeft.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getEventRemainingDays()).asObject());
            tblEventTableRegisteredParticipants.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getEventParticipants()).asObject());

            tblEventTable.setItems(eventList);
        } catch (ApplicationWideException e) {
            showAlert("Error", "Failed to retrieve events from the database.");
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

    @Override
    public void setModel(UsersModel usersModel) {

    }
}
