package GUI.Model;

import BLL.AdminManager;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.IOException;

public class AdminModel {
    private AdminManager adminManager;

    public AdminModel() throws SQLServerException, IOException {
        adminManager = new AdminManager();
    }

}
