package droiddevelopers254.droidconke.di

import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import droiddevelopers254.droidconke.database.AppDatabase
import droiddevelopers254.droidconke.repository.*
import droiddevelopers254.droidconke.viewmodels.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Firebase dependencies
    single { Firebase.firestore }
    single { FirebaseAuth.getInstance() }
    single { FirebaseRemoteConfig.getInstance() }
    single { FirebaseMessaging.getInstance() }
    single {
        val db = FirebaseDatabase.getInstance()
        db.setPersistenceEnabled(true)
        db
    }

    // ViewModels
    viewModel { AboutDetailsViewModel(get()) }
    viewModel { DayOneViewModel(get()) }
    viewModel { DayTwoViewModel(get()) }
    viewModel { SessionDataViewModel(get(), get(), get(), get()) }
    viewModel { AgendaViewModel(get()) }
    viewModel { EventTypeViewModel(get()) }
    viewModel { FeedBackViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { AnnouncementViewModel(get()) }
}

val dataModule = module {
    // Repos
    single { AboutDetailsRepo(get()) }
    single { DayOneRepo(get(), get()) }
    single { DayTwoRepo(get(), get()) }
    single { SessionDataRepo(get(), get()) }
    single { SpeakersRepo(get()) }
    single { RoomRepo(get()) }
    single { SessionFeedbackRepo(get()) }
    single { UpdateTokenRepo(get()) }
    single { EventFeedbackRepo(get()) }
    single { EventTypeRepo(get()) }
    single { AgendaRepo(get()) }
    single { AnnouncementRepo(get()) }

    // Database
    single { Room.databaseBuilder(get(), AppDatabase::class.java, "droidconKE_db").fallbackToDestructiveMigration().build() }
}