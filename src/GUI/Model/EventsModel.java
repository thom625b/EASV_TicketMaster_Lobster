package GUI.Model;

import BE.Events;
import BLL.EventsManager;
import CostumException.ApplicationWideException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.List;

public class EventsModel {

    private final EventsManager eventsManager;

    private final ObservableList<Events> eventList = FXCollections.observableArrayList();

    public EventsModel() throws IOException, ApplicationWideException {
        eventsManager = new EventsManager();
        loadEvents();
    }


    public void loadEvents() throws ApplicationWideException {
        List<Events> events = getAllEvents();
        eventList.setAll(events);

    }

    public ObservableList<Events> getEventList() {
        return eventList;
    }

    public ObservableList<Events> getAllEvents() throws ApplicationWideException {
        return FXCollections.observableArrayList(eventsManager.getAllEvents());
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

    public void addCoordinatorToEvents(int coordinatorId, int eventId) throws ApplicationWideException {
        eventsManager.addCoordinatorToEvents(coordinatorId, eventId);
    }

    public ObservableList<Events> getObsEvents() throws ApplicationWideException {
        List<Events> allEvents = eventsManager.getAllEvents();
        return FXCollections.observableArrayList(allEvents);
    }

}
