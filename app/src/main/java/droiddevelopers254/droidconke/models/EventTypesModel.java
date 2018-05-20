package droiddevelopers254.droidconke.models;

public class EventTypesModel {
    private int eventImageId;
    private String eventDescription;

    public int getEventImageId() {
        return eventImageId;
    }

    public void setEventImageId(int eventImageId) {
        this.eventImageId = eventImageId;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public EventTypesModel(int eventImageId, String eventDescription) {
        this.eventImageId = eventImageId;
        this.eventDescription = eventDescription;
    }
}
