package droiddevelopers254.droidconke.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.ViewModel

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
            if (this.infoViewModelMediatorLiveData.hasActiveObservers()) {
                this.infoViewModelMediatorLiveData.removeSource(travelInfoModelLiveData)
            }
            this.infoViewModelMediatorLiveData.setValue(infoViewModelMediatorLiveData)
        }
    }
}
