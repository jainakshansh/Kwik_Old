package me.akshanshjain.kwik.Objects;

import java.util.List;

public class EventItem {

    private String eventName;
    private String eventDescription;
    private String eventDate;
    private String eventTime;
    private List<String> eventAttendees;
    private boolean isHost;

    //Private Empty constructor to disable parameter less initialization.
    private EventItem() {
    }

    public EventItem(String eventName, String eventDescription, String eventDate, String eventTime, List<String> eventAttendees, boolean isHost) {
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.eventAttendees = eventAttendees;
        this.isHost = isHost;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getEventTime() {
        return eventTime;
    }

    public List<String> getEventAttendees() {
        return eventAttendees;
    }

    public boolean isHost() {
        return isHost;
    }
}
