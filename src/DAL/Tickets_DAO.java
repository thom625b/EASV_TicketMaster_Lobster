package DAL;

import BE.Costumers;
import BE.Events;
import BE.Tickets;
import CostumException.ApplicationWideException;
import DAL.DBConnector.DBConnector;
import com.microsoft.sqlserver.jdbc.SQLServerException;

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

    private int addTicketEvent(Connection conn, int ticketId, Events selectedEvent) throws SQLException {
        String ticketCostumerSql = "INSERT INTO EventTickets (eventID, ticketID) VALUES (?, ?)";
        try (PreparedStatement ticketCostumerPstmt = conn.prepareStatement(ticketCostumerSql, Statement.RETURN_GENERATED_KEYS)) {
            ticketCostumerPstmt.setInt(1, selectedEvent.getEventID());
            ticketCostumerPstmt.setInt(2, ticketId);
            ticketCostumerPstmt.executeUpdate();

            try (ResultSet rs = ticketCostumerPstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // mangler entitet
                }
            }
        }
        return 0;
    }

    private void addTicketCostumer(Connection conn, int ticketID, int costumerID) throws SQLException {
        String ticketCostumerSql = "INSERT INTO TicketCostumer (ticketID, costumerID) VALUES (?, ?)";
        try (PreparedStatement ticketCostumerPstmt = conn.prepareStatement(ticketCostumerSql)) {
            ticketCostumerPstmt.setInt(1, ticketID);
            ticketCostumerPstmt.setInt(2, costumerID);
            ticketCostumerPstmt.executeUpdate();
        }
    }

    private Tickets addTicket(Connection conn, String uuid, String ticketType) throws SQLException {
        String ticketSql = "INSERT INTO Tickets (qrHashCode, ticketType, isValid) VALUES (?, ?, ?)";
        try (PreparedStatement ticketPstmt = conn.prepareStatement(ticketSql, Statement.RETURN_GENERATED_KEYS)) {
            ticketPstmt.setString(1, uuid);
            ticketPstmt.setString(2, ticketType);
            ticketPstmt.setBoolean(3, true);
            ticketPstmt.executeUpdate();

            try (ResultSet rs = ticketPstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    return new Tickets(uuid, ticketType, 0, true, id);
                }
            }
        }
        return null;
    }

    public void saveTicketInformation(String uuid, boolean isValid, Events selectedEvent, String ticketType, Costumers customer) throws ApplicationWideException {
        try (Connection conn = dbConnector.getConnection()) {

            //Start transaction
            conn.setAutoCommit(false);

            try {
                Tickets createdTicket = addTicket(conn, uuid, ticketType);
                int ticketEventId = addTicketEvent(conn, createdTicket.getTicketID(), selectedEvent);

                addTicketCostumer(conn, ticketEventId, customer.getCostumerID());

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new RuntimeException(e);
            }
        } catch (SQLServerException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}


