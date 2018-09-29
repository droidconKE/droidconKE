package droiddevelopers254.droidconke.datastates;

import com.google.firebase.database.DatabaseError;

import java.util.List;

import droiddevelopers254.droidconke.models.SpeakersModel;

public class SpeakersState {
    private List<SpeakersModel> speakersModel;
    private String error;

    public SpeakersState(List<SpeakersModel> speakersModel) {
        this.speakersModel = speakersModel;
    }

    public SpeakersState(String databaseError) {
        this.error = databaseError;
    }

    public List<SpeakersModel> getSpeakersModel() {
        return speakersModel;
    }

    public void setSpeakersModel(List<SpeakersModel> speakersModel) {
        this.speakersModel = speakersModel;
    }

    public String getDatabaseError() {
        return error;
    }

    public void setDatabaseError(String  databaseError) {
        this.error = databaseError;
    }
}
