package droiddevelopers254.droidconke.datastates;

import com.google.firebase.database.DatabaseError;

import java.util.List;

import droiddevelopers254.droidconke.models.EventTypeModel;

public class EventTypeState {
    private List<EventTypeModel> eventTypeModel;
    private DatabaseError databaseError;

    public EventTypeState(List<EventTypeModel> eventTypeModel) {
        this.eventTypeModel = eventTypeModel;
        this.databaseError= null;
    }

    public EventTypeState(DatabaseError databaseError) {
        this.databaseError = databaseError;
        this.eventTypeModel= null;
    }

    public List<EventTypeModel> getEventTypeModel() {
        return eventTypeModel;
    }

    public DatabaseError getDatabaseError() {
        return databaseError;
    }
}
