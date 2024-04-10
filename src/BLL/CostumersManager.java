package BLL;

import BE.Costumers;
import BE.Events;
import CostumException.ApplicationWideException;
import DAL.Costumers_DAO;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.IOException;
import java.util.List;

public class CostumersManager {
    private final Costumers_DAO costumersDao;

    public CostumersManager() throws IOException {
        costumersDao = new Costumers_DAO();
    }

    public List<Costumers> getAllCostumers() throws ApplicationWideException, SQLServerException {
        return costumersDao.getAllCostumers();
    }

    public void updateCustomer(Costumers customer) throws ApplicationWideException {
        costumersDao.updateCostumer(customer);
    }

    public Costumers saveCustomer(Costumers customers) throws SQLServerException, ApplicationWideException {
       return costumersDao.saveCustomer(customers);
    }


}
