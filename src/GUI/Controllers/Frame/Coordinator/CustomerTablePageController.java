package GUI.Controllers.Frame.Coordinator;

import BE.Costumers;
import BE.Users;
import CostumException.ApplicationWideException;
import GUI.Model.CustomersModel;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class CustomerTablePageController implements Initializable {

    @FXML
    private TableColumn<Costumers, String> colCostumerEmail;

    @FXML
    private TableColumn<Costumers, String> colCostumerFirstname;

    @FXML
    private TableColumn<Costumers, String> colCostumerLastname;

    @FXML
    private TableColumn<Costumers, String> colCostumerId;

    @FXML
    private TableColumn<Costumers, Void> colCostumerUpdate;

    @FXML
    private TableView<Costumers> tblCostumerView;

    private CustomersModel customerModel;

    private Costumers currentCustomer;

    public CustomerTablePageController() {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            customerModel = new CustomersModel();
            initializeColumns();
            initializeButtonColumn();
            System.out.println(customerModel);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ApplicationWideException e) {
            throw new RuntimeException(e);
        } catch (SQLServerException e) {
            throw new RuntimeException(e);
        }


    }

    private void initializeColumns() throws ApplicationWideException, SQLServerException {

        tblCostumerView.setItems(customerModel.getAllCostumers());
        colCostumerId.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCostumerID())));
        colCostumerFirstname.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCostumerFName()));
        colCostumerLastname.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCostumerLName()));
        colCostumerEmail.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCostumerEmail())));


        customerModel.getCostumersObservableList().addListener((ListChangeListener<Costumers>) change -> {
            while (change.next()) {
                tblCostumerView.refresh();
            }
        });

    }

    private void initializeButtonColumn() {
        colCostumerUpdate.setCellFactory(param -> new TableCell<>() {
            private final Button btn = createButtonWithIcon("/pictures/pen.png");

            {
                btn.setOnAction(event -> {
                    Costumers customer = getTableView().getItems().get(getIndex());

                    if (customer != null) {
                        openNewFxmlFile(customer);

                    } else {
                        showAlert("Selection Error", "No customer selected.");
                    }

                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        });
    }

    private Button createButtonWithIcon(String imagePath) {
        Button button = new Button();
        try {
            Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
            button.setGraphic(new ImageView(img));
            button.setStyle("-fx-background-color: transparent;");
        } catch (NullPointerException | IllegalArgumentException e) {
            System.err.println("Error loading image: " + imagePath + " - " + e.getMessage());

        }
        return button;
    }

    private void openNewFxmlFile(Costumers customer) {
        CoordinatorFrameController.getInstance().loadAdminUpdateCustomer(customer);
    }

    private void showAlert(String title, String message) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
    }







}
