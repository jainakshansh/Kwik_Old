package me.akshanshjain.kwik.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class EventItem implements Parcelable {

    private String eventName;
    private String eventDescription;
    private String eventDate;
    private String eventTime;
    private String eventLocation;
    private List<String> eventAttendees;
    private boolean isHosting;
    private String eventKey;

    //Private Empty constructor to disable parameter less initialization.
    private EventItem() {
    }

    public EventItem(String eventName, String eventDescription, String eventDate, String eventTime, String eventLocation,
                     List<String> eventAttendees, boolean isHosting, String eventKey) {
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.eventLocation = eventLocation;
        this.eventAttendees = eventAttendees;
        this.isHosting = isHosting;
        this.eventKey = eventKey;
    }

    protected EventItem(Parcel in) {
        eventName = in.readString();
        eventDescription = in.readString();
        eventDate = in.readString();
        eventTime = in.readString();
        eventLocation = in.readString();
        eventAttendees = in.createStringArrayList();
        isHosting = in.readByte() != 0;
        eventKey = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(eventName);
        dest.writeString(eventDescription);
        dest.writeString(eventDate);
        dest.writeString(eventTime);
        dest.writeString(eventLocation);
        dest.writeStringList(eventAttendees);
        dest.writeByte((byte) (isHosting ? 1 : 0));
        dest.writeString(eventKey);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<EventItem> CREATOR = new Creator<EventItem>() {
        @Override
        public EventItem createFromParcel(Parcel in) {
            return new EventItem(in);
        }

        @Override
        public EventItem[] newArray(int size) {
            return new EventItem[size];
        }
    };

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

    public String getEventLocation() {
        return eventLocation;
    }

    public List<String> getEventAttendees() {
        return eventAttendees;
    }

    public boolean isHosting() {
        return isHosting;
    }

    public String getEventKey() {
        return eventKey;
    }
}
