package GUI.Controllers.Frame.Coordinator;

import BE.Costumers;
import BE.Events;
import CostumException.ApplicationWideException;
import GUI.Controllers.IController;
import GUI.Model.CustomersModel;
import GUI.Model.EventsModel;
import GUI.Model.TicketsModel;
import GUI.Model.UsersModel;
import GUI.Utility.PdfHandler;
import com.google.zxing.WriterException;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

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
    private TicketsModel ticketsModel;
    private CustomersModel customersModel;

    @FXML
    private TextField lblFirstnameTicket;
    @FXML
    private TextField lblLastnameTicket;
    private Stage primaryStage;

    @Override
    public void setModel(UsersModel usersModel) {

    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private Costumers saveCustomer(Costumers customer) throws SQLServerException, ApplicationWideException {
        String customerEmail = lblEmailTicket.getText();
        String customerFName = lblFirstnameTicket.getText();
        String customerLName = lblLastnameTicket.getText();
        Costumers customers = new Costumers(customerEmail, customerFName, customerLName);
        return customersModel.saveCustomer(customers);
    }

    private void saveTicketType(String uuid, boolean isValid, Events selectedEvent, Costumers customer) throws SQLServerException, ApplicationWideException {
        String ticketType = comboType.getSelectionModel().getSelectedItem();
        ticketsModel.saveTicketInformation(uuid, isValid, selectedEvent, ticketType, customer);
    }


    @FXML
    void BuyTicketToEvent(ActionEvent event) throws WriterException {
        Events selectedEvent = comboTickets.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) {
            return;
        }

        // Get customer information from input fields
        String customerEmail = lblEmailTicket.getText();
        String customerFName = lblFirstnameTicket.getText();
        String customerLName = lblLastnameTicket.getText();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PdfTicket.fxml"));
            Parent root = loader.load();

            PdfHandler pdfHandler = loader.getController();

            String eventName = selectedEvent.getEventName();
            String eventDate = String.valueOf(selectedEvent.getEventDate());
            String eventAddress = selectedEvent.getEventAddress();
            String eventZIP = String.valueOf(selectedEvent.getEventZipCode());
            String eventCity = selectedEvent.getEventCity();
            String eventType = comboType.getSelectionModel().getSelectedItem();
            BufferedImage eventImage = null;
            String uuid = UUID.randomUUID().toString();
            BufferedImage qrCodeImage = pdfHandler.generateQRCodeImage(uuid, 200, 200);

            pdfHandler.setTicketData(eventName, eventDate, eventAddress, eventZIP, eventCity, eventType, eventImage, qrCodeImage);

            String directoryPath = "resources/Data/Pdf/" + "Event" + selectedEvent.getEventID();

            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            String fileName = customerFName + "_" + customerLName + ".pdf";
            String destinationPath = directoryPath + "/" + fileName;
            pdfHandler.generatePDF(destinationPath);

            // Create customer object
            Costumers customer = new Costumers(customerEmail, customerFName, customerLName);

            // Save customer and ticket information
            Costumers createdCustomer = customersModel.saveCustomer(customer);

            boolean isValid = true;
            saveTicketType(uuid, isValid, selectedEvent, createdCustomer);

            new Scene(root);
            showAlert("Ticket Purchase", "Ticket successfully purchased and saved to: " + destinationPath, Alert.AlertType.INFORMATION);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLServerException e) {
            throw new RuntimeException(e);
        } catch (ApplicationWideException e) {
            throw new RuntimeException(e);
        }
    }

    // AlertBox
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
            eventsModel = new EventsModel();
            setupEventComboBox();
            customersModel = new CustomersModel();
            ticketsModel = new TicketsModel();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ApplicationWideException e) {
            throw new RuntimeException(e);
        } catch (SQLServerException e) {
            throw new RuntimeException(e);
        }
        initializeTicketTypes();
        initializeTicketAmounts();
    }


    @FXML
    public void createNewEvent(ActionEvent actionEvent) {
        CoordinatorFrameController.getInstance().openPageCoordinatorCreateEventPage();
    }
}
