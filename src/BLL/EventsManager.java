package BLL;

import BE.Events;
import CostumException.ApplicationWideException;
import DAL.Events_DAO;
import GUI.Utility.UserContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EventsManager {

    private final Events_DAO eventsDao;

    public EventsManager() throws IOException {
        eventsDao = new Events_DAO();
    }

    public void createEvent(Events event, int currentUserId) throws ApplicationWideException {
        eventsDao.addEvent(event, currentUserId);
    }

    public List<Events> getAllEvents() throws ApplicationWideException {
        return eventsDao.getAllEvents();
    }

    public List<Events> getEventsByCoordinator(int coordinatorID) throws ApplicationWideException {
        return eventsDao.getEventsByCoordinator(coordinatorID);
    }

    public void updateEvent(Events event) throws ApplicationWideException {
        eventsDao.updateEvent(event);
    }

    public void deleteEvent(Events event) throws ApplicationWideException {
        eventsDao.deleteEvent(event);
    }

    public void addCoordinatorToEvents(int coordinatorId, int eventId) throws ApplicationWideException {
        eventsDao.addCoordinatorToEvents(coordinatorId, eventId);
    }

    public void getEventsConnectedToUser() throws ApplicationWideException {
        int currentUserId = UserContext.getInstance().getCurrentUserId();

        eventsDao.getAllEvents();
        List<Events> usersEvents = new ArrayList<>();


    }
        // Implement additional methods as needed for managing events
}
