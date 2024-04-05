package GUI.Controllers.Frame.Admin;

import BE.Events;
import CostumException.ApplicationWideException;
import GUI.Controllers.IController;
import GUI.Model.EventsModel;
import GUI.Model.UsersModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminEventsPageController implements Initializable, IController {
    @FXML
    private BorderPane BPAdminManageCoor;
    private UsersModel usersModel;
    private EventsModel eventsModel;
    @FXML
    private TableColumn<Events,String> colAdminEventId;
    @FXML
    private TableColumn<Events,String> colEventAdmin;
    @FXML
    private TableColumn<Events,String> colAdminStartDate;
    @FXML
    private TableColumn<Events,Void> colAdminStatus;
    @FXML
    private TableColumn<Events,String> colAdminEventRegistered;
    @FXML
    private TableColumn<Events,String> colAdminDaysLeft;
    @FXML
    private TableColumn<Events,Void> coladminEventsButtons;
    @FXML
    private Button btnRefreshAdminEvents;
    @FXML
    private TableView<Events> tblViewAdminEvents;
    @FXML
    private ComboBox comboAdminManageName;
    @FXML
    private TextField txtSearchAdminEvents;

    private FilteredList<Events> filteredEvents;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            eventsModel = new EventsModel();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ApplicationWideException e) {
            throw new RuntimeException(e);
        }

        showAllEventsInTable();
        initializeDeleteButtonColumn();
        setupSearchFunctionality();
    }


    private void initializeDeleteButtonColumn() {
        coladminEventsButtons.setCellFactory(param -> new TableCell<Events, Void>() {
            private final Button deleteButton = createButton("/pictures/delete.png");

            {
                deleteButton.setOnAction(event -> {
                    Events eventToDelete = getTableView().getItems().get(getIndex());
                    eventToDelete.getEventID();


                    if (showConfirmationDialog()) {
                        try {
                            eventsModel.deleteEvent(eventToDelete);

                            refreshEventsTable();

                        } catch (ApplicationWideException e) {
                            e.printStackTrace(); // TODO
                        }
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getIndex() >= getTableView().getItems().size()) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });
    }

    private boolean showConfirmationDialog() {
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirm Deletion");
        confirmationDialog.setHeaderText("Delete Event");
        confirmationDialog.setContentText("Are you sure you want to delete this event? This action cannot be undone.");

        // Optional: Customizing the buttons
        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirmationDialog.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = confirmationDialog.showAndWait();
        return result.isPresent() && result.get() == buttonTypeYes;
    }
    public void refreshEventsTable() {
        try {
            eventsModel.loadEvents();
            tblViewAdminEvents.setItems(eventsModel.getEventList());
        } catch (ApplicationWideException e) {
            e.printStackTrace();
        }
    }

    private Button createButton(String imagePath) {
        Button button = new Button();
        Image img = new Image(getClass().getResourceAsStream(imagePath));
        button.setGraphic(new ImageView(img));
        button.setStyle("-fx-background-color: transparent;");
        return button;
    }
    public void showAllEventsInTable() {
        if (eventsModel != null) {
            tblViewAdminEvents.setItems(eventsModel.getEventList());
            colAdminEventId.setCellValueFactory(cellData ->
                    new SimpleStringProperty(String.valueOf(cellData.getValue().getEventID())));
            colEventAdmin.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().getEventName()));
            colAdminStartDate.setCellValueFactory(cellData -> {
                LocalDate eventDate = cellData.getValue().getEventDate();
                String formattedDate = eventDate.format(DATE_FORMATTER);
                return new SimpleStringProperty(formattedDate);
            });

            // Calculate and update days left
            colAdminDaysLeft.setCellValueFactory(cellData -> {
                LocalDate currentDate = LocalDate.now();
                LocalDate eventDate = cellData.getValue().getEventDate();
                long daysLeft = ChronoUnit.DAYS.between(currentDate, eventDate);
                return new SimpleStringProperty(String.valueOf(daysLeft));
            });

            colAdminEventRegistered.setCellValueFactory(cellData ->
                    new SimpleStringProperty(String.valueOf(cellData.getValue().getEventParticipants())));

            // Refresh table view to reflect changes
            tblViewAdminEvents.refresh();
        }
    }

    private void setupSearchFunctionality() {
        filteredEvents = new FilteredList<>(eventsModel.getEventList(), p -> true); // Initially show all events

        txtSearchAdminEvents.textProperty().addListener((observable, oldValue, newValue) ->
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
        sortedEvents.comparatorProperty().bind(tblViewAdminEvents.comparatorProperty());
        tblViewAdminEvents.setItems(sortedEvents);
    }


    @Override
    public void setModel(UsersModel usersModel) {
        this.usersModel = usersModel;
    }
}
