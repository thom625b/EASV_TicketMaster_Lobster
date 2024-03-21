package BE.MOCK;

import java.time.LocalDate;

public class Event_Mock {
    private String code;
    private String eventName;
    private LocalDate startDate;
    private String status;
    private int daysLeft;
    private int registeredParticipants;

    // Constructor
    public Event_Mock(String code, String eventName, LocalDate startDate, String status, int daysLeft, int registeredParticipants) {
        this.code = code;
        this.eventName = eventName;
        this.startDate = startDate;
        this.status = status;
        this.daysLeft = daysLeft;
        this.registeredParticipants = registeredParticipants;
    }

    // Getters and setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(int daysLeft) {
        this.daysLeft = daysLeft;
    }

    public int getRegisteredParticipants() {
        return registeredParticipants;
    }

    public void setRegisteredParticipants(int registeredParticipants) {
        this.registeredParticipants = registeredParticipants;
    }
}