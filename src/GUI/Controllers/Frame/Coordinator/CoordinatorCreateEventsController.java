package GUI.Controllers.Frame.Coordinator;

import GUI.Controllers.IController;
import GUI.Model.UsersModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class CoordinatorCreateEventsController implements IController {

    @FXML
    public TextField txtEventPicture;
    @FXML
    public TextField txtEventTitle;
    @FXML
    public TextField txtEventZipCode;
    @FXML
    public TextField txtEventCity;
    @FXML
    public TextField txtEventAddress;
    @FXML
    public Button btnUploadImageToEvent;
    @FXML
    public Button btnAddNewEvent;
    @FXML
    public DatePicker dpEventStartDate;

    @FXML
    public void EventStartDate(ActionEvent actionEvent) {

    }

    @FXML
    public void UploadImageToEvent(ActionEvent actionEvent) {
    }

    @FXML
    public void AddNewEvent(ActionEvent actionEvent) {
    }

    @Override
    public void setModel(UsersModel usersModel) {

    }
}
