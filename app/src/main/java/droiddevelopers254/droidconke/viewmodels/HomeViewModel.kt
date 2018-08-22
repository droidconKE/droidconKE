package droiddevelopers254.droidconke.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.ViewModel

import droiddevelopers254.droidconke.datastates.FiltersState
import droiddevelopers254.droidconke.datastates.UpdateTokenState
import droiddevelopers254.droidconke.models.FiltersModel
import droiddevelopers254.droidconke.repository.TopicFiltersRepo
import droiddevelopers254.droidconke.repository.TypeFiltersRepo
import droiddevelopers254.droidconke.repository.UpdateTokenRepo

class HomeViewModel : ViewModel() {
    private val filtersList: LiveData<List<FiltersModel>>? = null
    private val typeFiltersRepo: TypeFiltersRepo = TypeFiltersRepo()
    private val filtersStateMediatorLiveData: MediatorLiveData<FiltersState> = MediatorLiveData()
    private val stateMediatorLiveData: MediatorLiveData<FiltersState> = MediatorLiveData()
    private val updateTokenStateMediatorLiveData: MediatorLiveData<UpdateTokenState> = MediatorLiveData()
    private val topicFiltersRepo: TopicFiltersRepo = TopicFiltersRepo()
    private val updateTokenRepo: UpdateTokenRepo = UpdateTokenRepo()

    val typeFiltersResponse: LiveData<FiltersState>
        get() = filtersStateMediatorLiveData
    val topicFiltersResponse: LiveData<FiltersState>
        get() = stateMediatorLiveData
    val updateTokenResponse: LiveData<UpdateTokenState>
        get() = updateTokenStateMediatorLiveData

    fun getTypeFilters() {
        val filtersStateLiveData = typeFiltersRepo.filters
        filtersStateMediatorLiveData.addSource(filtersStateLiveData
        ) { filtersStateMediatorLiveData ->
            if (this.filtersStateMediatorLiveData.hasActiveObservers()) {
                this.filtersStateMediatorLiveData.removeSource(filtersStateLiveData)
            }
            this.filtersStateMediatorLiveData.setValue(filtersStateMediatorLiveData)
        }
    }

    fun getTopicFilters() {
        val filtersStateLiveData = topicFiltersRepo.filters
        stateMediatorLiveData.addSource(filtersStateLiveData
        ) { stateMediatorLiveData ->
            if (this.stateMediatorLiveData.hasActiveObservers()) {
                this.stateMediatorLiveData.removeSource(filtersStateLiveData)
            }
            this.stateMediatorLiveData.setValue(stateMediatorLiveData)
        }

    }
    fun updateToken(userId: String, refreshToken: String) {
        val updateTokenStateLiveData = updateTokenRepo.updateToken(userId, refreshToken)
        updateTokenStateMediatorLiveData.addSource(updateTokenStateLiveData
        ) { updateTokenStateMediatorLiveData ->
            if (this.updateTokenStateMediatorLiveData.hasActiveObservers()) {
                this.updateTokenStateMediatorLiveData.removeSource(updateTokenStateLiveData)
            }
            this.updateTokenStateMediatorLiveData.setValue(updateTokenStateMediatorLiveData)
        }
    }
}
