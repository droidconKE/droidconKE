package droiddevelopers254.droidconke.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import droiddevelopers254.droidconke.datastates.AuthenticateUserState
import droiddevelopers254.droidconke.models.UserModel

class AuthenticateUserRepo {

    fun checkUserExistence(firebaseUser: FirebaseUser): LiveData<AuthenticateUserState> {
        val userStateMutableLiveData = MutableLiveData<AuthenticateUserState>()
        val firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseFirestore.collection("users").document(firebaseUser.uid)
                .get()
                .addOnCompleteListener { task ->
                    when {
                        task.isSuccessful -> {
                            val documentSnapshot = task.result
                            when {
                                documentSnapshot != null -> if (documentSnapshot.exists()) {
                                    //user already exists do nothing
                                    userStateMutableLiveData.value = AuthenticateUserState(true)
                                } else {
                                    val user = UserModel(
                                            firebaseUser.uid,
                                            null,
                                            firebaseUser.email.toString(),
                                            firebaseUser.displayName.toString(),
                                            firebaseUser.photoUrl.toString()
                                    )
                                    userStateMutableLiveData.value = AuthenticateUserState(false,null,user)

                                    //save user in firestore

                                    firebaseFirestore.collection("users").document(user.user_id)
                                            .set(user)
                                            .addOnSuccessListener { userStateMutableLiveData.setValue(AuthenticateUserState(true)) }
                                            .addOnFailureListener {
                                                userStateMutableLiveData.setValue(AuthenticateUserState(false,it.message)) }

                                }
                            }
                        }
                    }

                }
        return userStateMutableLiveData
    }

}
