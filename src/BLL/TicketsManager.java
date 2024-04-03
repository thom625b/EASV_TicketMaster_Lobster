package BLL;

import DAL.Tickets_DAO;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.IOException;

public class TicketsManager {

    private final Tickets_DAO ticketsDao;

    public TicketsManager() throws IOException {
        ticketsDao = new Tickets_DAO();
    }



}
