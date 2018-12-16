package droiddevelopers254.droidconke.viewmodels;

import androidx.lifecycle.ViewModel;

import droiddevelopers254.droidconke.repository.RoomStarrSessionRepo;

public class StarrViewModel extends ViewModel {
    private RoomStarrSessionRepo roomStarrSessionRepo;

    public StarrViewModel(){
        roomStarrSessionRepo = new RoomStarrSessionRepo();
    }

}
