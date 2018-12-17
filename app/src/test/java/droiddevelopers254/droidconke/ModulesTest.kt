package droiddevelopers254.droidconke

import droiddevelopers254.droidconke.di.appModule
import droiddevelopers254.droidconke.di.dataModule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.checkModules

class ModulesTest : KoinTest {

    @Test
    fun `check module`() {
        checkModules(listOf(appModule, dataModule))
    }
}