package droiddevelopers254.droidconke.utils

import android.app.Application
import android.content.Context

import com.google.firebase.database.FirebaseDatabase

class DroidCoin : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        context = applicationContext
    }

    companion object {
       lateinit var context: Context
    }

}
