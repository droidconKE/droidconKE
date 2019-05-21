package droiddevelopers254.droidconke.utils

import android.app.Application
import com.google.firebase.database.FirebaseDatabase
import droiddevelopers254.droidconke.BuildConfig
import droiddevelopers254.droidconke.di.appModule
import droiddevelopers254.droidconke.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.EmptyLogger

class DroidCon : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            if (BuildConfig.DEBUG) androidLogger() else EmptyLogger()
            androidContext(this@DroidCon)
            modules(appModule, dataModule)
        }
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }
}
