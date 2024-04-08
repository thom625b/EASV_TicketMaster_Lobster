package GUI.Controllers.Frame.Coordinator;

import BE.Events;
import CostumException.ApplicationWideException;
import GUI.Controllers.IController;
import GUI.Model.EventsModel;
import GUI.Model.UsersModel;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CoordinatorTicketsController implements IController, Initializable {

    @FXML
    private ComboBox<Events> comboTickets;

    @FXML
    private ComboBox<Integer> comboAmount;

    @FXML
    private ComboBox<String> comboType;

    @FXML
    private TextField lblEmailTicket;

    @FXML
    private Label lblHeaderTicket;

    @FXML
    private TextField lblNameTicket;

    private EventsModel eventsModel;


    @Override
    public void setModel(UsersModel usersModel) {

    }



    @FXML
    void BuyTicketToEvent(ActionEvent event) {

    }

    @FXML
    void CloseTicketPage(ActionEvent event) {

    }

    private void initializeTicketTypes() {
        comboType.getItems().addAll("VIP", "Standard", "Food ticket");
    }

    private void populateComboTickets() {
        eventsModel.getEventList().forEach(event ->
                comboTickets.getItems().add(event)
        );
    }

    private void setupEventComboBox() {

        populateComboTickets();

        comboTickets.setConverter(new StringConverter<Events>() {
            @Override
            public String toString(Events event) {
                return event == null ? "" : event.getEventName() + " " + event.getEventDate();
            }

            @Override
            public Events fromString(String string) {
                return comboTickets.getItems().stream().filter(event ->
                        (event.getEventName() + " " + event.getEventDate()).equals(string)).findFirst().orElse(null);
            }
        });
    }

    private void initializeTicketAmounts() {
        for (int i = 1; i <= 10; i++) {
            comboAmount.getItems().add(i);
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            eventsModel =new EventsModel();
            setupEventComboBox();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ApplicationWideException e) {
            throw new RuntimeException(e);
        }
        initializeTicketTypes();
        initializeTicketAmounts();
    }



}
