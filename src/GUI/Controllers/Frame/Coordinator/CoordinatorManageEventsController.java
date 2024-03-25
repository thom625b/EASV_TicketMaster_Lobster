package GUI.Controllers.Frame.Coordinator;

import BE.Users;
import CostumException.ApplicationWideException;
import GUI.Controllers.IController;
import GUI.Model.UsersModel;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

import java.io.IOException;


public class CoordinatorManageEventsController implements IController {
    UsersModel usersModel;
    @FXML
    private ComboBox<Users> btnCoordinatorsDropDown;
    @FXML
    private ComboBox btnEventsDropDown;


    @FXML
    public void initialize() throws ApplicationWideException, SQLServerException, IOException {
        usersModel = new UsersModel();
        getCoordinators(null);
    }
    @FXML
    private void goToHomePage(ActionEvent actionEvent) {
       CoordinatorFrameController frameController = new CoordinatorFrameController();
       frameController.goBack();
    }

    @FXML
    private void mergeCoordAndEvent(ActionEvent actionEvent) {
    }

    @FXML
    private void getCoordinators(ActionEvent actionEvent) throws ApplicationWideException, SQLServerException, IOException {
        try {
            ObservableList<Users> coordinators = usersModel.getCoordinatorsObservable();
            btnCoordinatorsDropDown.setItems(coordinators);

            // laver BE om til en læsbar tekst
            btnCoordinatorsDropDown.setConverter(new StringConverter<Users>() {
                @Override
                public String toString(Users user) {
                    return user != null ? user.getFirstName() + " " + user.getLastName() : "";
                }
                @Override
                public Users fromString(String string) {
                    return null;
                }
            });
        } catch (ApplicationWideException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void getEvents(ActionEvent actionEvent) {

    }

    @Override
    public void setModel(UsersModel usersModel) {

    }
}
