package GUI.Controllers.Frame.Coordinator;

import BE.Events;
import CostumException.ApplicationWideException;
import GUI.Controllers.IController;
import GUI.Model.EventsModel;
import GUI.Model.UsersModel;
import GUI.Utility.UserContext;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class CoordinatorEventPageController implements IController {

    @FXML
    private TableView<Events> tblEventTable;
    @FXML
    private TableColumn<Events, String> tblEventTableCode;
    @FXML
    private TableColumn<Events, String> tblEventTableEventName;
    @FXML
    private TableColumn<Events, String> tblEventTableStartDate;
    @FXML
    private TableColumn<Events, String> tblEventTableStatus;
    @FXML
    private TableColumn<Events, Integer> tblEventTableDaysLeft;
    @FXML
    private TableColumn<Events, Integer> tblEventTableRegisteredParticipants;
    @FXML
    private TableColumn<Events, Void> editButton;
    @FXML
    private TableColumn<Events, String> tblEventStartTime;
    @FXML
    private TableColumn<Events, String> tblEventEndTime;

    @FXML
    private TextField txtTopCoorEventPage;

    private FilteredList<Events> filteredEvents;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private final EventsModel eventsModel;


    public CoordinatorEventPageController() throws IOException, ApplicationWideException {
        eventsModel = new EventsModel();

    }

    @FXML
    public void goToCreateNewEvent(ActionEvent actionEvent) {
        CoordinatorFrameController.getInstance().openPageCoordinatorCreateEventPage();
    }

    @FXML
    public void initialize() {

        int coordinatorID = UserContext.getInstance().getCurrentUserId();
        initializeColumns(coordinatorID);

        initializeEditButtonColumn();
        setupSearchFunctionality();
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

    private void initializeColumns(int coordinatorID) {
        try {

            tblEventTableCode.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getEventID())));
            tblEventTableEventName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEventName()));

            // Convert the date from yy/mm/dd to dd/mm/yy
            tblEventTableStartDate.setCellValueFactory(cellData -> {
                LocalDate eventDate = cellData.getValue().getEventDate();
                String formattedDate = eventDate.format(DATE_FORMATTER);
                return new SimpleStringProperty(formattedDate);
            });

            tblEventTableStatus.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getEventStatus())));

            // Calculate days left
            tblEventTableDaysLeft.setCellValueFactory(cellData -> {
                LocalDate currentDate = LocalDate.now();
                LocalDate eventDate = cellData.getValue().getEventDate();
                long daysLeft = ChronoUnit.DAYS.between(currentDate, eventDate);
                return new SimpleIntegerProperty((int) daysLeft).asObject();
            });

            tblEventTableRegisteredParticipants.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getEventParticipants()).asObject());
            tblEventStartTime.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEventStartTime()));
            tblEventEndTime.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEventEndTime()));

            tblEventTable.setItems(eventsModel.getUserEvents());
        } catch (ApplicationWideException e) {
            showAlert("Error", "Failed to retrieve events by coordinator from the database.");
            e.printStackTrace();
        }
    }

    private void setupSearchFunctionality() {
        filteredEvents = new FilteredList<>(eventsModel.getEventList(), p -> true);

        txtTopCoorEventPage.textProperty().addListener((observable, oldValue, newValue) ->
                filteredEvents.setPredicate(event -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();

                    boolean nameMatches = event.getEventName().toLowerCase().contains(lowerCaseFilter);

                    String dateString = event.getEventDate().format(DATE_FORMATTER);
                    boolean dateMatches = dateString.contains(lowerCaseFilter);

                    return nameMatches || dateMatches;
                })
        );

        SortedList<Events> sortedEvents = new SortedList<>(filteredEvents);
        sortedEvents.comparatorProperty().bind(tblEventTable.comparatorProperty());
        tblEventTable.setItems(sortedEvents);
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void refreshEventData() {
        int coordinatorID = UserContext.getInstance().getCurrentUserId();
        initializeColumns(coordinatorID);

    }

    @Override
    public void setModel(UsersModel usersModel) {

    }
}
