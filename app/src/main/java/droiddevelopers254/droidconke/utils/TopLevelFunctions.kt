package droiddevelopers254.droidconke.utils

// TODO Work in progress
//suspend fun <T : Any> safeFirebaseCall(call: suspend () -> Result<T>, errorMessage: String): Result<T> = try {
//    call.invoke()
//} catch (e: Exception) {
//    Result.Error(IOException(errorMessage, e))
//}
//val <T> T.exhaustive: T get() = this