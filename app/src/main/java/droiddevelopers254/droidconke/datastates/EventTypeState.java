package droiddevelopers254.droidconke.datastates;

import com.google.firebase.database.DatabaseError;

import java.util.List;

import droiddevelopers254.droidconke.models.EventTypeModel;

public class EventTypeState {
    private List<EventTypeModel> eventTypeModel;
    private String  databaseError;

    public EventTypeState(List<EventTypeModel> eventTypeModel) {
        this.eventTypeModel = eventTypeModel;
        this.databaseError= null;
    }

    public EventTypeState(String  databaseError) {
        this.databaseError = databaseError;
        this.eventTypeModel= null;
    }

    public List<EventTypeModel> getEventTypeModel() {
        return eventTypeModel;
    }

    public String getDatabaseError() {
        return databaseError;
    }
}
