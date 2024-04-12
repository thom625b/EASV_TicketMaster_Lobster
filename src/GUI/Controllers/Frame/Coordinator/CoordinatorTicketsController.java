package GUI.Controllers.Frame.Coordinator;

import BE.Costumers;
import BE.Events;
import CostumException.ApplicationWideException;
import GUI.Controllers.IController;
import GUI.Model.CustomersModel;
import GUI.Model.EventsModel;
import GUI.Model.TicketsModel;
import GUI.Model.UsersModel;
import GUI.Utility.EmailSender;
import GUI.Utility.PdfHandler;
import com.google.zxing.WriterException;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.resend.core.exception.ResendException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CoordinatorTicketsController implements IController, Initializable {

    @FXML
    private ComboBox<Events> comboTickets;


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
    @FXML
    private Button btnSubtract, btnAdd;

    @FXML
    private Label lblTicket;

    private int ticketCount = 1;

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

    private void saveTicketType(String uuid, boolean isValid, Events selectedEvent, Costumers customer, int ticketAmount) throws SQLServerException, ApplicationWideException {
        String ticketType = comboType.getSelectionModel().getSelectedItem();
        ticketsModel.saveTicketInformation(uuid, isValid, selectedEvent, ticketType, customer, ticketAmount);
    }


    private boolean isValidEmail(String email) {
        String emailRegex = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }



   @FXML
    void buyTicketToEvent(ActionEvent event) throws WriterException {
        Events selectedEvent = comboTickets.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) {
            Platform.runLater(() -> showAlert("Error", "No event selected", Alert.AlertType.ERROR));
            return;
        }

        // Get customer information from input fields
        String customerEmail = lblEmailTicket.getText();
        String customerFName = lblFirstnameTicket.getText();
        String customerLName = lblLastnameTicket.getText();

        if (!isValidEmail(customerEmail)) {
            showAlert("Input Error", "Please enter a valid email address.", Alert.AlertType.WARNING);
            return;
        }

        for (int i = 0; i < ticketCount; i++) {
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
                pdfHandler.generatePDFAsync(destinationPath);

                // Create customer object
                Costumers customer = new Costumers(customerEmail, customerFName, customerLName);

                // Save customer and ticket information
                Costumers createdCustomer = customersModel.saveCustomer(customer);

                boolean isValid = true;
                int ticketAmount = 1; // Each iteration creates one ticket
                saveTicketType(uuid, isValid, selectedEvent, createdCustomer, ticketAmount);

                new Scene(root);

                pdfHandler.generatePDFAsync(destinationPath)
                        .thenCompose(path -> {
                            CompletableFuture<Void> emailFuture = new CompletableFuture<>();
                            try {
                                EmailSender emailSender = new EmailSender();
                                emailSender.sendTicket(customerEmail, "Your Event Ticket", "Here is your ticket for the event.", Path.of(path));
                                Platform.runLater(() -> showAlert("Success", "Email sent successfully", Alert.AlertType.INFORMATION));
                                emailFuture.complete(null);
                            } catch (Exception e) {
                                emailFuture.completeExceptionally(e);
                            }
                            return emailFuture;
                        })
                        .exceptionally(e -> {
                            Platform.runLater(() -> showAlert("Email Error", "Failed to send email.", Alert.AlertType.ERROR));
                            return null;
                        });
            } catch (IOException | SQLServerException | ApplicationWideException e) {
                throw new RuntimeException(e);
            }
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
        List<Events> sortedEvents = eventsModel.getEventList().stream()
                .sorted(Comparator.comparing(Events::getEventDate))
                .collect(Collectors.toList());

        comboTickets.getItems().addAll(sortedEvents);
    }


    private void setupEventComboBox() {
        populateComboTickets();

        comboTickets.setConverter(new StringConverter<Events>() {
            @Override
            public String toString(Events event) {
                if (event == null) return "";
                String formattedDate = event.getEventDate().format(DateTimeFormatter.ofPattern("dd-MM-yy"));
                return event.getEventName() + " " + formattedDate;
            }

            @Override
            public Events fromString(String string) {
                return comboTickets.getItems().stream()
                        .filter(event -> {
                            String formattedDate = event.getEventDate().format(DateTimeFormatter.ofPattern("dd-MM-yy"));
                            return (event.getEventName() + " " + formattedDate).equals(string);
                        })
                        .findFirst()
                        .orElse(null);
            }
        });
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
        lblTicket.setText(Integer.toString(ticketCount));
        initializeTicketTypes();
    }


    @FXML
    public void createNewEvent(ActionEvent actionEvent) {
        CoordinatorFrameController.getInstance().openPageCoordinatorCreateEventPage();


    }

    @FXML
    public void btnSubtractTicket(ActionEvent actionEvent) {
        if (ticketCount > 1) {
            ticketCount--;
            lblTicket.setText(Integer.toString(ticketCount));
        }
    }

    @FXML
    public void btnAddTicket(ActionEvent actionEvent) {
        ticketCount++;
        lblTicket.setText(Integer.toString(ticketCount));
    }

    /**
     * For testing purposes only!
     * Acces modifiers is set to package private
     */

    int getTicketCount(){
        return ticketCount;
    }

    Label getLblTicket(){
        return lblTicket;
    }

    void setTicketCount(int ticketCount){
        this.ticketCount = ticketCount;
    }

    void setLabel(){
        this.lblTicket = new Label();
    }

}
