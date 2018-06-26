package droiddevelopers254.droidconke.datastates;

import com.google.firebase.database.DatabaseError;

import java.util.List;

import droiddevelopers254.droidconke.models.AgendaModel;

public class AgendaState {
    private List<AgendaModel> agendaModel;
    private String error;

    public AgendaState(List<AgendaModel> agendaModel) {
        this.agendaModel = agendaModel;
    }

    public AgendaState(String  databaseError) {
        this.error = databaseError;
    }

    public List<AgendaModel> getAgendaModel() {
        return agendaModel;
    }

    public String getDatabaseError() {
        return error;
    }
}
