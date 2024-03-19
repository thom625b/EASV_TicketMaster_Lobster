package GUI.Controllers.Frame.Admin;

import GUI.Model.UsersModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;

import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;

public class AdminFrameController implements Initializable {

    @FXML
    private StackPane stackPaneFrameAdmin;

    private static AdminFrameController instance;
    private Stack<Node> pageHistory = new Stack<>();
    private UsersModel usersModel;



    @FXML
    private void homeScreenWindow() throws IOException {
        loadpage("/fxml/Admin/AdminFrontPage.fxml");
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

        if (!stackPaneFrameAdmin.getChildren().isEmpty()) {
            pageHistory.push(stackPaneFrameAdmin.getChildren().get(0));
        }

        setCenterNode(root);
    }

    public void setCenterNode(Node node) {
        stackPaneFrameAdmin.getChildren().clear();
        stackPaneFrameAdmin.getChildren().add(node);
        StackPane.setAlignment(node, javafx.geometry.Pos.CENTER);
    }

    public void goBack() {
        if (!pageHistory.isEmpty()) {
            System.out.println("Going back...");
            Node previousNode = pageHistory.pop();
            stackPaneFrameAdmin.getChildren().setAll(previousNode);
        } else {
            System.out.println("No history to go back to.");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        try {
            loadpage("/fxml/Admin/AdminFrontPage");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


    public void setModel(UsersModel usersModel) {
        this.usersModel = usersModel;
    }
}
