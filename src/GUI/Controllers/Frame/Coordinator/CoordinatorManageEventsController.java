package GUI.Controllers.Frame.Coordinator;

import GUI.Controllers.IController;
import GUI.Model.UsersModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;


public class CoordinatorManageEventsController implements IController {


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

    @Override
    public void setModel(UsersModel usersModel) {

    }
}
