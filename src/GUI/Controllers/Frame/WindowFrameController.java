package GUI.Controllers.Frame;

import GUI.Model.AdminModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.function.Consumer;

public class WindowFrameController implements Initializable {

    @FXML
    private StackPane stackPaneFrame;

    private static WindowFrameController instance;
    private Stack<Node> pageHistory = new Stack<>();
    private AdminModel adminModel;



    @FXML
    private void homeScreenWindow() throws IOException {
        loadpage("/fxml/FrontPage.fxml");
    }

    public static WindowFrameController getInstance() {
        return instance;
    }

    public void loadpage(String page) throws IOException {
        URL url = getClass().getResource(page + ".fxml");
        if (url == null) {
            throw new IOException("FXML file not found: " + page);
        }
        Parent root = FXMLLoader.load(url);

        if (!stackPaneFrame.getChildren().isEmpty()) {
            pageHistory.push(stackPaneFrame.getChildren().get(0));
        }

        setCenterNode(root);
    }

    public void setCenterNode(Node node) {
        stackPaneFrame.getChildren().clear();
        stackPaneFrame.getChildren().add(node);
        StackPane.setAlignment(node, javafx.geometry.Pos.CENTER);
    }

    public void goBack() {
        if (!pageHistory.isEmpty()) {
            System.out.println("Going back...");
            Node previousNode = pageHistory.pop();
            stackPaneFrame.getChildren().setAll(previousNode);
        } else {
            System.out.println("No history to go back to.");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        try {
            loadpage("/fxml/FrontPage");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


    public void setModel(AdminModel adminModel) {
        this.adminModel = adminModel;
    }
}
