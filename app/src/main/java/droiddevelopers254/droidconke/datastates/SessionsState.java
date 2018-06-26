package droiddevelopers254.droidconke.datastates;

import com.google.firebase.database.DatabaseError;

import java.util.List;

import droiddevelopers254.droidconke.models.SessionsModel;

public class SessionsState {
    private List<SessionsModel> sessionsModel;
    private String error;

    public SessionsState(List<SessionsModel> sessionsModel) {
        this.sessionsModel = sessionsModel;
        this.error= null;
    }

    public SessionsState(String error) {
        this.error = error;
        this.sessionsModel = null;
    }

    public List<SessionsModel> getSessionsModel() {
        return sessionsModel;
    }

    public String getDatabaseError() {
        return error;
    }
}
