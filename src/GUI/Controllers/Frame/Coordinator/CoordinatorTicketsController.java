package GUI.Controllers.Frame.Coordinator;

import BE.Events;
import CostumException.ApplicationWideException;
import GUI.Controllers.IController;
import GUI.Model.EventsModel;
import GUI.Model.UsersModel;
import GUI.Utility.PdfHandler;
import com.google.zxing.WriterException;
import com.sun.tools.javac.Main;
import javafx.event.ActionEvent;
import javafx.event.Event;
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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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

    @FXML
    void BuyTicketToEvent(ActionEvent event) throws WriterException {
        Events selectedEvent = comboTickets.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) {
            return;
        }

        String firstName = lblFirstnameTicket.getText();
        String lastName = lblLastnameTicket.getText();

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


            String directoryPath = "resources/Data/Pdf/" + selectedEvent.getEventName();

            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            String fileName = firstName + "_" + lastName + ".pdf";
            String destinationPath = directoryPath + "/" + fileName;
            pdfHandler.generatePDF(destinationPath);
            new Scene(root);
            showAlert("Ticket Purchase", "Ticket successfully purchased and saved to: " + destinationPath, Alert.AlertType.INFORMATION);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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


    @FXML
    public void createNewEvent(ActionEvent actionEvent) {
        CoordinatorFrameController.getInstance().openPageCoordinatorCreateEventPage();
    }
}
