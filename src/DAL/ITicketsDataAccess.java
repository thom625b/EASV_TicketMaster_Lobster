package DAL;

import BE.Tickets;
import CostumException.ApplicationWideException;

import java.util.List;

public interface ITicketsDataAccess {
    List<Tickets> getAllEvents() throws ApplicationWideException;

    Tickets addEvent(Tickets newTicket) throws ApplicationWideException;
}
