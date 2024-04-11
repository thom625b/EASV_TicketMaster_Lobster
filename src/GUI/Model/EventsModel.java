package GUI.Model;

import BE.Events;
import BLL.EventsManager;
import CostumException.ApplicationWideException;
import GUI.Utility.UserContext;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class EventsModel {

    private final EventsManager eventsManager;

    private static final ObservableList<Events> userEvents = FXCollections.observableArrayList();
    private static final ObservableList<Events> eventList = FXCollections.observableArrayList();

    public final int id;

    public EventsModel() throws IOException, ApplicationWideException {
        eventsManager = new EventsManager();
        loadEvents();
        loadUserEvents();
        System.out.println("hej");
        id = new Random().nextInt(1000);
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
        userEvents.add(event);
    }

    public void updateEvent(Events event) throws ApplicationWideException {
        eventsManager.updateEvent(event);
        int index = eventList.indexOf(event);
        if (index >= 0) {
            eventList.set(index, event);
        }
        int userIndex = userEvents.indexOf(event);
        if (userIndex >= 0) {
            userEvents.set(userIndex, event);
        }
    }

    public void deleteEvent(Events event) throws ApplicationWideException {
        eventsManager.deleteEvent(event);
        eventList.remove(event);
        userEvents.remove(event);
    }

    public void addCoordinatorToEvents(int coordinatorId, int eventId) throws ApplicationWideException {
        eventsManager.addCoordinatorToEvents(coordinatorId, eventId);
    }

    public ObservableList<Events> getObsEvents() throws ApplicationWideException {
        List<Events> allEvents = eventsManager.getAllEvents();
        return FXCollections.observableArrayList(allEvents);
    }

    public ObservableList<Events> getUserEvents() throws ApplicationWideException {
        return userEvents;
    }

    public ObservableList<Events> getEventsByCoordinator(int coordinatorID) throws ApplicationWideException{
        List<Events> allEventsByCoordinator = eventsManager.getEventsByCoordinator(coordinatorID);
        return FXCollections.observableArrayList(allEventsByCoordinator);
    }

    public void loadUserEvents() throws ApplicationWideException {
        userEvents.addAll(getEventsByCoordinator(UserContext.getInstance().getCurrentUserId()));
    }
}
