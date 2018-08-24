package droiddevelopers254.droidconke.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import droiddevelopers254.droidconke.datastates.AgendaState
import droiddevelopers254.droidconke.models.AgendaModel

class AgendaRepo {

    val agendaData: LiveData<AgendaState>
        get() {
            val sessionsModelMutableLiveData = MutableLiveData<AgendaState>()
            val firebaseFirestore = FirebaseFirestore.getInstance()
            firebaseFirestore.collection("agenda")
                    .orderBy("id", Query.Direction.ASCENDING)
                    .get()
                    .addOnSuccessListener {
                        if (!it.isEmpty) {
                            val agendaModelList = it.toObjects(AgendaModel::class.java)
                            sessionsModelMutableLiveData.value = AgendaState(agendaModelList)
                        }

                    }
                    .addOnFailureListener {
                        sessionsModelMutableLiveData.value = AgendaState(null,it.message) }

            return sessionsModelMutableLiveData
        }
}
