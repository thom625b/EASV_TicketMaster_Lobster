package GUI.Controllers.Frame.Admin;

import BE.Users;

import CostumException.ApplicationWideException;
import GUI.Controllers.IController;
import GUI.Model.EventsModel;
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
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import java.util.Stack;

public class AdminFrameController implements Initializable, IController {

    @FXML
    private StackPane adminStackPane;

    private static AdminFrameController instance;
    private Stack<Node> pageHistory = new Stack<>();
    private UsersModel usersModel;

    private EventsModel eventsModel;
    private Users currentUser;
    @FXML
    private Button btnadminUsers, btnadminLogout, btnadminEvents, btnadminHome;
    @FXML
    private Button btnadminManageEvents, btnLoadPicture;

    private final String UPDATE_COORDINATOR_WINDOW_FXML = "/fxml/Admin/AdminUpdateCoordinator.fxml";

    private static ObjectProperty <Image> imageObjectProperty = new SimpleObjectProperty<>();
    private static ObjectProperty <Image> newimageObjectProperty = new SimpleObjectProperty<>();

    @FXML
    private ImageView imgProfilePictureAdmin = new ImageView();
    @FXML
    private ImageView imgNewUserPictureAdmin = new ImageView();

    private String newUserPicturePath;


    @FXML
    private void homeScreenWindow() throws IOException {
        loadpage("/fxml/Admin/AdminFrontPage");
    }


    public static AdminFrameController getInstance() {
        return instance;
    }

    public void loadpage(String page) throws IOException {
        URL url = getClass().getResource(page + ".fxml");
        if (url == null) {
            throw new IOException("FXML file not found: " + page);
        }
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent root = fxmlLoader.load();

        IController controller = fxmlLoader.getController();

        if (usersModel != null) {
            try {
                controller.setModel(usersModel);
            } catch (ApplicationWideException e) {
                throw new RuntimeException(e);
            }

        }


        if (!adminStackPane.getChildren().isEmpty()) {
            pageHistory.push(adminStackPane.getChildren().get(0));
        }

        setCenterNode(root);
    }

    public void setCenterNode(Node node) {
        adminStackPane.getChildren().clear();
        adminStackPane.getChildren().add(node);
        StackPane.setAlignment(node, javafx.geometry.Pos.CENTER);
    }

    public void goBack() {
        if (!pageHistory.isEmpty()) {
            System.out.println("Going back...");
            Node previousNode = pageHistory.pop();
            adminStackPane.getChildren().setAll(previousNode);
        } else {
            System.out.println("No history to go back to.");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;

    }


    public void setModel(UsersModel usersModel)  {

        this.usersModel = usersModel;

        setUserPictureFromStart();
        imgProfilePictureAdmin.imageProperty().bind(imageObjectProperty);
        imgNewUserPictureAdmin.imageProperty().bind(newimageObjectProperty);
        double radius = Math.min(imgProfilePictureAdmin.getFitWidth(), imgProfilePictureAdmin.getFitHeight()) / 2;
        Circle clip = new Circle(radius, radius, radius);
        imgProfilePictureAdmin.setClip(clip);
    }


    @FXML
    private void goToAdminHome(ActionEvent actionEvent) {
        try {
            loadpage("/fxml/Admin/AdminHomePage");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    private void goToAdminManageEvents(ActionEvent actionEvent) {
        try {
            loadpage("/fxml/Admin/AdminManageEvents");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    private void goToAdminEvents(ActionEvent actionEvent) {
        try {
            loadpage("/fxml/Admin/AdminEventsPage");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void goToAdminUsers(ActionEvent actionEvent) {
        try {
            loadpage("/fxml/Admin/AdminUsersPage");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadAdminUpdateCoordinatorPage(Users user) {
        try {
            URL url = getClass().getResource(UPDATE_COORDINATOR_WINDOW_FXML);
            if (url == null) {
                throw new IOException("FXML file not found: " + UPDATE_COORDINATOR_WINDOW_FXML);
            }
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();

            if (user != null) {
                AdminUpdateCoordinatorController controller = loader.getController();
                controller.setUser(user);
                controller.setModel(usersModel);

            }

            transitionToNewScene(root);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load the Admin Update Coordinator page", e);
        }
    }

    private void transitionToNewScene(Parent root) {
        if (!adminStackPane.getChildren().isEmpty()) {
            pageHistory.push(adminStackPane.getChildren().get(0));
        }
        setCenterNode(root);
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


    @FXML
    private void openChangeProfilePicture(MouseEvent mouseEvent) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Admin/AdminCreatePicturePageAdmin.fxml"));
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
    public static void copyImageToDir(String imagePath, String destDir, String imageName) throws IOException {
        if (imagePath.startsWith("file:/")) {
            imagePath = imagePath.substring(6);
        }

        Path sourcePath = Path.of(imagePath);
        System.out.println("sourceFile: " + sourcePath.getFileName());

        if (!Files.exists(sourcePath)) throw new FileNotFoundException("Source file not found: " + imagePath);

        Path dirPath = Path.of(destDir);
        if (!Files.exists(dirPath)) Files.createDirectories(dirPath);

        Path outputPath = dirPath.resolve(imageName);

        Files.copy(sourcePath, outputPath, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("Image copied to: " + outputPath);
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

    /*
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

                if (!userDirectory.exists() && userDirectory.mkdirs()) {
                   throw new IOException("Could not create directory: " + userDirectoryPath);
                }
                //String fileName = selectedFile.getName();

                if (!Files.exists(selectedFile.toPath())) {
                    File destinationFile = new File(userDirectoryPath, newUserPicturePath);
                    Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }



                File destinationFile = new File(userDirectory, selectedFile.getName());
                Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);


                newimageObjectProperty.set(new Image(selectedFile.toURI().toString()));
                newUserPicturePath = selectedFile.getName(); // Storing only the file name for database reference

            } catch (IOException ex) {

                ex.printStackTrace();//TODO
                // Handle any IO errors here
            }
        }
    }

     */


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
