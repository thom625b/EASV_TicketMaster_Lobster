package GUI.Controllers.Frame.Admin;

import GUI.Model.UsersModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;

import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;

public class AdminFrameController implements Initializable {

    @FXML
    private StackPane adminStackPane;

    private static AdminFrameController instance;
    private Stack<Node> pageHistory = new Stack<>();
    private UsersModel usersModel;

    @FXML
    private Button btnadminCoordinator, btnadminLogout, btnadminEvents, btnadminHome;


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
        Parent root = FXMLLoader.load(url);

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
        try {
            loadpage("/fxml/Admin/AdminHomePage");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


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
}
