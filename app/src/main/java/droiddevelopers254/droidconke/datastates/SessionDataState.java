package droiddevelopers254.droidconke.datastates;

import com.google.firebase.database.DatabaseError;

import droiddevelopers254.droidconke.models.SessionsModel;

public class SessionDataState {
    private SessionsModel sessionsModel;
    private String error;

    public SessionDataState(SessionsModel sessionsModel) {
        this.sessionsModel = sessionsModel;
        this.error= null;
    }

    public SessionDataState(String error) {
        this.error = error;
        this.sessionsModel=null;
    }

    public SessionsModel getSessionsModel() {
        return sessionsModel;
    }

    public String getDatabaseError() {
        return error;
    }
}
