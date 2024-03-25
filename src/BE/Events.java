package BE;

public class Events {


    private String adresse, eventName, date, description;
    private int eventID, eventStatus, remainingDays, eventParticipants, zipCode;


    public Events(String eventName, String date, int eventStatus, int remainingDays, int eventParticipants, String adresse, int zipCode, String description) {
        this.eventName = eventName;
        this.date = date;
        this.eventStatus = eventStatus;
        this.remainingDays = remainingDays;
        this.eventParticipants = eventParticipants;
        this.adresse = adresse;
        this.zipCode = zipCode;
        this.description = description;
    }

    public Events(String eventName, String date, int eventID, int eventStatus, int remainingDays, int eventParticipants, String adresse, int zipCode, String description) {
        this.eventName = eventName;
        this.date = date;
        this.eventID = eventID;
        this.eventStatus = eventStatus;
        this.remainingDays = remainingDays;
        this.eventParticipants = eventParticipants;
        this.adresse = adresse;
        this.zipCode = zipCode;
        this.description = description;
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
    private String getAdresse() {
        return adresse;
    }

    private void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    private int getZipCode() {
        return zipCode;
    }

    private void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }
}
