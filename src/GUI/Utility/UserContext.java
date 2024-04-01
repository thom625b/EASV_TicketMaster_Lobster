package GUI.Utility;

import BE.Users;

public class UserContext {
    private static UserContext instance;
    private Users currentUser;

    private UserContext() {}

    public static UserContext getInstance() {
        if (instance == null) {
            instance = new UserContext();
        }
        return instance;
    }



    public void setCurrentUser(Users user) {
        this.currentUser = user;
    }

    public Users getCurrentUser() {
        return this.currentUser;
    }

    public int getCurrentUserId() {
        if (currentUser != null) {
            return currentUser.getUserId();
        } else {
            // Handle case where there is no user logged in
            return -1; // or throw an exception.
        }
    }


    public void clear() {
        currentUser = null;
    }
}
