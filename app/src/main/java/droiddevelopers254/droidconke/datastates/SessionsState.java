package droiddevelopers254.droidconke.datastates;

import com.google.firebase.database.DatabaseError;

import java.util.List;

import droiddevelopers254.droidconke.models.SessionsModel;

public class SessionsState {
    private List<SessionsModel> sessionsModel;
    private DatabaseError databaseError;

    public SessionsState(List<SessionsModel> sessionsModel) {
        this.sessionsModel = sessionsModel;
        this.databaseError= null;
    }

    public SessionsState(DatabaseError databaseError) {
        this.databaseError = databaseError;
        this.sessionsModel = null;
    }

    public List<SessionsModel> getSessionsModel() {
        return sessionsModel;
    }

    public DatabaseError getDatabaseError() {
        return databaseError;
    }
}
