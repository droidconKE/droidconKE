package droiddevelopers254.droidconke.di

import com.google.firebase.firestore.FirebaseFirestore
import droiddevelopers254.droidconke.repository.AboutDetailsRepo
import droiddevelopers254.droidconke.repository.EventTypeRepo
import droiddevelopers254.droidconke.viewmodels.AboutDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Firebase dependencies
    single { FirebaseFirestore.getInstance() }

    // ViewModels
    viewModel { AboutDetailsViewModel(get()) }
}

val dataModule = module {
    single { AboutDetailsRepo(get()) }
    single { EventTypeRepo() }
}