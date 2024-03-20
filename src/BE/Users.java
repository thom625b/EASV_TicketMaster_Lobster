package BE;

public class Users {

    private int userId;
    private String username;
    private String hashedPassword;
    private Role role;



    public Users(int userId, String username, String hashedPassword, Role role) {
        this.userId = userId;
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.role = role;
    }

    public enum Role{
        ADMIN,
        COORDINATOR;
    }

    // Getters and setters
    public int getUserId() {
        return userId;
    }

    private void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public Role getRole() {
        return role;
    }

    private void setRole(Role role) {
        this.role = role;
    }
}
