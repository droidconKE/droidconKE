package droiddevelopers254.droidconke.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import droiddevelopers254.droidconke.datastates.FiltersState
import droiddevelopers254.droidconke.datastates.UpdateTokenState
import droiddevelopers254.droidconke.models.FiltersModel
import droiddevelopers254.droidconke.repository.UpdateTokenRepo

class HomeViewModel : ViewModel() {
    private val filtersList: LiveData<List<FiltersModel>>? = null
    private val filtersStateMediatorLiveData: MediatorLiveData<FiltersState> = MediatorLiveData()
    private val stateMediatorLiveData: MediatorLiveData<FiltersState> = MediatorLiveData()
    private val updateTokenStateMediatorLiveData: MediatorLiveData<UpdateTokenState> = MediatorLiveData()
    private val updateTokenRepo: UpdateTokenRepo = UpdateTokenRepo()

    val typeFiltersResponse: LiveData<FiltersState>
        get() = filtersStateMediatorLiveData
    val topicFiltersResponse: LiveData<FiltersState>
        get() = stateMediatorLiveData
    val updateTokenResponse: LiveData<UpdateTokenState>
        get() = updateTokenStateMediatorLiveData


    fun updateToken(userId: String, refreshToken: String) {
        val updateTokenStateLiveData = updateTokenRepo.updateToken(userId, refreshToken)
        updateTokenStateMediatorLiveData.addSource(updateTokenStateLiveData
        ) { updateTokenStateMediatorLiveData ->
            when {
                this.updateTokenStateMediatorLiveData.hasActiveObservers() -> this.updateTokenStateMediatorLiveData.removeSource(updateTokenStateLiveData)
            }
            this.updateTokenStateMediatorLiveData.setValue(updateTokenStateMediatorLiveData)
        }
    }
}
