package droiddevelopers254.droidconke.datastates;

import com.google.firebase.database.DatabaseError;

import droiddevelopers254.droidconke.models.SessionsModel;

public class SessionDataState {
    private SessionsModel sessionsModel;
    private DatabaseError databaseError;

    public SessionDataState(SessionsModel sessionsModel) {
        this.sessionsModel = sessionsModel;
        this.databaseError= null;
    }

    public SessionDataState(DatabaseError databaseError) {
        this.databaseError = databaseError;
        this.sessionsModel=null;
    }

    public SessionsModel getSessionsModel() {
        return sessionsModel;
    }

    public DatabaseError getDatabaseError() {
        return databaseError;
    }
}
