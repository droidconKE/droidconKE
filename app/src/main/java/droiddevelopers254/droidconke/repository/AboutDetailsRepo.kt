package droiddevelopers254.droidconke.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import droiddevelopers254.droidconke.datastates.AboutDetailsState
import droiddevelopers254.droidconke.models.AboutDetailsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AboutDetailsRepo(val firestore: FirebaseFirestore) {

//    fun getAboutDetails(aboutType: String): LiveData<AboutDetailsState> {
//        val detailsStateMutableLiveData = MutableLiveData<AboutDetailsState>()
//        firestore.collection(aboutType)
//                .orderBy("id", Query.Direction.ASCENDING)
//                .get()
//                .addOnSuccessListener {
//                    val aboutDetailsModelList = it.toObjects(AboutDetailsModel::class.java)
//                    detailsStateMutableLiveData.setValue(AboutDetailsState(aboutDetailsModelList))
//                }
//                .addOnFailureListener { e -> detailsStateMutableLiveData.setValue(AboutDetailsState(null,e.message)) }
//        return detailsStateMutableLiveData
//    }

    fun getAboutDetails(aboutType: String): AboutDetailsState {
        var state = AboutDetailsState(null, null)
        GlobalScope.launch(Dispatchers.Main) {
            state = try {
                val snapshot = firestore.collection(aboutType)
                        .orderBy("id", Query.Direction.ASCENDING)
                        .get()
                        .await()
                val aboutDetailsModelList = snapshot.toObjects(AboutDetailsModel::class.java)
                AboutDetailsState(aboutDetailsModelList)
            } catch (e: Exception) {
                AboutDetailsState(null, e.message)
            }
        }

        return state
    }
}
