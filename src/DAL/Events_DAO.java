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
                        rs.getDate("eventDate").toLocalDate(),
                        rs.getInt("eventStatus"),
                        rs.getInt("eventRemainingDays"),
                        rs.getInt("eventParticipants"),
                        rs.getString("eventAddress"),
                        rs.getInt("eventZIP"),
                        rs.getString("eventCity"),
                        rs.getString("eventDescription"),
                        rs.getString("eventStartTime"),
                        rs.getString("eventEndTime")
                );
                allEvents.add(event);
            }
        } catch (SQLException e) {
            throw new ApplicationWideException("Error occurred while retrieving events", e);
        }
        return allEvents;
    }


    @Override
    public Events addEvent(Events event, int currentUserId) throws ApplicationWideException {
        String insertEventSQL = "INSERT INTO Events (eventName, eventDate, eventStatus, eventRemainingDays, eventParticipants, eventAddress, eventZIP, eventCity, eventDescription, eventStartTime, eventEndTime) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        String insertEventUserSQL = "INSERT INTO EventUsers (eventID, userID) VALUES (?, ?);";

        try (Connection conn = dbConnector.getConnection();
             PreparedStatement pstmtEvent = conn.prepareStatement(insertEventSQL, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement pstmtEventUser = conn.prepareStatement(insertEventUserSQL)) {

            pstmtEvent.setString(1, event.getEventName());
            pstmtEvent.setString(2, event.getEventDate().toString());
            pstmtEvent.setInt(3, event.getEventStatus());
            pstmtEvent.setInt(4, event.getEventRemainingDays());
            pstmtEvent.setInt(5, event.getEventParticipants());
            pstmtEvent.setString(6, event.getEventAddress());
            pstmtEvent.setInt(7, event.getEventZipCode());
            pstmtEvent.setString(8, event.getEventCity());
            pstmtEvent.setString(9, event.getEventDescription());
            pstmtEvent.setString(10, event.getEventStartTime());
            pstmtEvent.setString(11, event.getEventEndTime());

            int affectedRows = pstmtEvent.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating event failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pstmtEvent.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    event.setEventID(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating event failed, no ID obtained.");
                }
            }

            pstmtEventUser.setInt(1, event.getEventID());
            pstmtEventUser.setInt(2, currentUserId);
            pstmtEventUser.executeUpdate();

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
                        rs.getDate("eventDate").toLocalDate(),
                        rs.getInt("eventStatus"),
                        rs.getInt("eventRemainingDays"),
                        rs.getInt("eventParticipants"),
                        rs.getString("eventAddress"),
                        rs.getInt("eventZIP"),
                        rs.getString("eventCity"),
                        rs.getString("eventDescription"),
                        rs.getString("eventStartTime"),
                        rs.getString("eventEndTime")
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
