package BE;

public class Events {


    private String eventAddress, eventName, eventDate, eventCity, eventDescription;
    private int eventID, eventStatus, eventRemainingDays, eventParticipants, eventZipCode;

    public Events(String eventName, String eventDate, int eventStatus, int eventRemainingDays, int eventParticipants, String eventAddress, int eventZipCode, String eventCity, String eventDescription) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventStatus = eventStatus;
        this.eventRemainingDays = eventRemainingDays;
        this.eventParticipants = eventParticipants;
        this.eventAddress = eventAddress;
        this.eventZipCode = eventZipCode;
        this.eventCity = eventCity;
        this.eventDescription = eventDescription;
    }


    public Events(int eventID, String eventName, String eventDate, int eventStatus, int eventRemainingDays, int eventParticipants, String eventAddress, int eventZipCode, String eventCity, String eventDescription) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventStatus = eventStatus;
        this.eventRemainingDays = eventRemainingDays;
        this.eventParticipants = eventParticipants;
        this.eventAddress = eventAddress;
        this.eventZipCode = eventZipCode;
        this.eventCity = eventCity;
        this.eventDescription = eventDescription;
    }


    public String getEventName() {
        return eventName;
    }

    private void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    private void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public int getEventID() {
        return eventID;
    }

    private void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public int getEventStatus() {
        return eventStatus;
    }

    private void setEventStatus(int eventStatus) {
        this.eventStatus = eventStatus;
    }

    public int getEventRemainingDays() {
        return eventRemainingDays;
    }

    private void setEventRemainingDays(int eventRemainingDays) {
        this.eventRemainingDays = eventRemainingDays;
    }

    public int getEventParticipants() {
        return eventParticipants;
    }

    private void setEventParticipants(int eventParticipants) {
        this.eventParticipants = eventParticipants;
    }
    public String getEventAddress() {
        return eventAddress;
    }

    private void setEventAddress(String eventAddress) {
        this.eventAddress = eventAddress;
    }

    public int getEventZipCode() {
        return eventZipCode;
    }

    private void setEventZipCode(int eventZipCode) {
        this.eventZipCode = eventZipCode;
    }

    public String getEventCity() {
        return eventCity;
    }

    public void setEventCity(String eventCity) {
        this.eventCity = eventCity;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    @Override
    public String toString() {
        return "Events{" +
                "eventName='" + eventName + '\'' +
                ", eventDate='" + eventDate + '\'' +
                ", eventID=" + eventID +
                ", eventStatus=" + eventStatus +
                ", eventRemainingDays=" + eventRemainingDays +
                ", eventParticipants=" + eventParticipants +
                ", eventAddress='" + eventAddress + '\'' +
                ", eventZipCode=" + eventZipCode +
                ", eventCity='" + eventCity + '\'' +
                ", eventDescription='" + eventDescription + '\'' +
                '}';
    }
}
