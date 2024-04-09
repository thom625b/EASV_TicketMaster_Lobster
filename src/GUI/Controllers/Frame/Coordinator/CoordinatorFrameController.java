package GUI.Controllers.Frame.Coordinator;

import BE.Costumers;
import BE.Events;
import CostumException.ApplicationWideException;
import GUI.Controllers.IController;
import GUI.Model.CustomersModel;
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

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.FileChannel;
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
    private final String UPDATE_CUSTOMER_WINDOW_FXML = "/fxml/Coordinator/CustomerUpdateView.fxml";

    private static ObjectProperty <Image> imageObjectProperty = new SimpleObjectProperty<>();
    private static ObjectProperty <Image> newimageObjectProperty = new SimpleObjectProperty<>();
    @FXML
    private ImageView imgNewUserPictureCoordinator = new ImageView();
    @FXML
    private ImageView imgCoordinatorFrame = new ImageView();
    private File selectedFile;
    private String newUserPicturePath;

    private CustomersModel customerModel;

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


    }


    public void setModel(UsersModel usersModel) {
        this.usersModel = usersModel;
        setUserPictureFromStart();
        imgCoordinatorFrame.imageProperty().bind(imageObjectProperty);
        imgNewUserPictureCoordinator.imageProperty().bind(newimageObjectProperty);
        double radius = Math.min(imgCoordinatorFrame.getFitWidth(), imgCoordinatorFrame.getFitHeight()) / 2;
        Circle clip = new Circle(radius, radius, radius);
        imgCoordinatorFrame.setClip(clip);
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
            loadpage("/fxml/Coordinator/CoordinatorTickets");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void goToCustomers(ActionEvent actionEvent) {
        try {
            loadpage("/fxml/Coordinator/CustomerTablePage");
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


    public void closeWindow(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }



    public void loadAdminUpdateCustomer(Costumers costumer) {
        try {
            URL url = getClass().getResource(UPDATE_CUSTOMER_WINDOW_FXML);
            if (url == null) {
                throw new IOException("FXML file not found: " + UPDATE_CUSTOMER_WINDOW_FXML);
            }
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();

            CustomerUpdateController controller = loader.getController();
            controller.setCurrentCustomer(costumer);

            transitionToNewScene(root);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load the Admin Update Coordinator page", e);
        }
    }



    @FXML
    private void loadNewUserPicture(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose profile picture");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("pictures", "*.png", "*.jpg", "*.jpeg")
        );

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            try {
                String userId = String.valueOf(UserContext.getInstance().getCurrentUserId());
                String userDirectoryPath = System.getProperty("user.dir") + "/resources/Data/profile_images/user" + userId;
                File userDirectory = new File(userDirectoryPath);

                if (!userDirectory.exists() && !userDirectory.mkdirs()) {
                    throw new IOException("Could not create directory: " + userDirectoryPath);
                }

                File destinationFile = new File(userDirectory, selectedFile.getName());
                copyFileWithChannel(selectedFile, destinationFile); // Using the method to copy file

                newimageObjectProperty.set(new Image(destinationFile.toURI().toString()));
                newUserPicturePath = selectedFile.getName(); // Storing only the file name for database reference

            } catch (IOException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to update the profile picture", ex.getMessage());
                ex.printStackTrace();
            }
        }

    }
    private void copyFileWithChannel(File source, File destination) throws IOException {
        try (FileChannel sourceChannel = new FileInputStream(source).getChannel();
             FileChannel destChannel = new FileOutputStream(destination, true).getChannel()) {
            destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
        } catch (IOException e) {
            throw new IOException("Error copying file: " + source + " to " + destination, e);
        }
    }


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

    }
    private void transitionToNewScene(Parent root) {
        if (!coorStackPane.getChildren().isEmpty()) {
            pageHistory.push(coorStackPane.getChildren().get(0));
        }
        setCenterNode(root);
    }
    @FXML
    private void openCoordinatorPictureChange(MouseEvent mouseEvent) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Coordinator/CreatePicturePageCoordinator.fxml"));
            Parent root = loader.load();
            IController controller = loader.getController();
            controller.setModel(usersModel);
            transitionToNewScene(root);
        } catch (IOException e) {
            throw new RuntimeException("Failed to open the profile picture change window", e);
        } catch (ApplicationWideException e) {
            throw new RuntimeException(e);
        }
    }


    private void showAlert(Alert.AlertType alertType, String title, String header, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void logoutToLogin(ActionEvent actionEvent) {
        try {
            URL loginPageURL = getClass().getResource("/fxml/Login.fxml");
            if (loginPageURL == null) {
                throw new IllegalArgumentException("Cannot find resource Login.fxml");
            }
            Parent loginRoot = FXMLLoader.load(loginPageURL);
            Scene loginScene = new Scene(loginRoot);
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.setScene(loginScene);
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            // TODO
        }
    }
}
