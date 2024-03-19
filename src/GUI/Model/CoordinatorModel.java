package GUI.Model;
import BLL.CoordinatorManager;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.IOException;

public class CoordinatorModel {

    private CoordinatorManager coordinatorManager;

    public CoordinatorModel() throws SQLServerException, IOException {
        coordinatorManager = new CoordinatorManager();
    }
}
