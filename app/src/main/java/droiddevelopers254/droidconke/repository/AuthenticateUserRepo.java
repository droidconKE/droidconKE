package droiddevelopers254.droidconke.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import droiddevelopers254.droidconke.datastates.AuthenticateUserState;
import droiddevelopers254.droidconke.models.UserModel;

public class AuthenticateUserRepo {

    public LiveData<AuthenticateUserState> checkUserExistence(FirebaseUser firebaseUser){
        final MutableLiveData<AuthenticateUserState> userStateMutableLiveData=new MutableLiveData<>();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("users").document(firebaseUser.getUid())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        DocumentSnapshot documentSnapshot= task.getResult();
                        if (documentSnapshot.exists()){
                            //user already exists do nothing
                            userStateMutableLiveData.setValue(new AuthenticateUserState(true));
                        }else {
                            UserModel user = new UserModel();
                            userStateMutableLiveData.setValue(new AuthenticateUserState(user));

                            //save user in firestore

                            firebaseFirestore.collection("users").document(user.getUser_id())
                                    .set(user)
                                    .addOnSuccessListener(aVoid -> userStateMutableLiveData.setValue(new AuthenticateUserState(true)))
                                    .addOnFailureListener(e -> userStateMutableLiveData.setValue(new AuthenticateUserState(e.getMessage())));


                        }
                    }

                });
    return userStateMutableLiveData;
    }

}
