package BE;

public class Coordinator {

    private int coordinatorId;
    private String username;
    private String hashedPassword;


    public Coordinator(int coordinatorId, String username, String hashedPassword) {
        this.coordinatorId = coordinatorId;
        this.username = username;
        this.hashedPassword = hashedPassword;
    }

    // Getters and setters
    private int getCoordinatorId() {
        return coordinatorId;
    }

    private void setCoordinatorId(int coordinatorId) {
        this.coordinatorId = coordinatorId;
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
