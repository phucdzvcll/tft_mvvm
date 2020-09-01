@file:Suppress("DEPRECATION")

package com.tft_mvvm.app.features.details

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
import com.tft_mvvm.app.features.main.MainActivity
import com.tft_mvvm.app.features.matcher.*
import com.tft_mvvm.champ.R
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith

@Suppress("DEPRECATION")
@LargeTest
@RunWith(AndroidJUnit4::class)
class DetailsActivityTest {

    private val screenshotTaker =
        ScreenshotTaker("Details_activity")

    @Rule
    @JvmField
    var watcher: TestRule =
        ScreenshotTestWatcher()

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun detailsActivityTest() {
        Thread.sleep(2000)
        val recyclerView = onView(withId(R.id.rvByGold))

        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))
        Thread.sleep(1000)
        screenshotTaker.takeScreenshot("001_load_champ_detail.png")

        onView(withText("Caitlyn")).check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.champ_cost),
                withText("1")
            )
        ).check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.skill_name), withText("Bách Phát Bách Trúng")
            )
        ).check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.activated),
                withText("Caitlyn nhắm vào kẻ thù xa nhất, gây sát thương phép lên kẻ thù này. Sát thương: 700/1000/1800.")
            )
        ).check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.tv_header_details),
                withText("Tộc và Hệ")
            )
        ).check(matches(isDisplayed()))
        Thread.sleep(500)
        val recyclerDetailsChamp = onView(withId(R.id.rv_show_details_champ))
        recyclerDetailsChamp.perform(RecyclerViewActions.scrollToPosition<ViewHolder>(3))
        Thread.sleep(100)
        screenshotTaker.takeScreenshot("002_class_and_origin_content.png")
        Thread.sleep(1000)
        onView(
            allOf(
                withId(R.id.origin_or_class_name),
                withText("Thời Không")
            )
        ).check(matches(isDisplayed()))
        onView(
            allOf(
                withId(R.id.origin_or_class_content),
                withText("Tất cả đồng minh nhận thêm tốc độ đánh mỗi 4 giây")
            )
        ).check(matches(isDisplayed()))

        val recyclerviewByOrigin = onView(
            allOf(
                isDescendantOfA(R.id.rv_show_details_champ.withRecyclerView()?.atPosition(2)),
                withId(R.id.rv_origin_or_class)
            )
        )
        recyclerviewByOrigin.check(
            RecyclerViewItemCountAssertion(
                8
            )
        )
        recyclerviewByOrigin.perform(actionOnItemAtPosition<ViewHolder>(2, click()))
        Thread.sleep(2000)
        screenshotTaker.takeScreenshot("003_show_dialog.png")

        onView(
            allOf(
                withId(R.id.name_champ_dialog),
                withText("Blitzcrank")
            )
        ).check(matches(isDisplayed()))
        onView(
            allOf(
                withId(R.id.champ_cost_dialog),
                withText("2"),
                isDisplayed()
            )
        )
        val imageItemBlitzcrankViewItem1 = onView(withId(R.id.suitable_item_dialog_img_1))
        imageItemBlitzcrankViewItem1.perform(click())
        Thread.sleep(200)
        onView(withText("Chén Sức Mạnh"))
            .inRoot(ToastMatcher())
            .check(matches(isDisplayed()))
        Thread.sleep(200)
        screenshotTaker.takeScreenshot("004_toast_name_item_1.png")
        Thread.sleep(1200)

        val imageItemBlitzcrankViewItem2 = onView(withId(R.id.suitable_item_dialog_img_2))
        imageItemBlitzcrankViewItem2.perform(click())
        Thread.sleep(200)
        onView(withText("Nỏ Sét"))
            .inRoot(ToastMatcher())
            .check(matches(isDisplayed()))
        Thread.sleep(200)
        screenshotTaker.takeScreenshot("005_toast_name_item_2.png")
        Thread.sleep(1200)

        val imageItemBlitzcrankViewItem3 = onView(withId(R.id.suitable_item_dialog_img_3))
        imageItemBlitzcrankViewItem3.perform(click())
        Thread.sleep(200)
        onView(withText("Giáp Máu"))
            .inRoot(ToastMatcher())
            .check(matches(isDisplayed()))
        Thread.sleep(200)
        screenshotTaker.takeScreenshot("006_toast_name_item_3.png")
        Thread.sleep(1200)
        Espresso.pressBack()
        Thread.sleep(200)
        onView(
            allOf(
                isDescendantOfA(R.id.rv_show_details_champ.withRecyclerView()?.atPosition(2)),
                withId(R.id.item_bonus_1)
            )
        ).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(
            allOf(
                isDescendantOfA(R.id.rv_show_details_champ.withRecyclerView()?.atPosition(2)),
                withId(R.id.item_bonus_count_1),
                withText("2")
            )
        ).check(matches(isDisplayed()))
        onView(
            allOf(
                withId(R.id.bonus_content_1),
                withText("15% Tốc độ đánh cộng thêm")
            )
        ).check(matches(isDisplayed()))

        onView(
            allOf(
                isDescendantOfA(R.id.rv_show_details_champ.withRecyclerView()?.atPosition(2)),
                withId(R.id.item_bonus_2)
            )
        ).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(
            allOf(
                withId(R.id.item_bonus_count_2),
                withText("4")
            )
        ).check(matches(isDisplayed()))
        onView(
            allOf(
                withId(R.id.bonus_content_2),
                withText("35% Tốc độ đánh cộng thêm")
            )
        ).check(matches(isDisplayed()))
        onView(
            allOf(
                isDescendantOfA(R.id.rv_show_details_champ.withRecyclerView()?.atPosition(2)),
                withId(R.id.item_bonus_3)
            )
        ).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(
            allOf(
                withId(R.id.item_bonus_count_3),
                withText("6")
            )
        ).check(matches(isDisplayed()))
        onView(
            allOf(
                withId(R.id.bonus_content_3),
                withText("65% Tốc độ đánh cộng thêm")
            )
        ).check(matches(isDisplayed()))
        onView(
            allOf(
                withId(R.id.origin_or_class_name),
                withText("Xạ Thủ")
            )
        ).check(matches(isDisplayed()))
        onView(
            allOf(
                withId(R.id.origin_or_class_content),
                withText("Các tướng Xạ Thủ gây thêm sát thương dựa trên số ô khoảng cách giữa họ và mục tiêu")
            )
        ).check(matches(isDisplayed()))

        val recyclerviewByClass = onView(
            allOf(
                isDescendantOfA(R.id.rv_show_details_champ.withRecyclerView()?.atPosition(3)),
                withId(R.id.rv_origin_or_class)
            )
        )
        recyclerviewByClass.check(
            RecyclerViewItemCountAssertion(
                5
            )
        )
        recyclerviewByClass.perform(actionOnItemAtPosition<ViewHolder>(1, click()))
        Thread.sleep(2000)
        screenshotTaker.takeScreenshot("007_show_dialog.png")

        val imageItemAsheViewItem1 = onView(withId(R.id.suitable_item_dialog_img_1))
        imageItemAsheViewItem1.perform(click())
        Thread.sleep(200)
        onView(withText("Cuồng Đao Guinsoo's"))
            .inRoot(ToastMatcher())
            .check(matches(isDisplayed()))
        Thread.sleep(200)
        screenshotTaker.takeScreenshot("008_toast_name_item_1.png")
        Thread.sleep(1200)

        val imageItemAsheViewItem2 = onView(withId(R.id.suitable_item_dialog_img_2))
        imageItemAsheViewItem2.perform(click())
        Thread.sleep(200)
        onView(withText("Thương Shojin"))
            .inRoot(ToastMatcher())
            .check(matches(isDisplayed()))
        Thread.sleep(200)
        screenshotTaker.takeScreenshot("009_toast_name_item_2.png")
        Thread.sleep(1200)

        val imageItemAsheViewItem3 = onView(withId(R.id.suitable_item_dialog_img_3))
        imageItemAsheViewItem3.perform(click())
        Thread.sleep(200)
        onView(withText("Thương Shojin"))
            .inRoot(ToastMatcher())
            .check(matches(isDisplayed()))
        Thread.sleep(200)
        screenshotTaker.takeScreenshot("010_toast_name_item_3.png")
        Thread.sleep(1200)
        Espresso.pressBack()
        Thread.sleep(500)

        onView(
            allOf(
                isDescendantOfA(R.id.rv_show_details_champ.withRecyclerView()?.atPosition(3)),
                withId(R.id.item_bonus_1)
            )
        ).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(
            allOf(
                isDescendantOfA(R.id.rv_show_details_champ.withRecyclerView()?.atPosition(3)),
                withId(R.id.item_bonus_count_1),
                withText("2")
            )
        ).check(matches(isDisplayed()))
        onView(
            allOf(
                withId(R.id.bonus_content_1),
                withText("12%")
            )
        ).check(matches(isDisplayed()))
        recyclerDetailsChamp.perform(RecyclerViewActions.scrollToPosition<ViewHolder>(5))
        Thread.sleep(500)

        onView(
            allOf(
                withText("Đội Hình Thích Hợp")
            )
        ).check(matches(isDisplayed()))

        val recyclerViewTeamRecommend = onView(
            allOf(
                isDescendantOfA(R.id.rv_show_details_champ.withRecyclerView()?.atPosition(5)),
                withId(R.id.rv_item_by_team_recommend)
            )
        ).check(matches(isDisplayed()))
        recyclerViewTeamRecommend.perform(actionOnItemAtPosition<ViewHolder>(4, click()))
        Thread.sleep(1000)
        screenshotTaker.takeScreenshot("011_show_dialog.png")
        val imageItemJhinViewItem1 = onView(withId(R.id.suitable_item_dialog_img_1))
        imageItemJhinViewItem1.perform(click())
        Thread.sleep(200)
        onView(withText("Giáp Thiên Thần"))
            .inRoot(ToastMatcher())
            .check(matches(isDisplayed()))
        Thread.sleep(200)
        screenshotTaker.takeScreenshot("012_toast_name_item_1.png")
        Thread.sleep(1200)

        val imageItemJhinViewItem2 = onView(withId(R.id.suitable_item_dialog_img_2))
        imageItemJhinViewItem2.perform(click())
        Thread.sleep(200)
        onView(withText("Vô Cực Kiếm"))
            .inRoot(ToastMatcher())
            .check(matches(isDisplayed()))
        Thread.sleep(200)
        screenshotTaker.takeScreenshot("013_toast_name_item_2.png")
        Thread.sleep(1200)

        val imageItemJhinViewItem3 = onView(withId(R.id.suitable_item_dialog_img_3))
        imageItemJhinViewItem3.perform(click())
        Thread.sleep(200)
        onView(withText("Cung Xanh"))
            .inRoot(ToastMatcher())
            .check(matches(isDisplayed()))
        Thread.sleep(200)
        screenshotTaker.takeScreenshot("014_toast_name_item_3.png")
        Thread.sleep(1200)
        Espresso.pressBack()
        Thread.sleep(500)

        val appCompatImageView = onView(
            allOf(
                withId(R.id.item_btn_back)
            )
        )

        appCompatImageView.perform(click())
        screenshotTaker.takeScreenshot("015_click_back.png")
        Thread.sleep(1000)
    }

    private fun Int.withRecyclerView(): RecyclerViewMatcher? {
        return RecyclerViewMatcher(this)
    }
}
