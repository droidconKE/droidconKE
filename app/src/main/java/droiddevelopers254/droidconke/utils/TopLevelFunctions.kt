package droiddevelopers254.droidconke.utils

import com.google.firebase.firestore.FirebaseFirestoreException
import droiddevelopers254.droidconke.datastates.Result

suspend fun<T : Any> safeFirebaseCall(call : suspend () -> Result<T>,errorMessage :String) :Result<T> = try {
    call.invoke()
} catch (e: FirebaseFirestoreException) {
    Result.Error(e.message)
}
val <T> T.exhaustive: T get() = this