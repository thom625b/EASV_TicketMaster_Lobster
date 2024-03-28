package GUI.Model;

import BE.Events;
import BLL.EventsManager;
import CostumException.ApplicationWideException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;

public class EventsModel {

    private final EventsManager eventsManager;

    public EventsModel() throws IOException {
        eventsManager = new EventsManager();
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
}
