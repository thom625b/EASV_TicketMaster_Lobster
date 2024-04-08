package BE;

public class Tickets {

    private String pdf;
    private String qrHashCode;
    private String ticketType;
    private int ticketID;


    public Tickets(String pdf, int ticketID) {
        this.pdf = pdf;
        this.ticketID = ticketID;
    }

    public Tickets(String pdf) {
        this.pdf = pdf;
    }

    private String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public int getTicketID() {
        return ticketID;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }
}
