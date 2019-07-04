package droiddevelopers254.droidconke.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import droiddevelopers254.droidconke.datastates.Result
import droiddevelopers254.droidconke.models.Announcement
import droiddevelopers254.droidconke.repository.AnnouncementRepo
import droiddevelopers254.droidconke.utils.NonNullMediatorLiveData
import kotlinx.coroutines.launch

class AnnouncementViewModel(private val announcementRepo: AnnouncementRepo) : ViewModel() {
    private val announcementMediatorLiveData = NonNullMediatorLiveData<List<Announcement>>()
    private val announcementError = NonNullMediatorLiveData<String>()


    fun getAnnouncementsResponse(): LiveData<List<Announcement>> = announcementMediatorLiveData

    fun getAnnouncementError(): LiveData<String> = announcementError

    fun getAnnouncements() {
        viewModelScope.launch {
            when (val value = announcementRepo.getAnnouncements()) {
                is Result.Success -> announcementMediatorLiveData.postValue(value.data)
                is Result.Error -> announcementError.postValue(value.exception)
            }
        }
    }
}