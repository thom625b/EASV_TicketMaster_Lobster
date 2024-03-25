package DAL;

import BE.Events;
import BE.Users;
import CostumException.ApplicationWideException;
import DAL.DBConnector.DBConnector;

import java.io.IOException;
import java.util.List;

public class Events_DAO implements IEventsDataAccess {

    private DBConnector dbConnector;

    public Events_DAO() throws IOException {
        dbConnector = new DBConnector();
    }

    @Override
    public List<Events> getAllEvents() throws ApplicationWideException {
        return null;
    }

    @Override
    public Users addEvent(Events newEvent) throws ApplicationWideException {
        return null;
    }

    @Override
    public void deleteEvent(Events events) throws ApplicationWideException {

    }

    @Override
    public void updateEvent(Events events) throws ApplicationWideException {

    }

    @Override
    public List<Events> getEventsByCoordinator(int coordinatorID) throws ApplicationWideException {
        return null;
    }
}
