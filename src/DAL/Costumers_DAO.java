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

    public Costumers saveCustomer(Costumers customers) throws ApplicationWideException {
        String sql = "INSERT INTO Costumer (costumerEmail, costumerFName, costumerLName) VALUES (?, ?, ?)";

        try (Connection conn = dbConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Set the parameters for the update based on the Costumers object
            pstmt.setString(1, customers.getCostumerEmail());
            pstmt.setString(2, customers.getCostumerFName());
            pstmt.setString(3, customers.getCostumerLName());

            // Execute the update
            int affectedRows = pstmt.executeUpdate();

            // Check if any rows were affected
            if (affectedRows == 0) {
                throw new ApplicationWideException("Inserting customer failed, no rows affected.");
            }

            // Retrieve the generated keys
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    // Get the generated ID from the ResultSet
                    int id = rs.getInt(1);
                    // Create a new Costumers object with the generated ID
                    return new Costumers(customers.getCostumerEmail(), customers.getCostumerFName(), customers.getCostumerLName(), id);
                } else {
                    // If no generated keys were returned, throw an exception
                    throw new ApplicationWideException("Inserting customer failed, no generated keys obtained.");
                }
            }

        } catch (SQLException e) {
            throw new ApplicationWideException("Error inserting customer", e);
        }
    }
}

