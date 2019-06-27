package droiddevelopers254.droidconke.utils

import android.app.Application
import androidx.core.provider.FontRequest
import androidx.emoji.text.EmojiCompat
import androidx.emoji.text.FontRequestEmojiCompatConfig
import com.google.firebase.database.FirebaseDatabase
import droiddevelopers254.droidconke.BuildConfig
import droiddevelopers254.droidconke.R
import droiddevelopers254.droidconke.di.appModule
import droiddevelopers254.droidconke.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.EmptyLogger

class DroidCon : Application() {
    override fun onCreate() {
        super.onCreate()

        setupEmojiCompat()

        startKoin {
            if (BuildConfig.DEBUG) androidLogger() else EmptyLogger()
            androidContext(this@DroidCon)
            modules(listOf(appModule, dataModule))
        }
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }

    private fun setupEmojiCompat() {
        val fontRequest = FontRequest(
                "com.google.android.gms.fonts",
                "com.google.android.gms",
                "Noto Color Emoji Compat",
                R.array.com_google_android_gms_fonts_certs
        )
        val config = FontRequestEmojiCompatConfig(applicationContext, fontRequest)
                .setReplaceAll(true)
                .registerInitCallback(object : EmojiCompat.InitCallback() {
                    override fun onInitialized() {
                    }

                    override fun onFailed(throwable: Throwable?) {
                    }
                })
        EmojiCompat.init(config)

    }
}
