package com.tft_mvvm.app.features.main


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.tft_mvvm.champ.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockserver.client.MockServerClient
import org.mockserver.junit.MockServerRule

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private val screenshotTaker = ScreenshotTaker()

    @Rule
    @JvmField
    var mockServerRule: MockServerRule = MockServerRule(this, 8888)

    @JvmField
    var mockServerClient: MockServerClient? = null

    @Rule
    @JvmField
    var watcher: TestRule = ScreenshotTestWatcher()

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {
        val textView = onView(withText("TƯỚNG")).check(matches(isDisplayed()))

        val textView2 = onView(withText("XẾP LOẠI")).check(matches(isDisplayed()))

        val textView3 = onView(withText("ĐỘI HÌNH")).check(matches(isDisplayed()))

        Thread.sleep(2000)
        screenshotTaker.takeScreenshot("001_load_champs.png")

        val recyclerView = onView(withId(R.id.rvByGold))
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        Thread.sleep(1000)
        screenshotTaker.takeScreenshot("002_load_champ_detail.png")

        onView(withText("Caitlyn")).check(matches(isDisplayed()))

        val textView6 = onView(
            allOf(
                withId(R.id.champ_cost),
                withText("1")
            )
        )
        textView6.check(matches(isDisplayed()))

        val textView7 = onView(
            allOf(
                withId(R.id.skill_name), withText("Bách Phát Bách Trúng")
            )
        )
        textView7.check(matches(isDisplayed()))

        val textView8 = onView(
            allOf(
                withId(R.id.activated),
                withText("Caitlyn nhắm vào kẻ thù xa nhất, gây sát thương phép lên kẻ thù này. Sát thương: 700/1000/1800.")
            )
        )
        textView8.check(matches(isDisplayed()))

        val appCompatImageView = onView(
            allOf(
                withId(R.id.item_btn_back)
            )
        )
        appCompatImageView.perform(click())
        screenshotTaker.takeScreenshot("003_click_back.png")

        Thread.sleep(1000)
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
