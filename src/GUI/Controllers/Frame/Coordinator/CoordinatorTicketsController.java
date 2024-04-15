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
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.util.Duration;
import javafx.util.StringConverter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
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
    private TextField lblEmailTicket, lblFirstnameTicket, lblLastnameTicket, lblNameTicket;
    @FXML
    private Label lblTicket, lblErrorText, lblHeaderTicket;
    @FXML
    private Button btnSubtract, btnAdd, btnCreateNewCoordinatorTicket;

    private EventsModel eventsModel;
    private TicketsModel ticketsModel;
    private CustomersModel customersModel;

    private int ticketCount = 1;

    @Override
    public void setModel(UsersModel usersModel) {

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
        setupBordersToBlink();
    }


    private void setupBordersToBlink () {
        setupControlValidation(lblEmailTicket);
        setupControlValidation(lblFirstnameTicket);
        setupControlValidation(lblLastnameTicket);
        setupControlValidation(comboTickets);
        setupControlValidation(comboType);
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

       if (!validateAllFields()) {
           updateErrorDisplay("Please enter a valid email address.", lblEmailTicket);
           return;
       }


        // Get customer information from input fields
        String customerEmail = lblEmailTicket.getText();
        String customerFName = lblFirstnameTicket.getText();
        String customerLName = lblLastnameTicket.getText();


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
                                Platform.runLater(() -> updateMessageDisplay("Email sent successfully", false));
                                emailFuture.complete(null);
                            } catch (Exception e) {
                                emailFuture.completeExceptionally(e);
                            }
                            return emailFuture;
                        })
                        .exceptionally(e -> {
                            Platform.runLater(() -> updateMessageDisplay("Failed to send email.", true));
                            return null;
                        });
            } catch (IOException | SQLServerException | ApplicationWideException e) {
                throw new RuntimeException(e);
            }
        }
    }



    private void updateErrorDisplay(String message, Control field) {
        Platform.runLater(() -> {
            lblErrorText.setText(message);
            if (!message.isEmpty()) {
                lblErrorText.setStyle("-fx-text-fill: red;");
            }

            // clear the error message after 3 seconds
            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(e -> lblErrorText.setText(""));  // Clear the error message text
            pause.play();

        });


    }

    private void updateMessageDisplay(String message, boolean isError) {
        Platform.runLater(() -> {
            lblErrorText.setText(message);
            if (isError) {
                lblErrorText.setStyle("-fx-text-fill: red;");
            } else {
                lblErrorText.setStyle("-fx-text-fill: green;");
            }
        });
    }

    private boolean validateAllFields() {
        boolean hasErrors = false;

        // Validate each required field and apply red border if empty
        hasErrors |= !validateField(lblEmailTicket, isValidEmail(lblEmailTicket.getText()));
        hasErrors |= !validateField(lblFirstnameTicket, !lblFirstnameTicket.getText().trim().isEmpty());
        hasErrors |= !validateField(lblLastnameTicket, !lblLastnameTicket.getText().trim().isEmpty());
        hasErrors |= !validateComboBox(comboTickets, comboTickets.getValue() != null);
        hasErrors |= !validateComboBox(comboType, comboType.getValue() != null);

        return !hasErrors;
    }

    private boolean validateField(TextField field, boolean isValid) {
        if (!isValid) {
            field.setStyle("-fx-border-color: red; -fx-border-width: 1px; -fx-border-radius: 15px;");
            return false;
        }
        field.setStyle("");
        return true;

    }

    private boolean validateComboBox(ComboBox<?> comboBox, boolean isValid) {
        if (!isValid) {
            comboBox.setStyle("-fx-border-color: red; -fx-border-width: 1px; -fx-border-radius: 15px;");
            return false;
        }
        comboBox.setStyle("");
        return true;


    }



    private void setupControlValidation(Control control) {
        if (control instanceof TextField) {
            TextField textField = (TextField) control;
            textField.textProperty().addListener((observable, oldValue, newValue) -> {
                validateField(textField, !newValue.trim().isEmpty());
            });
        } else if (control instanceof ComboBox) {
            ComboBox<?> comboBox = (ComboBox<?>) control;
            comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                validateComboBox(comboBox, newValue != null);
            });
        }
    }

    @FXML
    void CloseTicketPage(ActionEvent event) {
    CoordinatorFrameController.getInstance().goBack();
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

    int getTicketCount() {
        return ticketCount;
    }

    Label getLblTicket() {
        return lblTicket;
    }

    void setTicketCount(int ticketCount) {
        this.ticketCount = ticketCount;
    }

    void setLabel() {
        this.lblTicket = new Label();
    }

}
