package droiddevelopers254.droidconke.utils

import android.app.Application
import android.content.Context

import com.google.firebase.database.FirebaseDatabase
import droiddevelopers254.droidconke.BuildConfig
import droiddevelopers254.droidconke.di.appModule
import droiddevelopers254.droidconke.di.dataModule
import org.koin.android.ext.android.startKoin
import org.koin.android.logger.AndroidLogger
import org.koin.log.EmptyLogger

class DroidCon : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule, dataModule),
                logger = if (BuildConfig.DEBUG) AndroidLogger() else EmptyLogger())
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        context = applicationContext
    }

    companion object {
       lateinit var context: Context
    }

}
