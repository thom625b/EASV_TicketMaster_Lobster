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

    public void createEvent(Events event) throws ApplicationWideException {
        eventsDao.addEvent(event);
    }

    public List<Events> getAllEvents() throws ApplicationWideException {
        return eventsDao.getAllEvents();
    }

    public List<Events> getEventsByCoordinator(int coordinatorID) throws ApplicationWideException {
        return eventsDao.getEventsByCoordinator(coordinatorID);
    }

    public void updateEvent(Events event) {
    }

    public void deleteEvent(Events event) {
    }

    // Implement additional methods as needed for managing events
}
