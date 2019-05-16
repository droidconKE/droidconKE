package droiddevelopers254.droidconke

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
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
}

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class CoroutinesRule : TestRule {
    override fun apply(base: Statement?, description: Description?): Statement {
        return object : Statement() {
            private val mainThreadSurrogate = newSingleThreadContext("UI thread")

            override fun evaluate() {
                Dispatchers.setMain(mainThreadSurrogate)
                startKoin { modules(testContext, appModule, dataModule) }
                base?.evaluate()
                Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
                mainThreadSurrogate.close()
                stopKoin()
            }
        }
    }
}