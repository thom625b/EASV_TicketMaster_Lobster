package GUI.Controllers.Frame.Admin;


import BE.Events;
import BE.Users;
import CostumException.ApplicationWideException;
import GUI.Controllers.Frame.Coordinator.CoordinatorFrameController;
import GUI.Controllers.IController;
import GUI.Model.EventsModel;
import GUI.Model.UsersModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
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
    @FXML
    private ImageView imgFromCombBox;
    @FXML
    private Label lblErrorText;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            eventsModel = new EventsModel();
            setupEventComboBox();
            initializeImageView();
            initializeValidationListeners();
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


        comboAdminManageName.valueProperty().addListener((obs, oldVal, newVal) -> {
            String defaultImagePath = "/pictures/Customer.png";
            URL defaultImageUrl = getClass().getResource(defaultImagePath);
            Image defaultImage = new Image(defaultImageUrl.toExternalForm(), true);

            if (newVal != null) {
                try {
                    String userImagePath = usersModel.getUserImageName(newVal.getUserId());
                    if (userImagePath != null && !userImagePath.isEmpty()) {
                        String imagePath = "/Data/profile_images/user" + newVal.getUserId() + "/" + userImagePath;
                        URL imageUrl = getClass().getResource(imagePath);
                        if (imageUrl != null) {
                            updateImage(imageUrl);
                        } else {
                            updateImage(defaultImageUrl);
                        }
                    } else {
                        updateImage(defaultImageUrl);
                    }
                } catch (ApplicationWideException e) {

                    updateImage(defaultImageUrl);
                }
            } else {
                updateImage(defaultImageUrl);
            }
        });

    }


    private void initializeImageView() {
        // Set fixed size for the ImageView
        imgFromCombBox.setFitWidth(160); // Adjust the width as needed
        imgFromCombBox.setFitHeight(160); // Adjust the height as needed

        // Set clip for rounded corners
        Rectangle clip = new Rectangle(
                imgFromCombBox.getFitWidth(), imgFromCombBox.getFitHeight()
        );
        clip.setArcWidth(20); // Corner curve radius
        clip.setArcHeight(20); // Corner curve radius
        imgFromCombBox.setClip(clip);
    }

    private void updateImage(URL imageUrl) {
        if (imageUrl != null) {
            Image userImage = new Image(imageUrl.toExternalForm(), true); // true to preserve ratio
            imgFromCombBox.setImage(userImage);
        }
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
    private void addCoordinatorToEvents(ActionEvent actionEvent) {
        boolean isCoordinatorValid = comboAdminManageName.getSelectionModel().getSelectedItem() != null;
        boolean isEventValid = comboAdminManageEvent.getSelectionModel().getSelectedItem() != null;

        validateComboBox(comboAdminManageName, isCoordinatorValid);
        validateComboBox(comboAdminManageEvent, isEventValid);

        if (!isCoordinatorValid || !isEventValid) {
            updateMessageDisplay("Please select both a coordinator and an event.", true);
            return;
        }

        try {
            int coordinatorId = comboAdminManageName.getSelectionModel().getSelectedItem().getUserId();
            int eventId = comboAdminManageEvent.getSelectionModel().getSelectedItem().getEventID();
            eventsModel.addCoordinatorToEvents(coordinatorId, eventId);
            updateMessageDisplay("Coordinator has been added to the event successfully.", false);
        } catch (ApplicationWideException e) {
            updateMessageDisplay("Failed to add coordinator: " + e.getMessage(), true);
        }
    }
    private void validateComboBox(ComboBox<?> comboBox, boolean isValid) {
        if (!isValid) {
            comboBox.setStyle("-fx-border-color: red; -fx-border-width: 1px; -fx-border-radius: 15px;");
        } else {
            comboBox.setStyle(""); // Clear the style if the value becomes valid
        }
    }

    private void initializeValidationListeners() {
        // Listener for the coordinator ComboBox
        comboAdminManageName.valueProperty().addListener((obs, oldVal, newVal) ->
                validateComboBox(comboAdminManageName, newVal != null));

        // Listener for the event ComboBox
        comboAdminManageEvent.valueProperty().addListener((obs, oldVal, newVal) ->
                validateComboBox(comboAdminManageEvent, newVal != null));
    }

    private void updateMessageDisplay(String message, boolean isError) {
        Platform.runLater(() -> {
            lblErrorText.setText(message);
            if (isError) {
                lblErrorText.setStyle("-fx-text-fill: red; -fx-font-size: 10px;");
            } else {
                lblErrorText.setStyle("-fx-text-fill: green; -fx-font-size: 10px;");
            }

            // Clear the message after 3 seconds
            new Timeline(new KeyFrame(
                    Duration.seconds(3),
                    ae -> lblErrorText.setText("")
            )).play();
        });
    }

    @FXML
    private void goToFrontPage(ActionEvent actionEvent) {
        try {

            AdminFrameController.getInstance().loadpage("/fxml/Admin/AdminHomePage");
        } catch (IOException e) {
            // Handle IOException show an error dialog)
            e.printStackTrace(); //TODO
        }
    }
}