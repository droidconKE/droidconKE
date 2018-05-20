package droiddevelopers254.droidconke.firebase;

import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import droiddevelopers254.droidconke.models.UserModel;

public class InstanceIdService extends FirebaseInstanceIdService {
    FirebaseUser firebaseUser;
    FirebaseAuth auth;

    public InstanceIdService() {
        super();
    }

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String refreshToken=  FirebaseInstanceId.getInstance().getToken();

        sendToDb(refreshToken);
    }

    private void sendToDb(String refreshToken) {
        //check whether the user is signed in first
        auth = FirebaseAuth.getInstance();
        UserModel user = new UserModel();
        user.setRefresh_token(refreshToken);
        if (auth.getCurrentUser() != null) {
            firebaseUser= auth.getCurrentUser();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference users = database.getReference("users");
            users.child(firebaseUser.getUid()).setValue(user);

        }


    }
}
