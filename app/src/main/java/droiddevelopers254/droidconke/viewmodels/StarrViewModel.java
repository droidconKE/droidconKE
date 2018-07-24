package droiddevelopers254.droidconke.viewmodels;

import android.arch.lifecycle.ViewModel;

import droiddevelopers254.droidconke.repository.StarrSessionRepo;

public class StarrViewModel extends ViewModel {
    private StarrSessionRepo starrSessionRepo;

    public StarrViewModel(){
        starrSessionRepo = new StarrSessionRepo();
    }

    public void updateSession(int sessionId, boolean isStarred){
        starrSessionRepo.updateSession(sessionId, isStarred);
    }

    public void isSessionStarred(int sessionId){
        starrSessionRepo.isSessionStarred(sessionId);
    }
}
