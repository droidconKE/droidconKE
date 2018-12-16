package droiddevelopers254.droidconke.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import droiddevelopers254.droidconke.datastates.AgendaState;
import droiddevelopers254.droidconke.repository.AgendaRepo;

public class AgendaViewModel extends ViewModel {
    private MediatorLiveData<AgendaState> agendaStateMediatorLiveData;
    private AgendaRepo agendaRepo;

    public AgendaViewModel(){
        agendaStateMediatorLiveData= new MediatorLiveData<>();
        agendaRepo = new AgendaRepo();
    }

    public LiveData<AgendaState> getAgendas(){
        return agendaStateMediatorLiveData;
    }
    public void fetchAgendas(){
        final LiveData<AgendaState> agendaStateLiveData= agendaRepo.getAgendaData();
        agendaStateMediatorLiveData.addSource(agendaStateLiveData,
                agendaStateMediatorLiveData ->{
                    if (this.agendaStateMediatorLiveData.hasActiveObservers()){
                        this.agendaStateMediatorLiveData.removeSource(agendaStateLiveData);
                    }
                    this.agendaStateMediatorLiveData.setValue(agendaStateMediatorLiveData);
                });
    }
}
