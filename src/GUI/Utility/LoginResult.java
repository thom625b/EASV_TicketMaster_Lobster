package GUI.Utility;

import BE.Users;

public class LoginResult {

    private final boolean success;
    private final Users.Role role;

    public LoginResult(boolean success, Users.Role role) {
        this.success = success;
        this.role = role;
    }

    public boolean isSuccess() {
        return success;
    }

    public Users.Role getRole() {
        return role;
    }
}
