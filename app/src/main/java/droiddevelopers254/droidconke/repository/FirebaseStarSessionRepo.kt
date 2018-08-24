package droiddevelopers254.droidconke.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import droiddevelopers254.droidconke.datastates.StarSessionState
import droiddevelopers254.droidconke.models.StarredSessionModel

class FirebaseStarSessionRepo {
    internal var firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val starSessionStateMutableLiveData: MutableLiveData<StarSessionState> = MutableLiveData()

    fun starSession(starredSessionModel: StarredSessionModel, userId: String): LiveData<StarSessionState> {
        firebaseFirestore.collection("starred_sessions").document(starredSessionModel.documentId)
                .set(starredSessionModel)
                .addOnSuccessListener {
                    //add session to user starred sessions
                    firebaseFirestore.collection("users").document(userId).collection("starred").document(starredSessionModel.documentId)
                            .set(starredSessionModel)
                            .addOnSuccessListener { _ ->
                                Log.d("star_response", "success")
                                starSessionStateMutableLiveData.setValue(StarSessionState(true,null,null,0))
                            }
                            .addOnFailureListener { e -> starSessionStateMutableLiveData.setValue(StarSessionState(false,e.message,null,null)) }
                }
                .addOnFailureListener { e -> starSessionStateMutableLiveData.setValue(StarSessionState(false,e.message,null,0)) }
        return starSessionStateMutableLiveData
    }

    fun unStarSession(sessionId: String, userId: String, starred: Boolean): LiveData<StarSessionState> {
        firebaseFirestore.collection("starred_sessions").document(sessionId)
                .delete()
                .addOnSuccessListener {
                    //change starred field in users starred sessions to false
                    firebaseFirestore.collection("users").document(userId).collection("starred").document(sessionId)
                            .update("starred", starred)
                            .addOnSuccessListener { _ ->
                                Log.d("unstar_response", "success")
                                starSessionStateMutableLiveData.setValue(StarSessionState(false,null,null,0))
                            }
                            .addOnFailureListener { e -> starSessionStateMutableLiveData.setValue(StarSessionState(false,e.message,null,0)) }
                }
                .addOnFailureListener { e -> starSessionStateMutableLiveData.setValue(StarSessionState(false,e.message,null,null)) }
        return starSessionStateMutableLiveData
    }

    fun checkStarStatus(sessionId: String, userId: String): LiveData<StarSessionState> {
        firebaseFirestore.collection("users").document(userId).collection("starred").document(sessionId)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val documentSnapshot = task.result
                        if (documentSnapshot.exists()) {
                            //session already starred, un star session
                            val starredSessionModel = documentSnapshot.toObject(StarredSessionModel::class.java)
                            starSessionStateMutableLiveData.setValue(StarSessionState(false,null,starredSessionModel,0))
                        } else {
                            starSessionStateMutableLiveData.setValue(StarSessionState(false,null,null,0))
                        }
                    }
                }
        return starSessionStateMutableLiveData
    }
}
