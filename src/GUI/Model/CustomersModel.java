package GUI.Model;

import BE.Costumers;
import BLL.CostumersManager;
import CostumException.ApplicationWideException;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;

public class CustomersModel {
    private final CostumersManager costumersManager;
    private final ObservableList<Costumers> costumersObservableList;


    public CustomersModel() throws IOException, ApplicationWideException, SQLServerException {
        costumersManager = new CostumersManager();
        costumersObservableList = FXCollections.observableArrayList(costumersManager.getAllCostumers());
    }


    public ObservableList<Costumers> getAllCostumers() throws ApplicationWideException, SQLServerException {
        return FXCollections.observableArrayList(costumersManager.getAllCostumers());
    }

    public ObservableList<Costumers> getCostumersObservableList() {
        return costumersObservableList;
    }


    public void updateCustomer(Costumers customer) throws ApplicationWideException {
        costumersManager.updateCustomer(customer);

    }

    public Costumers saveCustomer(Costumers customers) throws SQLServerException, ApplicationWideException {
        return costumersManager.saveCustomer(customers);
    }
}
