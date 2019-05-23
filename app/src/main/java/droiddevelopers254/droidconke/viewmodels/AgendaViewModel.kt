package droiddevelopers254.droidconke.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import droiddevelopers254.droidconke.datastates.Result
import droiddevelopers254.droidconke.models.AgendaModel
import droiddevelopers254.droidconke.repository.AgendaRepo
import droiddevelopers254.droidconke.utils.NonNullMediatorLiveData
import kotlinx.coroutines.launch

class AgendaViewModel(private val agendaRepo: AgendaRepo) : ViewModel() {
    private val agendaStateMediatorLiveData = NonNullMediatorLiveData<List<AgendaModel>>()
    private val agendaError = NonNullMediatorLiveData<String>()

    fun getAgendasResponse(): LiveData<List<AgendaModel>> = agendaStateMediatorLiveData

    fun getAgendaError(): LiveData<String> = agendaError

    fun fetchAgendas() {
        viewModelScope.launch {
            when (val value = agendaRepo.agendaData()) {
                is Result.Success -> agendaStateMediatorLiveData.postValue(value.data)
                is Result.Error -> agendaError.postValue(value.exception)
            }
        }
    }
}
