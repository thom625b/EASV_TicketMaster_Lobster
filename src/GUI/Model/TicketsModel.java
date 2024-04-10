package GUI.Model;
import BE.Events;
import BLL.TicketsManager;
import CostumException.ApplicationWideException;

import java.io.IOException;


public class TicketsModel {
    private TicketsManager ticketsManager;


    public TicketsModel() throws IOException {
        ticketsManager = new TicketsManager();
    }

    public void saveTicketInformation(String uuid, boolean isValid, Events selectedEvent, String ticketType) throws ApplicationWideException {
        ticketsManager.saveTicketInformation(uuid, isValid, selectedEvent, ticketType);
    }
}
