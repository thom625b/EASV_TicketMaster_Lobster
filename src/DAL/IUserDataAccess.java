package DAL;

import BE.Users;
import CostumException.ApplicationWideException;

import java.util.List;

public interface IUserDataAccess {
    List<Users> getAllUsers() throws ApplicationWideException;

    Users addUser(Users newCategory) throws ApplicationWideException;

    void deleteUser(Users users) throws ApplicationWideException;

    void updateUsers(Users users) throws ApplicationWideException;

}
