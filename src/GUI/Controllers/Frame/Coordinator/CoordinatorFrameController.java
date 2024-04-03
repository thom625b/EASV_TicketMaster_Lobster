package GUI.Controllers.Frame.Coordinator;

import BE.Events;
import CostumException.ApplicationWideException;
import GUI.Controllers.IController;
import GUI.Model.UsersModel;
import GUI.Utility.UserContext;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.Stack;

public class CoordinatorFrameController implements Initializable, IController {



    private static CoordinatorFrameController instance;
    private Stack<Node> pageHistory = new Stack<>();
    private UsersModel usersModel;
    @FXML
    private StackPane coorStackPane;

    // Define the FXML file path here
    private final String CREATE_EVENTS_WINDOW_FXML = "/fxml/Coordinator/EventControllers/CoordinatorCreateEventsWindow.fxml";
    private final String EDIT_EVENT_WINDOW_FXML = "fxml/Coordinator/EventControllers/CoordinatorEditEventWindow.fxml";

    private static ObjectProperty<Image> imageObjectProperty = new SimpleObjectProperty<>();
    private static ObjectProperty <Image> newimageObjectProperty = new SimpleObjectProperty<>();
    private String newUserPicturePath;

    @FXML
    private ImageView imgNewUserPictureCoordinator = new ImageView();
    @FXML
    private ImageView imgCoordinatorFrame = new ImageView();


    @FXML
    private void homeScreenWindow() throws IOException {
        loadpage("FXML/Coordinator/CoordinatorHomePage");
    }

    public static CoordinatorFrameController getInstance() {
        return instance;
    }

    public void loadpage(String page) throws IOException {
        URL url = getClass().getResource(page + ".fxml");
        if (url == null) {
            throw new IOException("FXML file not found: " + page);
        }
        Parent root = FXMLLoader.load(url);

        if (!coorStackPane.getChildren().isEmpty()) {
            pageHistory.push(coorStackPane.getChildren().get(0));
        }

        setCenterNode(root);
    }

    public void setCenterNode(Node node) {
        coorStackPane.getChildren().clear();
        coorStackPane.getChildren().add(node);
        StackPane.setAlignment(node, javafx.geometry.Pos.CENTER);
    }

