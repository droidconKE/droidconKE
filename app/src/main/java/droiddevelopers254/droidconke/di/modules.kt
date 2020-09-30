package droiddevelopers254.droidconke.di

import androidx.room.Room
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import droiddevelopers254.droidconke.database.AppDatabase
import droiddevelopers254.droidconke.repository.*
import droiddevelopers254.droidconke.viewmodels.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
  // Firebase dependencies
  single { Firebase.firestore }

  // ViewModels
  viewModel { AboutDetailsViewModel(get()) }
  viewModel { DayOneViewModel(get()) }
  viewModel { DayTwoViewModel(get()) }
  viewModel { SessionDataViewModel(get(), get(), get(), get()) }
  viewModel { AgendaViewModel(get()) }
  viewModel { EventTypeViewModel(get()) }
  viewModel { FeedBackViewModel(get()) }
  viewModel { HomeViewModel(get()) }
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

  // Database
  single { Room.databaseBuilder(get(), AppDatabase::class.java, "droidconKE_db").build() }
}