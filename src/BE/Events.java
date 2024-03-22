package BE;

public class Events {

    private String eventName;
    private String date;
    private int eventID, eventStatus, remainingDays, eventParticipants;


    private Events(String eventName, String date, int eventStatus, int remainingDays, int eventParticipants) {
        this.eventName = eventName;
        this.date = date;
        this.eventStatus = eventStatus;
        this.remainingDays = remainingDays;
        this.eventParticipants = eventParticipants;
    }

    private Events(String eventName, String date, int eventID, int eventStatus, int remainingDays, int eventParticipants) {
        this.eventName = eventName;
        this.date = date;
        this.eventID = eventID;
        this.eventStatus = eventStatus;
        this.remainingDays = remainingDays;
        this.eventParticipants = eventParticipants;
    }

    private String getEventName() {
        return eventName;
    }

    private void setEventName(String eventName) {
        this.eventName = eventName;
    }

    private String getDate() {
        return date;
    }

    private void setDate(String date) {
        this.date = date;
    }

    private int getEventID() {
        return eventID;
    }

    private void setEventID(int eventID) {
        this.eventID = eventID;
    }

    private int getEventStatus() {
        return eventStatus;
    }

    private void setEventStatus(int eventStatus) {
        this.eventStatus = eventStatus;
    }

    private int getRemainingDays() {
        return remainingDays;
    }

    private void setRemainingDays(int remainingDays) {
        this.remainingDays = remainingDays;
    }

    private int getEventParticipants() {
        return eventParticipants;
    }

    private void setEventParticipants(int eventParticipants) {
        this.eventParticipants = eventParticipants;
    }
}
