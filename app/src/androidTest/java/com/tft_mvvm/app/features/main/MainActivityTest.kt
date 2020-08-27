package com.tft_mvvm.app.features.main


import androidx.recyclerview.widget.RecyclerView.ViewHolder
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
import com.fasterxml.jackson.databind.deser.DataFormatReaders
import com.tft_mvvm.app.features.dialog_show_details_champ.DialogShowDetailsChamp
import com.tft_mvvm.champ.R
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private val screenshotTaker = ScreenshotTaker()

//    @Rule
//    @JvmField
//    var mockServerRule: MockServerRule = MockServerRule(this, 8888)

//    @JvmField
//    var mockServerClient: MockServerClient? = null

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

        Thread.sleep(1500)
        screenshotTaker.takeScreenshot("001_load_champs.png")

        val recyclerView = onView(withId(R.id.rvByGold))
        recyclerView.perform(RecyclerViewActions.scrollToPosition<ViewHolder>(56))
        val textViewOfLastItem = onView(
            allOf(
                withId(R.id.name),
                withText("Xerath")
            )
        )
        textViewOfLastItem.check(matches(isDisplayed()))
        Thread.sleep(500)
        screenshotTaker.takeScreenshot("002_last_item_showChampByGold.png")
        Thread.sleep(100)
        recyclerView.perform(RecyclerViewActions.scrollToPosition<ViewHolder>(0))
        Thread.sleep(200)
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))
        Thread.sleep(1000)
        screenshotTaker.takeScreenshot("003_load_champ_detail.png")

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

        val textView9 = onView(
            withText("Tộc và Hệ")
        )
        textView9.check(matches(isDisplayed()))

        val textView10 = onView(
            allOf(
                withId(R.id.origin_or_class_name),
                withText("Thời Không")
            )
        )
        textView10.check(matches(isDisplayed()))

        val textView11 = onView(
            allOf(
                withId(R.id.origin_or_class_content),
                withText("Tất cả đồng minh nhận thêm tốc độ đánh mỗi 4 giây")
            )
        )
        textView11.check(matches(isDisplayed()))
        Thread.sleep(1000)
        val recycleViewShowChampByOriginAndClass = onView(withId(R.id.rv_origin_or_class))
        recycleViewShowChampByOriginAndClass.perform(
            RecyclerViewActions.actionOnItemAtPosition<ViewHolder>(
                2,
                click()
            )
        )
        Thread.sleep(2000)
        screenshotTaker.takeScreenshot("004_show_dialog.png")
        val textViewNameDialog = onView(
            allOf(
                withId(R.id.name_champ_dialog),
                withText("Blitzcrank")
            )
        )
        textViewNameDialog.check(matches(isDisplayed()))

        val textViewCostDialog = onView(
            allOf(
                withId(R.id.champ_cost_dialog),
                withText("2")
            )
        )
        textViewCostDialog.check(matches(isDisplayed()))

        val imageViewItem1 = onView(withId(R.id.suitable_item_dialog_img_1))
        imageViewItem1.perform(click())
        Thread.sleep(200)
        screenshotTaker.takeScreenshot("005_toast_name_item_1.png")
        Thread.sleep(1200)

        val imageViewItem2 = onView(withId(R.id.suitable_item_dialog_img_2))
        imageViewItem2.perform(click())
        Thread.sleep(200)
        screenshotTaker.takeScreenshot("006_toast_name_item_2.png")
        Thread.sleep(1200)

        val imageViewItem3 = onView(withId(R.id.suitable_item_dialog_img_3))
        imageViewItem3.perform(click())
        Thread.sleep(200)
        screenshotTaker.takeScreenshot("007_toast_name_item_3.png")
        Thread.sleep(1200)
        Espresso.pressBack()
        Thread.sleep(300)
        val recyclerDetailsChamp = onView(withId(R.id.rv_show_details_champ))
        recyclerDetailsChamp.perform(RecyclerViewActions.scrollToPosition<ViewHolder>(4))
        Thread.sleep(500)
        screenshotTaker.takeScreenshot("008_scroll_to_position_4.png")
        Thread.sleep(1000)
        recyclerDetailsChamp.perform(RecyclerViewActions.scrollToPosition<ViewHolder>(5))
        Thread.sleep(300)
        screenshotTaker.takeScreenshot("009_scroll_to_position_6.png")
        Thread.sleep(500)
        val appCompatImageView = onView(
            allOf(
                withId(R.id.item_btn_back)
            )
        )

        appCompatImageView.perform(click())
        screenshotTaker.takeScreenshot("010_click_back.png")
        Thread.sleep(1000)
    }

//    private fun childAtPosition(
//        parentMatcher: Matcher<View>, position: Int
//    ): Matcher<View> {
//
//        return object : TypeSafeMatcher<View>() {
//            override fun describeTo(description: Description) {
//                description.appendText("Child at position $position in parent ")
//                parentMatcher.describeTo(description)
//            }
//
//            public override fun matchesSafely(view: View): Boolean {
//                val parent = view.parent
//                return parent is ViewGroup && parentMatcher.matches(parent)
//                        && view == parent.getChildAt(position)
//            }
//        }
//    }

}
