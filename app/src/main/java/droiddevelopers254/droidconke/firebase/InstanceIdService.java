package droiddevelopers254.droidconke.firebase;

import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import droiddevelopers254.droidconke.models.UserModel;

import static droiddevelopers254.droidconke.utils.SharedPref.FIREBASE_TOKEN;
import static droiddevelopers254.droidconke.utils.SharedPref.PREF_NAME;

public class InstanceIdService extends FirebaseInstanceIdService {
    FirebaseUser firebaseUser;
    FirebaseAuth auth;
    SharedPreferences sharedPreferences;
    String refreshToken;


    public InstanceIdService() {
        super();
    }

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        refreshToken=  FirebaseInstanceId.getInstance().getToken();

        sendToDb(refreshToken);
    }

    private void sendToDb(String refreshToken) {
       sharedPreferences=getSharedPreferences(PREF_NAME,MODE_PRIVATE);
       sharedPreferences.edit().putString(FIREBASE_TOKEN,refreshToken).apply();

       }
}
