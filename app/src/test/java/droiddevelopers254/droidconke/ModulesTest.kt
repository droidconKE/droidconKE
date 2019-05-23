package droiddevelopers254.droidconke

import droiddevelopers254.droidconke.di.appModule
import droiddevelopers254.droidconke.di.dataModule
import org.junit.Test
import org.koin.dsl.koinApplication
import org.koin.test.KoinTest
import org.koin.test.check.checkModules

class ModulesTest : KoinTest {

    @Test
    fun `check dependencies`() {
        koinApplication { modules(testContext, appModule, dataModule, testFirebase) }.checkModules()
    }
}