package droiddevelopers254.droidconke.utils;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class DroidConKe extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

    }

}
