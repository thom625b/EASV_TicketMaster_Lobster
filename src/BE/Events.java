package BE;

import java.time.LocalDate;
import java.util.List;

public class Events {

    private String eventAddress, eventName, eventCity, eventDescription;
    private int eventID, eventStatus, eventRemainingDays, eventParticipants, eventZipCode;
    private List<Users> users;
    private LocalDate eventDate;

    public Events(String eventName, LocalDate eventDate, int eventStatus, int eventRemainingDays, int eventParticipants, String eventAddress, int eventZipCode, String eventCity, String eventDescription) {
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

    public Events(int eventID, String eventName, LocalDate eventDate, int eventStatus, int eventRemainingDays, int eventParticipants, String eventAddress, int eventZipCode, String eventCity, String eventDescription) {
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

    public LocalDate getEventDate() {
        return eventDate;
    }

    public int getEventID() {
        return eventID;
    }

    public int getEventStatus() {
        return eventStatus;
    }

    public int getEventRemainingDays() {
        return eventRemainingDays;
    }

    public int getEventParticipants() {
        return eventParticipants;
    }

    public String getEventAddress() {
        return eventAddress;
    }

    public int getEventZipCode() {
        return eventZipCode;
    }

    public String getEventCity() {
        return eventCity;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    private List<Users> getUsers() {
        return users;
    }

    private void setUsers(List<Users> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Events{" +
                "eventName='" + eventName + '\'' +
                ", eventDate=" + eventDate +
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
