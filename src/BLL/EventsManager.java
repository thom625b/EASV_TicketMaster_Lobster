package BLL;

import BE.Events;
import CostumException.ApplicationWideException;
import DAL.Events_DAO;

import java.io.IOException;
import java.util.List;

public class EventsManager {

    private final Events_DAO eventsDao;

    public EventsManager() throws IOException {
        eventsDao = new Events_DAO();
    }

    public void createEvent(String eventName, String eventDate, int eventStatus, int eventRemainingDays, int eventParticipants, String eventAddress, int eventZipCode, String eventDescription) throws ApplicationWideException {
        Events newEvent = new Events(eventName, eventDate, eventStatus, eventRemainingDays, eventParticipants, eventAddress, eventZipCode, eventDescription);
        eventsDao.addEvent(newEvent);
    }

    public List<Events> getAllEvents() throws ApplicationWideException {
        return eventsDao.getAllEvents();
    }

    public List<Events> getEventsByCoordinator(int coordinatorID) throws ApplicationWideException {
        return eventsDao.getEventsByCoordinator(coordinatorID);
    }

    // Implement additional methods as needed for managing events
}
