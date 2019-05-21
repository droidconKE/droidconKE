package droiddevelopers254.droidconke.di

import androidx.room.Room
import com.google.firebase.firestore.FirebaseFirestore
import droiddevelopers254.droidconke.database.AppDatabase
import droiddevelopers254.droidconke.repository.*
import droiddevelopers254.droidconke.viewmodels.AboutDetailsViewModel
import droiddevelopers254.droidconke.viewmodels.DayOneViewModel
import droiddevelopers254.droidconke.viewmodels.DayTwoViewModel
import droiddevelopers254.droidconke.viewmodels.SessionDataViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Firebase dependencies
    single { FirebaseFirestore.getInstance() }

    // ViewModels
    viewModel { AboutDetailsViewModel(get()) }
    viewModel { DayOneViewModel(get()) }
    viewModel { DayTwoViewModel(get()) }
    viewModel { SessionDataViewModel(get(), get(), get(), get()) }
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

    // Database
    single { Room.databaseBuilder(get(), AppDatabase::class.java, "droidconKE_db").build() }
}