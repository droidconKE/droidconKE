package droiddevelopers254.droidconke.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;

import droiddevelopers254.droidconke.datastates.AuthenticateUserState;
import droiddevelopers254.droidconke.repository.AuthenticateUserRepo;

public class AuthenticateUserViewModel extends ViewModel {
    private MediatorLiveData<AuthenticateUserState> userStateMediatorLiveData;
    private AuthenticateUserRepo authenticateUserRepo;

    public AuthenticateUserViewModel(){
        userStateMediatorLiveData= new MediatorLiveData<>();
        authenticateUserRepo= new AuthenticateUserRepo();
    }

    public LiveData<AuthenticateUserState> getAuthenticateResponse(){

        return userStateMediatorLiveData;
    }

    public void authenticateUser(FirebaseUser firebaseUser){
        final LiveData<AuthenticateUserState> stateLiveData=authenticateUserRepo.checkUserExistence(firebaseUser);
        userStateMediatorLiveData.addSource(stateLiveData,
                userStateMediatorLiveData->{
            if (this.userStateMediatorLiveData.hasActiveObservers()){
                this.userStateMediatorLiveData.removeSource(stateLiveData);
            }
            this.userStateMediatorLiveData.setValue(userStateMediatorLiveData);
                });
    }
}
