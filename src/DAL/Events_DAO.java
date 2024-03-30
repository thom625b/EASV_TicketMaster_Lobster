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
                        rs.getInt("eventID"),
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
        String deleteEventUsersSQL = "DELETE FROM EventUsers WHERE eventID = ?";
        String deleteEventSQL = "DELETE FROM Events WHERE eventID = ?";

        try (Connection conn = dbConnector.getConnection()) {
            conn.setAutoCommit(false); // Start transaction block


            try (PreparedStatement pstmt = conn.prepareStatement(deleteEventUsersSQL)) {
                pstmt.setInt(1, event.getEventID());
                pstmt.executeUpdate();
            }

            try (PreparedStatement pstmt = conn.prepareStatement(deleteEventSQL)) {
                pstmt.setInt(1, event.getEventID());
                pstmt.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            throw new ApplicationWideException("Error deleting the event from the database.", e);
        }
    }

    @Override
    public void updateEvent(Events event) throws ApplicationWideException {
        // Implement updating an existing event in the database
    }

    @Override
    public List<Events> getEventsByCoordinator(int coordinatorID) throws ApplicationWideException {
        List<Events> eventsByCoordinator = new ArrayList<>();
        String sql = "SELECT * FROM Events INNER JOIN EventUsers ON Events.eventID = EventUsers.eventID WHERE EventUsers.userID = ?";

        try (Connection conn = dbConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, coordinatorID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Events event = new Events(
                        rs.getInt("eventID"),
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
                eventsByCoordinator.add(event);
            }
        } catch (SQLException e) {
            throw new ApplicationWideException("Error occurred while retrieving events by coordinator", e);
        }
        return eventsByCoordinator;
    }


    public void addCoordinatorToEvents(int coordinatorId, int eventId) throws ApplicationWideException {
        String sql = "INSERT INTO EventUsers (eventID, userID) VALUES (?, ?);";

        try (Connection conn = dbConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, eventId);
            pstmt.setInt(2, coordinatorId);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user-event association failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new ApplicationWideException("Error occurred while associating coordinator with event.", e);
        }

    }
}
