package DAL;

import BE.Tickets;
import CostumException.ApplicationWideException;
import DAL.DBConnector.DBConnector;

import java.io.IOException;
import java.util.List;

public class Tickets_DAO implements ITicketsDataAccess{

    private DBConnector dbConnector;

    public Tickets_DAO() throws IOException {
        dbConnector = new DBConnector();
    }

    @Override
    public List<Tickets> getAllEvents() throws ApplicationWideException {
        return null;
    }

    @Override
    public Tickets addEvent(Tickets newTicket) throws ApplicationWideException {
        return null;
    }
}
