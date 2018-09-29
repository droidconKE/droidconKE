package droiddevelopers254.droidconke.datastates;

import com.google.firebase.database.DatabaseError;

import java.util.List;

import droiddevelopers254.droidconke.models.RoomModel;

public class RoomState {
    private RoomModel roomModel;
    private String error;

    public RoomState(RoomModel roomModel) {
        this.roomModel = roomModel;
        this.error= null;
    }

    public RoomState(String  databaseError) {
        this.error = databaseError;
        this.roomModel= null;
    }

    public RoomModel getRoomModel() {
        return roomModel;
    }

    public String  getDatabaseError() {
        return error;
    }
}
