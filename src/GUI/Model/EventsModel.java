package GUI.Model;

import BE.Events;
import BLL.EventsManager;
import CostumException.ApplicationWideException;
import GUI.Utility.UserContext;
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
        int currentCoordinatorId = UserContext.getInstance().getCurrentUserId();
        eventsManager.createEvent(event, currentCoordinatorId);
        eventList.add(event);
    }

    public void updateEvent(Events event) throws ApplicationWideException {
        eventsManager.updateEvent(event);
        int index = eventList.indexOf(event);
        if (index >= 0) {
            eventList.set(index, event);
        }
    }

    public void deleteEvent(Events event) throws ApplicationWideException {
        eventsManager.deleteEvent(event);
        eventList.remove(event);
    }

    public void addCoordinatorToEvents(int coordinatorId, int eventId) throws ApplicationWideException {
        eventsManager.addCoordinatorToEvents(coordinatorId, eventId);
    }

    public ObservableList<Events> getObsEvents() throws ApplicationWideException {
        List<Events> allEvents = eventsManager.getAllEvents();
        return FXCollections.observableArrayList(allEvents);
    }

    public ObservableList<Events> getEventsByCoordinator(int coordinatorID) throws ApplicationWideException{
        List<Events> allEventsByCoordinator = eventsManager.getEventsByCoordinator(coordinatorID);
        return FXCollections.observableArrayList(allEventsByCoordinator);
    }

}
