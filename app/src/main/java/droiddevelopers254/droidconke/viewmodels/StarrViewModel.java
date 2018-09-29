package droiddevelopers254.droidconke.viewmodels;

import android.arch.lifecycle.ViewModel;

import droiddevelopers254.droidconke.repository.RoomStarrSessionRepo;

public class StarrViewModel extends ViewModel {
    private RoomStarrSessionRepo roomStarrSessionRepo;

    public StarrViewModel(){
        roomStarrSessionRepo = new RoomStarrSessionRepo();
    }

}
