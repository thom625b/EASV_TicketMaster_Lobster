package BLL;

import DAL.USER_DAO;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.IOException;

public class AdminManager {


    private USER_DAO adminDao;

    public AdminManager() throws SQLServerException, IOException {
        adminDao = new USER_DAO();
    }
}
