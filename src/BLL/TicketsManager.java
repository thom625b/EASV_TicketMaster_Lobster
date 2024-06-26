package BLL;

import BE.Costumers;
import BE.Events;
import CostumException.ApplicationWideException;
import DAL.Tickets_DAO;

import java.io.IOException;

public class TicketsManager {

    private final Tickets_DAO ticketsDao;

    public TicketsManager() throws IOException {
        ticketsDao = new Tickets_DAO();
    }


    public void saveTicketInformation(String uuid, boolean isValid, Events selectedEvent, String ticketType, Costumers customer, int ticketAmount) throws ApplicationWideException {
        ticketsDao.saveTicketInformation(uuid, isValid, selectedEvent, ticketType, customer, ticketAmount);
    }
}
