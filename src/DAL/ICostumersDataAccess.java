package DAL;

import BE.Costumers;
import CostumException.ApplicationWideException;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.util.List;

public interface ICostumersDataAccess {
    List<Costumers> getAllCostumers() throws ApplicationWideException, SQLServerException;

    Costumers addCostumer(Costumers newCostumers) throws ApplicationWideException;

    void updateCostumer(Costumers costumers) throws ApplicationWideException;
}
