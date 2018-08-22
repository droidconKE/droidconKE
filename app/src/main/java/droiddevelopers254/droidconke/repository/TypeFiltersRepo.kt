package droiddevelopers254.droidconke.repository


import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import droiddevelopers254.droidconke.datastates.FiltersState
import droiddevelopers254.droidconke.models.FiltersModel
import java.util.*

class TypeFiltersRepo {
    internal var filtersModelList: List<FiltersModel> = ArrayList()

    val filters: LiveData<FiltersState>
        get() {
            val filtersStateMutableLiveData = MutableLiveData<FiltersState>()
            val firebaseFirestore = FirebaseFirestore.getInstance()
            firebaseFirestore.collection("session_types")
                    .orderBy("id", Query.Direction.ASCENDING)
                    .get()
                    .addOnSuccessListener {
                        val filtersModelList = it.toObjects(FiltersModel::class.java)
                        filtersStateMutableLiveData.value = FiltersState(filtersModelList,null)

                    }
                    .addOnFailureListener {
                        filtersStateMutableLiveData.value = FiltersState(null,it.message) }
            return filtersStateMutableLiveData
        }

}
