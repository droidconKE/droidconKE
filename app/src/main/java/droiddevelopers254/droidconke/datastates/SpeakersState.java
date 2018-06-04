package droiddevelopers254.droidconke.datastates;

import com.google.firebase.database.DatabaseError;

import java.util.List;

import droiddevelopers254.droidconke.models.SpeakersModel;

public class SpeakersState {
    private SpeakersModel speakersModel;
    private DatabaseError databaseError;

    public SpeakersState(SpeakersModel speakersModel) {
        this.speakersModel = speakersModel;
    }

    public SpeakersState(DatabaseError databaseError) {
        this.databaseError = databaseError;
    }

    public SpeakersModel getSpeakersModel() {
        return speakersModel;
    }

    public void setSpeakersModel(SpeakersModel speakersModel) {
        this.speakersModel = speakersModel;
    }

    public DatabaseError getDatabaseError() {
        return databaseError;
    }

    public void setDatabaseError(DatabaseError databaseError) {
        this.databaseError = databaseError;
    }
}
