package droiddevelopers254.droidconke.datastates;

import com.google.firebase.database.DatabaseError;

import java.util.List;

import droiddevelopers254.droidconke.models.AgendaModel;

public class AgendaState {
    private List<AgendaModel> agendaModel;
    private DatabaseError databaseError;

    public AgendaState(List<AgendaModel> agendaModel) {
        this.agendaModel = agendaModel;
    }

    public AgendaState(DatabaseError databaseError) {
        this.databaseError = databaseError;
    }

    public List<AgendaModel> getAgendaModel() {
        return agendaModel;
    }

    public DatabaseError getDatabaseError() {
        return databaseError;
    }
}
