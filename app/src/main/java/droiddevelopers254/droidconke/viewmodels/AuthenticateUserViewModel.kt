package droiddevelopers254.droidconke.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.ViewModel

import com.google.firebase.auth.FirebaseUser

import droiddevelopers254.droidconke.datastates.AuthenticateUserState
import droiddevelopers254.droidconke.repository.AuthenticateUserRepo

class AuthenticateUserViewModel : ViewModel() {
    private val userStateMediatorLiveData: MediatorLiveData<AuthenticateUserState> = MediatorLiveData()
    private val authenticateUserRepo: AuthenticateUserRepo = AuthenticateUserRepo()

    val authenticateResponse: LiveData<AuthenticateUserState>
        get() = userStateMediatorLiveData

    fun authenticateUser(firebaseUser: FirebaseUser) {
        val stateLiveData = authenticateUserRepo.checkUserExistence(firebaseUser)
        userStateMediatorLiveData.addSource(stateLiveData
        ) { userStateMediatorLiveData ->
            if (this.userStateMediatorLiveData.hasActiveObservers()) {
                this.userStateMediatorLiveData.removeSource(stateLiveData)
            }
            this.userStateMediatorLiveData.setValue(userStateMediatorLiveData)
        }
    }
}
