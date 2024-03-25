package DAL;

import BE.Events;
import BE.Users;
import CostumException.ApplicationWideException;

import java.util.List;

public interface IEventsDataAccess {
    List<Events> getAllEvents() throws ApplicationWideException;

    Users addEvent(Events newEvent) throws ApplicationWideException;

    void deleteEvent(Events events) throws ApplicationWideException;

    void updateEvent(Events events) throws ApplicationWideException;

    List<Events> getEventsByCoordinator(int coordinatorID) throws ApplicationWideException;

}
