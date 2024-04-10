package DAL;

import BE.Costumers;
import BE.Events;
import BE.Tickets;
import CostumException.ApplicationWideException;
import DAL.DBConnector.DBConnector;

import java.io.IOException;
import java.sql.*;
import java.util.List;

public class Tickets_DAO implements ITicketsDataAccess{

    private DBConnector dbConnector;

    public Tickets_DAO() throws IOException {
        dbConnector = new DBConnector();
    }

    @Override
    public List<Tickets> getAllEvents() throws ApplicationWideException {
        return null;
    }

    @Override
    public Tickets addEvent(Tickets newTicket) throws ApplicationWideException {
        return null;
    }

    public void saveTicketInformation(String uuid, boolean isValid, Events selectedEvent, String ticketType) throws ApplicationWideException {
        try {
            String sql = "INSERT INTO Tickets (qrHashCode, ticketType, isValid) VALUES (?, ?, ?)";
            try (Connection conn = dbConnector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, uuid);
                pstmt.setString(2, ticketType);
                pstmt.setBoolean(3, true);
                pstmt.executeUpdate();

                int affectedRows = pstmt.getUpdateCount();
                if(affectedRows == 0){
                    throw new ApplicationWideException("Creating ticket failed, now rows affected");

                }
            }

        } catch (SQLException e) {
            throw new ApplicationWideException("Error saving ticket information", e);
        }
    }

}
