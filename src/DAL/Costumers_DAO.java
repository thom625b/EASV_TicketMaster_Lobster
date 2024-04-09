package DAL;

import BE.Costumers;
import CostumException.ApplicationWideException;
import DAL.DBConnector.DBConnector;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.IOException;
import java.sql.*;
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
    public void updateCostumer(Costumers costumer) throws ApplicationWideException {
        String sql = "UPDATE Costumer SET costumerEmail = ?, costumerFName = ?, costumerLName = ? WHERE costumerID = ?";

        try (Connection conn = dbConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set the parameters for the update based on the Costumers object
            pstmt.setString(1, costumer.getCostumerEmail());
            pstmt.setString(2, costumer.getCostumerFName());
            pstmt.setString(3, costumer.getCostumerLName());
            pstmt.setInt(4, costumer.getCostumerID());

            // Execute the update
            int affectedRows = pstmt.executeUpdate();

            // Optionally, you can check the affected rows and act accordingly
            if (affectedRows == 0) {
                throw new ApplicationWideException("Updating customer failed, no rows affected.");
            }
        } catch (SQLException e) {
            // Wrap the SQL exception in a more general exception if you wish
            throw new ApplicationWideException("Error updating customer", e);
        }
    }
}
