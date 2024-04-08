package BE;

public class Costumers {
    private String costumerEmail;
    private String costumerFName;
    private String costumerLName;
    private int costumerID;


    //With ID
    public Costumers(String costumerEmail, String costumerFName, String costumerLName, int costumerID) {
        this.costumerEmail = costumerEmail;
        this.costumerFName = costumerFName;
        this.costumerLName = costumerLName;
        this.costumerID = costumerID;
    }

    //Without ID
    public Costumers(String costumerEmail, String costumerFName, String costumerLName) {
        this.costumerEmail = costumerEmail;
        this.costumerFName = costumerFName;
        this.costumerLName = costumerLName;
    }

    public String getCostumerEmail() {
        return costumerEmail;
    }

    public void setCostumerEmail(String costumerEmail) {
        this.costumerEmail = costumerEmail;
    }

    public String getCostumerFName() {
        return costumerFName;
    }

    public void setCostumerFName(String costumerFName) {
        this.costumerFName = costumerFName;
    }

    public String getCostumerLName() {
        return costumerLName;
    }

    public void setCostumerLName(String costumerLName) {
        this.costumerLName = costumerLName;
    }

    public int getCostumerID() {
        return costumerID;
    }

    public void setCostumerID(int costumerID) {
        this.costumerID = costumerID;
    }

    @Override
    public String toString() {
        return "Costumers{" +
                "costumerEmail='" + costumerEmail + '\'' +
                ", costumerFName='" + costumerFName + '\'' +
                ", costumerLName='" + costumerLName + '\'' +
                ", costumerID=" + costumerID +
                '}';
    }
}
