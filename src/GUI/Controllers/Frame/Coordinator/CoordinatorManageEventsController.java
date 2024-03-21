package GUI.Controllers.Frame.Coordinator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;


public class CoordinatorManageEventsController {


    @FXML
    private void goToHomePage(ActionEvent actionEvent) {
       CoordinatorFrameController frameController = new CoordinatorFrameController();
       frameController.goBack();
    }

    @FXML
    private void mergeCoordAndEvent(ActionEvent actionEvent) {
    }

    @FXML
    private void getCoordinators(ActionEvent actionEvent) {
    }

    @FXML
    private void getEvents(ActionEvent actionEvent) {
    }
}
