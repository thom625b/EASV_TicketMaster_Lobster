package BE;

public class Users {

    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String hashedPassword;
    private String userPicture;
    private Role role;


    public Users(int userId, String firstName, String lastName, String email, String hashedPassword, Role role, String userPicture) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.role = role;
        this.userPicture = userPicture;
    }

    public Users(String firstName, String lastName, String email, String hashedPassword, Role role, String userPicture) {
        this(-1, firstName, lastName, email, hashedPassword, role, userPicture);
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

    /**
     * Checks if the user is an admin.
     *
     * @return true if the user's role is ADMIN, false otherwise.
     */
    public boolean isAdmin() {
        return this.role == Role.ADMIN;
    }

    /**
     * Checks if the user is a coordinator.
     *
     * @return true if the user's role is COORDINATOR, false otherwise.
     */
    public boolean isCoordinator() {
        return this.role == Role.COORDINATOR;
    }
    public String getFirstName() {
        return firstName;
    }

    private void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    private void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    private void setEmail(String email) {
        this.email = email;
    }

   public String getUserPicture() {
        return userPicture;
    }

    public void setUserPicture(String userPicture) {
        this.userPicture = userPicture;
    }
}