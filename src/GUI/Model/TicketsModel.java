package GUI.Model;
import BLL.TicketsManager;

import java.io.IOException;


public class TicketsModel {
    private TicketsManager ticketsManager;


    public TicketsModel() throws IOException {
        ticketsManager = new TicketsManager();
    }
}
