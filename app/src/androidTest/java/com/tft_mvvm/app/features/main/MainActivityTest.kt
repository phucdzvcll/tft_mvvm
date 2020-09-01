@file:Suppress("DEPRECATION")

package com.tft_mvvm.app.features.main

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.tft_mvvm.app.features.matcher.*
import com.tft_mvvm.champ.R
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith


@Suppress("DEPRECATION")
@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private val screenshotTaker =
        ScreenshotTaker("Main_activity")

    @Rule
    @JvmField
    var watcher: TestRule =
        ScreenshotTestWatcher()

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainTest() {
        onView(withText("TƯỚNG"))
            .check(matches(isDisplayed()))

        onView(withText("XẾP LOẠI"))
            .check(matches(isDisplayed()))

        onView(withText("ĐỘI HÌNH"))
            .check(matches(isDisplayed()))

        val recyclerViewShowChampByGold = onView(withId(R.id.rvByGold))
        recyclerViewShowChampByGold.check(RecyclerViewItemCountAssertion(57))
        screenshotTaker.takeScreenshot("001_fragment_show_champ_by_gold.png")
        Thread.sleep(500)
        onView(withText("XẾP LOẠI")).perform(click())
        Thread.sleep(3000)
        screenshotTaker.takeScreenshot("001_fragment_show_champ_by_rank.png")
        val recyclerViewShowChampByRank = onView(withId(R.id.rv_by_rank))
        recyclerViewShowChampByRank.check(RecyclerViewItemCountAssertion(61))
        onView(
            allOf(
                withId(R.id.tvTitle),
                withText("Bậc S"),
                childAtPosition(
                    withId(R.id.rv_by_rank),
                    0
                )
            )
        ).check(
            matches(isDisplayed())
        )

        onView(
            allOf(
                withId(R.id.tvTitle),
                withText("Bậc A"),
                childAtPosition(
                    withId(R.id.rv_by_rank),
                    4
                )

            )
        ).check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.tvTitle),
                withText("Bậc B"),
                childAtPosition(
                    withId(R.id.rv_by_rank),
                    23
                )

            )
        ).check(matches(isDisplayed()))

        recyclerViewShowChampByRank.perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                60
            )
        )
        Thread.sleep(100)
        onView(
            allOf(
                withId(R.id.tvTitle),
                withText("Bậc C")
            )
        ).check(matches(isDisplayed()))
        Thread.sleep(100)

        recyclerViewShowChampByRank.perform(
            actionOnItemAtPosition<RecyclerView.ViewHolder>(
                10,
                click()
            )
        )
        Thread.sleep(2000)

        onView(
            allOf(
                withId(R.id.name_champ_dialog),
                withText("Fizz")
            )
        ).check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.champ_cost_dialog),
                withText("4")
            )
        ).check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.skill_name_dialog),
                withText("Triệu Hồi Thủy Quái")
            )
        ).check(matches(isDisplayed()))

        val imageItemFizzViewItem1 = onView(withId(R.id.suitable_item_dialog_img_1))
        imageItemFizzViewItem1.perform(click())
        Thread.sleep(200)
        onView(withText("Giáp Gai"))
            .inRoot(ToastMatcher())
            .check(matches(isDisplayed()))
        Thread.sleep(200)
        screenshotTaker.takeScreenshot("004_toast_name_item_1.png")
        Thread.sleep(1200)

        val imageItemFizzViewItem2 = onView(withId(R.id.suitable_item_dialog_img_2))
        imageItemFizzViewItem2.perform(click())
        Thread.sleep(200)
        onView(withText("Nỏ Sét"))
            .inRoot(ToastMatcher())
            .check(matches(isDisplayed()))
        Thread.sleep(200)
        screenshotTaker.takeScreenshot("005_toast_name_item_2.png")
        Thread.sleep(1200)

        val imageItemFizzViewItem3 = onView(withId(R.id.suitable_item_dialog_img_3))
        imageItemFizzViewItem3.perform(click())
        Thread.sleep(200)
        onView(withText("Áo Choàng Tĩnh Lặng"))
            .inRoot(ToastMatcher())
            .check(matches(isDisplayed()))
        Thread.sleep(200)
        screenshotTaker.takeScreenshot("006_toast_name_item_3.png")
        Thread.sleep(1200)
        Espresso.pressBack()
        Thread.sleep(200)

        recyclerViewShowChampByRank.perform(
            actionOnItemAtPosition<RecyclerView.ViewHolder>(
                50,
                click()
            )
        )
        Thread.sleep(2000)

        onView(
            allOf(
                withId(R.id.name_champ_dialog),
                withText("Malphite")
            )
        ).check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.champ_cost_dialog),
                withText("1")
            )
        ).check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.skill_name_dialog),
                withText("Nội tại")
            )
        ).check(matches(isDisplayed()))

        val imageItemMalphiteViewItem1 = onView(withId(R.id.suitable_item_dialog_img_1))
        imageItemMalphiteViewItem1.perform(click())
        Thread.sleep(200)
        onView(withText("Giáp Gai"))
            .inRoot(ToastMatcher())
            .check(matches(isDisplayed()))
        Thread.sleep(200)
        screenshotTaker.takeScreenshot("004_toast_name_item_1.png")
        Thread.sleep(1200)

        val imageItemMalphiteViewItem2 = onView(withId(R.id.suitable_item_dialog_img_2))
        imageItemMalphiteViewItem2.perform(click())
        Thread.sleep(200)
        onView(withText("Giáp Máu"))
            .inRoot(ToastMatcher())
            .check(matches(isDisplayed()))
        Thread.sleep(200)
        screenshotTaker.takeScreenshot("005_toast_name_item_2.png")
        Thread.sleep(1200)

        val imageItemMalphiteViewItem3 = onView(withId(R.id.suitable_item_dialog_img_3))
        imageItemMalphiteViewItem3.perform(click())
        Thread.sleep(200)
        onView(withText("Vuốt Rồng"))
            .inRoot(ToastMatcher())
            .check(matches(isDisplayed()))
        Thread.sleep(200)
        screenshotTaker.takeScreenshot("006_toast_name_item_3.png")
        Thread.sleep(1200)
        Espresso.pressBack()
        Thread.sleep(200)
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

    private fun Int.withRecyclerView(): RecyclerViewMatcher? {
        return RecyclerViewMatcher(this)
    }
}