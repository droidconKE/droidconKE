package droiddevelopers254.droidconke.di

import androidx.room.Room
import com.google.firebase.firestore.FirebaseFirestore
import droiddevelopers254.droidconke.database.AppDatabase
import droiddevelopers254.droidconke.repository.AboutDetailsRepo
import droiddevelopers254.droidconke.repository.DayOneRepo
import droiddevelopers254.droidconke.repository.DayTwoRepo
import droiddevelopers254.droidconke.viewmodels.AboutDetailsViewModel
import droiddevelopers254.droidconke.viewmodels.DayOneViewModel
import droiddevelopers254.droidconke.viewmodels.DayTwoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Firebase dependencies
    single { FirebaseFirestore.getInstance() }

    // ViewModels
    viewModel { AboutDetailsViewModel(get()) }
    viewModel { DayOneViewModel(get()) }
    viewModel { DayTwoViewModel(get()) }
}

val dataModule = module {
    // Repos
    single { AboutDetailsRepo(get()) }
    single { DayOneRepo(get(), get()) }
    single { DayTwoRepo(get(), get()) }

    // Database
    single { Room.databaseBuilder(get(), AppDatabase::class.java, "droidconKE_db").build() }
}