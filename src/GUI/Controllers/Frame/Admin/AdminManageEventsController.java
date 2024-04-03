package GUI.Controllers.Frame.Admin;


import BE.Events;
import BE.Users;
import CostumException.ApplicationWideException;
import GUI.Controllers.IController;
import GUI.Model.EventsModel;
import GUI.Model.UsersModel;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminManageEventsController implements IController, Initializable {
    private EventsModel eventsModel;
    private UsersModel usersModel;
    @FXML
    private ComboBox<Users> comboAdminManageName;
    @FXML
    private ComboBox<Events> comboAdminManageEvent;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            eventsModel = new EventsModel();
            setupEventComboBox();

        } catch (IOException | ApplicationWideException e) {
            throw new RuntimeException("Failed to initialize EventsModel", e);
        }
    }
    private void setupEventComboBox() {

        populateComboAdminManageEvent();

        comboAdminManageEvent.setConverter(new StringConverter<Events>() {
            @Override
            public String toString(Events event) {
                return event == null ? "" : event.getEventName()+ " " +  event.getEventDate();
            }

            @Override
            public Events fromString(String string) {
                return comboAdminManageEvent.getItems().stream().filter(event ->
                        event.getEventName().equals(string)).findFirst().orElse(null);
            }
        });
    }

    private void setupNameComboBox() {
        populateComboAdminManageName();

        comboAdminManageName.setConverter(new StringConverter<Users>() {
            @Override
            public String toString(Users user) {
                return user == null ? "" : user.getFirstName() + " " + user.getLastName();
            }

            @Override
            public Users fromString(String string) {
                return comboAdminManageName.getItems().stream()
                        .filter(user -> (user.getFirstName() + " " + user.getLastName()).equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });
    }

    private void populateComboAdminManageName() {
        usersModel.getObservableUsers().stream()
                .filter(user -> "COORDINATOR".equalsIgnoreCase(String.valueOf(user.getRole())))
                .forEach(user -> comboAdminManageName.getItems().add(user));
    }

    private void populateComboAdminManageEvent() {
        eventsModel.getEventList().forEach(event ->
                comboAdminManageEvent.getItems().add(event)
        );
    }

    @Override
    public void setModel(UsersModel usersModel)  {

        this.usersModel = usersModel;

        setupNameComboBox();
    }

    @FXML
    private void closeManageEventPage(ActionEvent actionEvent) {
        try {

            AdminFrameController.getInstance().loadpage("/fxml/Admin/AdminHomePage");
        } catch (IOException e) {
            // Handle IOException show an error dialog)
            e.printStackTrace(); //TODO
        }
    }

    @FXML
    private void addCoordinatorToEvents(ActionEvent actionEvent) {
        Users selectedCoordinator = comboAdminManageName.getSelectionModel().getSelectedItem();
        Events selectedEvent = comboAdminManageEvent.getSelectionModel().getSelectedItem();

        if (selectedCoordinator == null || selectedEvent == null) {
            showAlert("Selection Required", "Please select both a coordinator and an event.");
            return;
        }

        int coordinatorId = selectedCoordinator.getUserId();
        int eventId = selectedEvent.getEventID();


        try {
            eventsModel.addCoordinatorToEvents(coordinatorId, eventId);
        } catch (ApplicationWideException e) {
            throw new RuntimeException(e); //TODO
        }
        // Optionally, show a success message to the user
        showAlert("Success", "Coordinator has been added to the event successfully.");
    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        alert.getDialogPane().getButtonTypes().setAll(okButton);

        alert.showAndWait();
    }
}