package DAL;

import BE.Events;
import CostumException.ApplicationWideException;
import DAL.DBConnector.DBConnector;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Events_DAO implements IEventsDataAccess {

    private final DBConnector dbConnector;

    public Events_DAO() throws IOException {
        dbConnector = new DBConnector();
    }

    @Override
    public List<Events> getAllEvents() throws ApplicationWideException {
        List<Events> allEvents = new ArrayList<>();
        try (Connection conn = dbConnector.getConnection();
             Statement stmt = conn.createStatement()) {
            String sql = "SELECT * FROM Events";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Events event = new Events(
                        rs.getString("eventName"),
                        rs.getString("eventDate"),
                        rs.getInt("eventStatus"),
                        rs.getInt("eventRemainingDays"),
                        rs.getInt("eventParticipants"),
                        rs.getString("eventAddress"),
                        rs.getInt("eventZIP"),
                        rs.getString("eventCity"),
                        rs.getString("eventDescription")
                );
                allEvents.add(event);
            }
        } catch (SQLException e) {
            throw new ApplicationWideException("Error occurred while retrieving events", e);
        }
        return allEvents;
    }

    public Events addEvent(Events event) throws ApplicationWideException {
        String sql = "INSERT INTO Events (eventName, eventDate, eventStatus, eventRemainingDays, eventParticipants, eventAddress, eventZIP, eventCity, eventDescription) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try (Connection conn = dbConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, event.getEventName());
            pstmt.setString(2, event.getEventDate());
            pstmt.setInt(3, event.getEventStatus());
            pstmt.setInt(4, event.getEventRemainingDays());
            pstmt.setInt(5, event.getEventParticipants());
            pstmt.setString(6, event.getEventAddress());
            pstmt.setInt(7, event.getEventZipCode());
            pstmt.setString(8, event.getEventCity());
            pstmt.setString(9, event.getEventDescription());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new ApplicationWideException("Error occurred while adding event", e);
        }
        return event;
    }


    @Override
    public void deleteEvent(Events event) throws ApplicationWideException {
        // Implement deletion of an event from the database
    }

    @Override
    public void updateEvent(Events event) throws ApplicationWideException {
        // Implement updating an existing event in the database
    }

    @Override
    public List<Events> getEventsByCoordinator(int coordinatorID) throws ApplicationWideException {
        // Implement retrieval of events by coordinator from the database
        return null;
    }
}
