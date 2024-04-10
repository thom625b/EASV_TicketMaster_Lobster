package BE;

public class Tickets {

    private String qrHashCode;
    private String ticketType;
    private int ticketPrice;
    private boolean isValid;
    private int ticketID;


    // With ID
    public Tickets(String qrHashCode, String ticketType, int ticketPrice, boolean isValid, int ticketID) {
        this.qrHashCode = qrHashCode;
        this.ticketType = ticketType;
        this.ticketPrice = ticketPrice;
        this.isValid = isValid;
        this.ticketID = ticketID;
    }


    // Without ID
    public Tickets(String qrHashCode, String ticketType, int ticketPrice, boolean isValid) {
        this.qrHashCode = qrHashCode;
        this.ticketType = ticketType;
        this.ticketPrice = ticketPrice;
        this.isValid = isValid;
    }

    public Tickets(String ticketType, boolean isValid, String qrHashCode) {
        this.ticketType = ticketType;
        this.isValid = isValid;
        this.qrHashCode = qrHashCode;
    }

    public String getQrHashCode() {
        return qrHashCode;
    }

    public void setQrHashCode(String qrHashCode) {
        this.qrHashCode = qrHashCode;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public int getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(int ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public int getTicketID() {
        return ticketID;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    @Override
    public String toString() {
        return "Tickets{" +
                "qrHashCode='" + qrHashCode + '\'' +
                ", ticketType='" + ticketType + '\'' +
                ", ticketPrice=" + ticketPrice +
                ", isValid=" + isValid +
                ", ticketID=" + ticketID +
                '}';
    }
}
