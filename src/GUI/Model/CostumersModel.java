package GUI.Model;

import BE.Costumers;
import BLL.CostumersManager;
import CostumException.ApplicationWideException;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;

public class CostumersModel {
    private final CostumersManager costumersManager;
    private final ObservableList<Costumers> costumersObservableList = FXCollections.observableArrayList();


    public CostumersModel() throws IOException, ApplicationWideException {
        costumersManager = new CostumersManager();
    }


    public ObservableList<Costumers> getAllCostumers() throws ApplicationWideException, SQLServerException {
        return FXCollections.observableArrayList(costumersManager.getAllCostumers());
    }

    public ObservableList<Costumers> getCostumersObservableList() {
        return costumersObservableList;
    }



}
