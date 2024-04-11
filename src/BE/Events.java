package BE;

import java.time.LocalDate;
import java.util.List;

public class Events {

    private int coordinatorId;
    private String eventAddress, eventName, eventCity, eventDescription, eventStartTime, eventEndTime;


    private int eventID, eventStatus, eventRemainingDays, eventParticipants, eventZipCode;
    private List<Users> users;
    private LocalDate eventDate;

    public Events(String eventName, LocalDate eventDate, int eventStatus, int eventRemainingDays, int eventParticipants, String eventAddress, int eventZipCode, String eventCity, String eventDescription, int coordinatorId, String eventStartTime, String eventEndTime) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventStatus = eventStatus;
        this.eventRemainingDays = eventRemainingDays;
        this.eventParticipants = eventParticipants;
        this.eventAddress = eventAddress;
        this.eventZipCode = eventZipCode;
        this.eventCity = eventCity;
        this.eventDescription = eventDescription;
        this.coordinatorId = coordinatorId;
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
    }

    public Events(int eventID, String eventName, LocalDate eventDate, int eventStatus, int eventRemainingDays, int eventParticipants, String eventAddress, int eventZipCode, String eventCity, String eventDescription, String eventStartTime, String eventEndTime) {
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
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName){
        this.eventName = eventName;
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

    public void setCoordinatorId(int currentCoordinatorId) {
        this.coordinatorId = currentCoordinatorId;
    }
    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public String getEventStartTime() {
        return eventStartTime;
    }

    public void setEventStartTime(String eventStartTime) {
        this.eventStartTime = eventStartTime;
    }

    public String getEventEndTime() {
        return eventEndTime;
    }

    public void setEventEndTime(String eventEndTime) {
        this.eventEndTime = eventEndTime;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Events events = (Events) o;
        return eventID == events.eventID;
    }

    @Override
    public int hashCode() {
        return eventID;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public void setEventCity(String eventCity) {
        this.eventCity = eventCity;
    }

    public void setEventZipCode(int eventZipCode) {
        this.eventZipCode = eventZipCode;
    }

    public void setEventAddress(String eventAddress) {
        this.eventAddress = eventAddress;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }
}
