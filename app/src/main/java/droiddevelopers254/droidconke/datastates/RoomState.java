package droiddevelopers254.droidconke.datastates;

import com.google.firebase.database.DatabaseError;

import java.util.List;

import droiddevelopers254.droidconke.models.RoomModel;

public class RoomState {
    private RoomModel roomModel;
    private DatabaseError databaseError;

    public RoomState(RoomModel roomModel) {
        this.roomModel = roomModel;
        this.roomModel= roomModel;
    }

    public RoomState(DatabaseError databaseError) {
        this.databaseError = databaseError;
        this.databaseError= databaseError;
    }

    public RoomModel getRoomModel() {
        return roomModel;
    }

    public DatabaseError getDatabaseError() {
        return databaseError;
    }
}
