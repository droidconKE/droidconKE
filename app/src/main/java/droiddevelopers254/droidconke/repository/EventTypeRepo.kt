package droiddevelopers254.droidconke.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import droiddevelopers254.droidconke.datastates.EventTypeState
import droiddevelopers254.droidconke.models.EventTypeModel

class EventTypeRepo {

    val sessionData: LiveData<EventTypeState>
        get() {
            val sessionsModelMutableLiveData = MutableLiveData<EventTypeState>()
            val firebaseFirestore = FirebaseFirestore.getInstance()
            firebaseFirestore.collection("event_types")
                    .orderBy("id", Query.Direction.ASCENDING)
                    .get()
                    .addOnSuccessListener {
                        val eventTypeModelList = it.toObjects(EventTypeModel::class.java)
                        sessionsModelMutableLiveData.setValue(EventTypeState(eventTypeModelList,null))

                    }
                    .addOnFailureListener {
                        sessionsModelMutableLiveData.setValue(EventTypeState(null,it.message)) }
            return sessionsModelMutableLiveData
        }
}
