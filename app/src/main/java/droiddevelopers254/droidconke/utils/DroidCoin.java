package droiddevelopers254.droidconke.utils;

import android.app.Application;
import android.content.Context;

import com.google.firebase.database.FirebaseDatabase;

public class DroidCoin extends Application {
    public  static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
       context=getApplicationContext();
    }

}
