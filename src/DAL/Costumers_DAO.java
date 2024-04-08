package DAL;

import BE.Costumers;
import CostumException.ApplicationWideException;
import DAL.DBConnector.DBConnector;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Costumers_DAO implements ICostumersDataAccess{

    private final DBConnector dbConnector;

    public Costumers_DAO() throws IOException {
        dbConnector = new DBConnector();
    }


    @Override
    public List<Costumers> getAllCostumers() throws ApplicationWideException, SQLServerException {
        List<Costumers> allCostumers = new ArrayList<>();
        try(Connection conn = dbConnector.getConnection();
            Statement stmt = conn.createStatement()){

            String sql = "SELECT * FROM Costumer";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                Costumers costumers = new Costumers(
                        rs.getString("costumerEmail"),
                        rs.getString("costumerFName"),
                        rs.getString("costumerLName"),
                        rs.getInt("costumerID")
                );
                allCostumers.add(costumers);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allCostumers;
    }

    @Override
    public Costumers addCostumer(Costumers newCostumers) throws ApplicationWideException {
        return null;
    }

    @Override
    public void updateCostumer(Costumers costumers) throws ApplicationWideException {

    }
}
