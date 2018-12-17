package droiddevelopers254.droidconke.activities

import android.content.Intent
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import droiddevelopers254.droidconke.R
import droiddevelopers254.droidconke.views.activities.AboutDetailsActivity
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AboutDetailsActivityTest {

    val activityTestRule = ActivityTestRule<AboutDetailsActivity>(AboutDetailsActivity::class.java, false, false)

    @Test
    fun checkToolbarText() {
        val intent = Intent().apply {
            putExtra("aboutType", "about_droidconKE")
        }
        activityTestRule.launchActivity(intent)

        onView(allOf(instanceOf(TextView::class.java), withParent(withId(R.id.toolbar))))
                .check(matches(withText("About droidconKE")))
    }
}