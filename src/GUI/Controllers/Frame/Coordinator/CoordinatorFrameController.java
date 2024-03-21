package GUI.Controllers.Frame.Coordinator;

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

public class CoordinatorFrameController implements Initializable {



    private static CoordinatorFrameController instance;
    private Stack<Node> pageHistory = new Stack<>();
    private UsersModel usersModel;
    @FXML
    private StackPane coorStackPane;


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
            loadpage("/fxml/Coordinator/CoordinatorEventPage");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void goToUserHome(ActionEvent actionEvent) {
        try {
            loadpage("/fxml/Coordinator/CoordinatorHomePage");
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

}
