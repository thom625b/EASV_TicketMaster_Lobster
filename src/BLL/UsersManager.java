package BLL;

import DAL.USER_DAO;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.IOException;

public class UsersManager {


    private USER_DAO adminDao;

    public UsersManager() throws SQLServerException, IOException {
        adminDao = new USER_DAO();
    }
}
