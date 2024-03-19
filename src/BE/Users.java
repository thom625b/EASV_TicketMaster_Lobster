package BE;

public class Users {

    private int userId;
    private String username;
    private String hashedPassword;


    public Users(int userId, String username, String hashedPassword) {
        this.userId = userId;
        this.username = username;
        this.hashedPassword = hashedPassword;
    }

    public enum ROLE{
        ADMIN,
        COORDINATOR;
    }

    // Getters and setters
    private int getUserId() {
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
}
