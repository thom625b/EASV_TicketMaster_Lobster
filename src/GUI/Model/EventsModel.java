package GUI.Model;

import BE.Events;
import BLL.EventsManager;
import CostumException.ApplicationWideException;

import java.io.IOException;
import java.util.List;

public class EventsModel {

    private final EventsManager eventsManager;

    public EventsModel() throws IOException {
        eventsManager = new EventsManager();
    }

    public List<Events> getAllEvents() throws ApplicationWideException {
        return eventsManager.getAllEvents();
    }

    public void createEvent(Events event) throws ApplicationWideException {
        eventsManager.createEvent(event);
    }

    public void updateEvent(Events event) throws ApplicationWideException {
        eventsManager.updateEvent(event);
    }

    public void deleteEvent(Events event) throws ApplicationWideException {
        eventsManager.deleteEvent(event);
    }
}
