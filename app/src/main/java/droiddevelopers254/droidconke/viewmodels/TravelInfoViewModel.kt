package droiddevelopers254.droidconke.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import droiddevelopers254.droidconke.models.TravelInfoModel
import droiddevelopers254.droidconke.repository.TravelInfoRepo

class TravelInfoViewModel(application: Application) : AndroidViewModel(application) {
    private val infoViewModelMediatorLiveData: MediatorLiveData<TravelInfoModel> = MediatorLiveData()
    private val travelInfoRepo: TravelInfoRepo = TravelInfoRepo(application)

    val travelInfo: LiveData<TravelInfoModel>
        get() = infoViewModelMediatorLiveData


    fun fetchRemoteConfigValues() {
        val travelInfoModelLiveData = travelInfoRepo.travelInfo
        infoViewModelMediatorLiveData.addSource(travelInfoModelLiveData
        ) { infoViewModelMediatorLiveData ->
            when {
                this.infoViewModelMediatorLiveData.hasActiveObservers() -> this.infoViewModelMediatorLiveData.removeSource(travelInfoModelLiveData)
            }
            this.infoViewModelMediatorLiveData.setValue(infoViewModelMediatorLiveData)
        }
    }
}
