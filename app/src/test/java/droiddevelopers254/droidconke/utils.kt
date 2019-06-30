package droiddevelopers254.droidconke

import android.content.Context
import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.nhaarman.mockitokotlin2.mock
import droiddevelopers254.droidconke.di.appModule
import droiddevelopers254.droidconke.di.dataModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

val testContext = module {
    single { mock<Context>() }
}

val testFirebase = module {
    single(override = true) { mock<FirebaseFirestore>() }
    single(override = true) { mock<FirebaseAuth>() }
    single(override = true) { mock<FirebaseRemoteConfig>() }
    single(override = true) { mock<FirebaseDatabase>() }
    single(override = true) { mock<FirebaseMessaging>() }
}

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class CoroutinesRule : TestRule {
    override fun apply(base: Statement?, description: Description?): Statement {
        return object : Statement() {
            private val mainThreadSurrogate = newSingleThreadContext("UI thread")

            override fun evaluate() {
                Dispatchers.setMain(mainThreadSurrogate)
                startKoin { modules(listOf(testContext, appModule, dataModule, testFirebase)) }
                base?.evaluate()
                Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
                mainThreadSurrogate.close()
                stopKoin()
            }
        }
    }
}


class OneTimeObserver<T>(private val handler: (T) -> Unit) : Observer<T>, LifecycleOwner {
    private val lifecycle = LifecycleRegistry(this)

    init {
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    override fun getLifecycle(): Lifecycle = lifecycle

    override fun onChanged(t: T) {
        handler(t)
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }
}

fun <T> LiveData<T>.observeOnce(onChangeHandler: (T) -> Unit) {
    val observer = OneTimeObserver(handler = onChangeHandler)
    observe(observer, observer)
}