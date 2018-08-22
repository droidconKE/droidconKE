package droiddevelopers254.droidconke.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore

import droiddevelopers254.droidconke.datastates.UpdateTokenState

class UpdateTokenRepo {

    fun updateToken(userId: String, refreshToken: String): LiveData<UpdateTokenState> {
        val updateTokenStateMutableLiveData = MutableLiveData<UpdateTokenState>()
        val firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseFirestore.collection("users").document(userId)
                .update("refresh_token", refreshToken)
                .addOnSuccessListener {
                    updateTokenStateMutableLiveData.value =UpdateTokenState(true,null) }
                .addOnFailureListener {
                    updateTokenStateMutableLiveData.value =UpdateTokenState(false,it.message) }
        return updateTokenStateMutableLiveData
    }
}
