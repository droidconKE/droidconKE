package droiddevelopers254.droidconke.activities

import android.content.Intent
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.bartoszlipinski.disableanimationsrule.DisableAnimationsRule
import droiddevelopers254.droidconke.R
import droiddevelopers254.droidconke.ui.about.AboutDetailsAdapter
import droiddevelopers254.droidconke.utils.EspressoIdlingResource
import droiddevelopers254.droidconke.ui.about.AboutDetailsActivity
import org.hamcrest.CoreMatchers.*
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AboutDetailsActivityTest {

    companion object {
        @ClassRule
        @JvmField
        val disableAnimationsRule = DisableAnimationsRule()
    }

    @get:Rule
    val activityTestRule = ActivityTestRule<AboutDetailsActivity>(AboutDetailsActivity::class.java, false, false)

    @Before
    fun setup() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource())
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource())
    }

    @Test
    fun checkToolbarText() {
        val intent = Intent().apply {
            putExtra("aboutType", "about_droidconKE")
        }
        activityTestRule.launchActivity(intent)

        onView(allOf(instanceOf(TextView::class.java), withParent(withId(R.id.toolbar))))
                .check(matches(withText("About droidconKE")))
    }

    @Test
    fun recyclerViewItem_checkTextIsLoaded() {
        val intent = Intent().apply {
            putExtra("aboutType", "about_droidconKE")
        }
        activityTestRule.launchActivity(intent)

        onView(withId(R.id.aboutDetailsRv))
                .perform(RecyclerViewActions.actionOnItemAtPosition<AboutDetailsAdapter.MyViewHolder>(0, click()))

        onView(allOf(withId(R.id.aboutDetailsDescText),
                withText(containsString("is held annually by the Android254 community."))))
                .check(matches(isDisplayed()))
    }

    @Test
    fun recyclerViewItem_checkImageIsLoaded() {
        val intent = Intent().apply {
            putExtra("aboutType", "about_droidconKE")
        }
        activityTestRule.launchActivity(intent)

        onView(allOf(withId(R.id.aboutDetailsImg))).check(matches(isDisplayed()))
    }
}