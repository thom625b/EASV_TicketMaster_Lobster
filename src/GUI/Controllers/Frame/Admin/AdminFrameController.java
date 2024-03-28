package GUI.Controllers.Frame.Admin;

import BE.Users;

import CostumException.ApplicationWideException;
import GUI.Controllers.IController;
import GUI.Model.EventsModel;
import GUI.Model.UsersModel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Stack;

public class AdminFrameController implements Initializable {

    @FXML
    private StackPane adminStackPane;

    private static AdminFrameController instance;
    private Stack<Node> pageHistory = new Stack<>();
    private UsersModel usersModel;

    private EventsModel eventsModel;
    private Users currentUser;
    @FXML
    private Button btnadminCoordinator, btnadminLogout, btnadminEvents, btnadminHome;
    @FXML
    private Button btnadminManageEvents;

    private final String UPDATE_COORDINATOR_WINDOW_FXML = "/fxml/Admin/AdminUpdateCoordinator.fxml";
    @FXML
    private ImageView imgProfilePictureAdmin;
    @FXML
    private ImageView imgNewUserPictureAdmin;

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


    public void setModel(UsersModel usersModel) {
        this.usersModel = usersModel;

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
    private void goToAdminEvents(ActionEvent actionEvent)  {
        try {
            loadpage("/fxml/Admin/AdminEventsPage");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void goToAdminCoordinators(ActionEvent actionEvent) {
        try {
            loadpage("/fxml/Admin/AdminCoordinatorPage");
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
    private void openChangeProfilePicture(MouseEvent mouseEvent ) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Admin/AdminCreatePicturePage.fxml"));
            Parent root = loader.load();

            transitionToNewScene(root);
        } catch (IOException e) {
            throw new RuntimeException("Failed to open the profile picture change window", e);
        }
    }


    @FXML
    private void loadNewUserPicture(ActionEvent actionEvent) {
    }

    @FXML
    private void insertNewUserPicture(ActionEvent actionEvent) {
    }
}
