package GUI.Controllers;

import CostumException.ApplicationWideException;
import GUI.Model.EventsModel;
import GUI.Model.UsersModel;

public interface IController {
    void setModel(UsersModel usersModel) throws ApplicationWideException;


}