    public void goBack() {
        if (!pageHistory.isEmpty()) {
            System.out.println("Going back...");
            Node previousNode = pageHistory.pop();
            coorStackPane.getChildren().setAll(previousNode);
        } else {
            System.out.println("No history to go back to.");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        try {
            loadpage("/fxml/Coordinator/CoordinatorHomePage");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


    public void setModel(UsersModel usersModel) {
        this.usersModel = usersModel;
    }



    public void btnGoToEventsPage(ActionEvent actionEvent) {
        try {
            loadpage("/fxml/Coordinator/EventControllers/CoordinatorEventPage");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    @FXML
    private void goToUserHome(ActionEvent actionEvent) {
        try {
            loadpage("/fxml/Coordinator/EventControllers/CoordinatorEventPage");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void goToCateringTickets(ActionEvent actionEvent) {
        try {
            loadpage("/fxml/Coordinator/CoordinatorTickets");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void goToUserManageEvents(ActionEvent actionEvent) {
        try {
            loadpage("/fxml/Coordinator/CoordinatorManageEvents");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void openPageCoordinatorCreateEventPage() {
        try {
            URL url = getClass().getResource(CREATE_EVENTS_WINDOW_FXML);
            if (url == null) {
                throw new IOException("FXML file not found: " + CREATE_EVENTS_WINDOW_FXML);
            }
            Parent root = FXMLLoader.load(url);

            if (!coorStackPane.getChildren().isEmpty()) {
                pageHistory.push(coorStackPane.getChildren().get(0));
            }

            setCenterNode(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void openEditEventPage(Events selectedEvent) {
        try {
            URL url = getClass().getClassLoader().getResource(EDIT_EVENT_WINDOW_FXML);
            if (url == null) {
                throw new IOException("FXML file not found: " + EDIT_EVENT_WINDOW_FXML);
            }
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();

            // Pass the selected event details to the controller of the loaded FXML file
            CoordinatorEditEventPageController editEventController = loader.getController();

            // Initialize the edit event page with the selected event details
            editEventController.initData(selectedEvent);

            if (!coorStackPane.getChildren().isEmpty()) {
                pageHistory.push(coorStackPane.getChildren().get(0));
            }

            setCenterNode(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void transitionToNewScene(Parent root) {
        if (!coorStackPane.getChildren().isEmpty()) {
            pageHistory.push(coorStackPane.getChildren().get(0));
        }
        setCenterNode(root);
    }
    public void closeWindow(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void openChangeProfilePictureCoordinator(MouseEvent mouseEvent) {

    }

    @FXML
    private void loadNewUserPicture(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose profile picture");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("pictures", "*.png", "*.jpg", "*.jpeg")
        );

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            try {
                String userId = String.valueOf(UserContext.getInstance().getCurrentUserId());
                String userDirectoryPath = System.getProperty("user.dir") + "/resources/Data/profile_images/user" + userId;
                File userDirectory = new File(userDirectoryPath);

                if (!userDirectory.exists()) {
                    boolean isDirectoryCreated = userDirectory.mkdirs();
                    if (!isDirectoryCreated) {
                        throw new IOException("Could not create directory: " + userDirectoryPath);
                    }
                }
                String fileName = selectedFile.getName();

                if (!Files.exists(selectedFile.toPath())) {
                    File destinationFile = new File(userDirectoryPath, newUserPicturePath);
                    Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }

                newimageObjectProperty.set(new Image(selectedFile.toURI().toString()));
                newUserPicturePath = fileName;  // Storing only the file name for database reference

            } catch (IOException ex) {

                ex.printStackTrace();//TODO
                // Handle any IO errors here
            }
        }


    }

    private File selectedFile;

    @FXML
    private void insertNewUserPicture(ActionEvent actionEvent) {
        if (newUserPicturePath != null && !newUserPicturePath.isEmpty() && usersModel != null) {
            try {
                int userId = UserContext.getInstance().getCurrentUserId();
                usersModel.updateUserImage(userId, newUserPicturePath);
                setUserPictureFromStart();

                //copyImageToDir(selectedFile.toURI().toString(),userDirectoryPath, newUserPicturePath);
                showAlert(Alert.AlertType.INFORMATION, "Update Successful", null, "Profile picture updated successfully.");
            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "Update Failed", "Failed to update the profile picture.");
            }
        } else {
            if (newUserPicturePath == null || newUserPicturePath.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "No File Selected", null, "Please select a file to update.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", null, "UsersModel is not initialized.");
            }
        }


    }



    private void setUserPictureFromStart() {


        try {
            // if (imgProfilePictureAdmin != null) {


            String imageName = usersModel.getCurrentUserImageName();

            if (imageName != null && !imageName.isEmpty()) { // Check if user has a profile picture
                String imagePath = "/resources/Data/profile_images/user" + UserContext.getInstance().getCurrentUserId() + "/" + imageName;
                File imageFile = new File(System.getProperty("user.dir") + imagePath);

                if (imageFile.exists()) {
                    Image image = new Image(new FileInputStream(imageFile));


                    // Ensure that ImageView is fully loaded
                    Platform.runLater(() -> {
                        imageObjectProperty.set(image);
                    });
                } else {
                    System.out.println("User image file not found: " + imagePath);
                }
            } else {
                System.out.println("User does not have a profile picture.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle any exceptions silently (without showing an error message)
        }
    /*
    }


        else {
            System.out.println("imgProfilePictureAdmin is not initialized.");
        }
        */
    }
    private void showAlert(Alert.AlertType alertType, String title, String header, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
