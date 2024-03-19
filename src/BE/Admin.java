package BE;

public class Admin {

    private int adminId;
    private String username;
    private String hashedPassword;

    public Admin(int adminId, String username, String hashedPassword) {
        this.adminId = adminId;
        this.username = username;
        this.hashedPassword = hashedPassword;
    }

    // Getters and setters
    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
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
