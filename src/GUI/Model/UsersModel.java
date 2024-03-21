package GUI.Model;

import BLL.UsersManager;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.IOException;

public class UsersModel {
    private UsersManager usersManager;

    public UsersModel() throws SQLServerException, IOException {
        usersManager = new UsersManager();
    }

}
