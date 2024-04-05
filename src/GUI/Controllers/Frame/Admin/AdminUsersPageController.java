package GUI.Controllers.Frame.Admin;

import BE.Users;
import GUI.Controllers.IController;
import GUI.Model.UsersModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminUsersPageController implements Initializable, IController {

    private UsersModel usersModel;
    @FXML
    private TableColumn<Users, String> colName, colEmail, colLastName;
    @FXML
    private TableView<Users> tblViewCoorAdmin;
    @FXML
    private TableColumn<Users, Void> tblColButtons;
    @FXML
    private TableColumn<Users, String> colUserId;
    @FXML
    private TableColumn<Users, String> colRole;
    @FXML
    private TextField txtSearchCoorAdmin;

    public void showAllUsersInTable() {
        if (usersModel != null) {
            tblViewCoorAdmin.setItems(usersModel.getObservableUsers());
            colUserId.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getUserId())));
            colName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()));
            colLastName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));
            colEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
            colRole.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getRole())));


            usersModel.getObservableUsers().addListener((ListChangeListener<Users>)change -> {
                while (change.next()) {
                    tblViewCoorAdmin.refresh();
                }

            });
        } else {
            System.err.println("UsersModel is not initialized.");
        }
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeButtonColumn();
        if (usersModel != null) {
            setupSearchFunctionality();
        }
    }

    @Override
    public void setModel(UsersModel usersModel) {
        this.usersModel = usersModel;
        if (usersModel != null) {
            showAllUsersInTable();
            setupSearchFunctionality();
        }
    }




    private void initializeButtonColumn() {
        tblColButtons.setCellFactory(new Callback<TableColumn<Users, Void>, TableCell<Users, Void>>() {
            @Override
            public TableCell<Users, Void> call(final TableColumn<Users, Void> param) {
                return new TableCell<Users, Void>() {
                    private final Button btn = new Button();

                    {
                        Image img = new Image(getClass().getResourceAsStream("/pictures/pen.png"));
                        ImageView view = new ImageView(img);
                        btn.setGraphic(view);

                        //  button background transparent
                        btn.setStyle("-fx-background-color: transparent;");

                        btn.setOnAction(event -> {
                            Users user = getTableView().getItems().get(getIndex());
                            openNewFxmlFile(user);
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
                };
            }
        });
    }

    private void openNewFxmlFile(Users user) {
        AdminFrameController.getInstance().loadAdminUpdateCoordinatorPage(user);
    }


    private void setupSearchFunctionality() {
        FilteredList<Users> filteredUsers = new FilteredList<>(usersModel.getObservableUsers(), p -> true);
        txtSearchCoorAdmin.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredUsers.setPredicate(user -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (user.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (user.getLastName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (user.getEmail().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else return user.getRole().toString().toLowerCase().contains(lowerCaseFilter);
            });
        });

        SortedList<Users> sortedUsers = new SortedList<>(filteredUsers);
        sortedUsers.comparatorProperty().bind(tblViewCoorAdmin.comparatorProperty());
        tblViewCoorAdmin.setItems(sortedUsers);
    }


}
