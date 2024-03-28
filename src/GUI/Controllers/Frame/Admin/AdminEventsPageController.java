package GUI.Controllers.Frame.Admin;

import BE.Events;
import CostumException.ApplicationWideException;
import GUI.Controllers.IController;
import GUI.Model.EventsModel;
import GUI.Model.UsersModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
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
            colAdminStartDate.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().getEventDate()));
            colAdminDaysLeft.setCellValueFactory(cellData ->
                    new SimpleStringProperty(String.valueOf(cellData.getValue().getEventRemainingDays())));
            colAdminEventRegistered.setCellValueFactory(cellData ->
                    new SimpleStringProperty(String.valueOf(cellData.getValue().getEventParticipants())));

            // Refresh table view to reflect changes
            tblViewAdminEvents.refresh();
        }
    }


    @Override
    public void setModel(UsersModel usersModel) {
        this.usersModel = usersModel;
    }
}
