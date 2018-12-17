package droiddevelopers254.droidconke.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import droiddevelopers254.droidconke.repository.AboutDetailsRepo
import droiddevelopers254.droidconke.viewmodels.AboutDetailsViewModel
import org.koin.androidx.viewmodel.experimental.builder.viewModel
import org.koin.dsl.module.module
import org.koin.experimental.builder.single

val appModule = module {
    // Firebase dependencies
    single {
        // In order to avoid this issue https://www.youtube.com/watch?v=Nqscp8SCqLk
        val settings = FirebaseFirestoreSettings.Builder().setTimestampsInSnapshotsEnabled(true)
                .build()
        val firestore = FirebaseFirestore.getInstance()
        firestore.firestoreSettings = settings
        firestore
    }

    // ViewModels
    viewModel<AboutDetailsViewModel>()
}

val dataModule = module {
    single<AboutDetailsRepo>()
}