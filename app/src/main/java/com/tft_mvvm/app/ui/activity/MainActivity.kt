package com.tft_mvvm.app.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.tft_mvvm.app.ui.adapter.ViewPagerAdapter
import com.tft_mvvm.app.ui.fragment.ShowChampByGold
import com.tft_mvvm.app.ui.fragment.ShowChampByName
import com.tft_mvvm.app.ui.fragment.ShowRecommendTeam
import com.tft_mvvm.champ.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addTab(view_pager)
        tab_layout.setupWithViewPager(view_pager)
    }

    private fun addTab(viewPager: ViewPager){
        val showChampByGold = ShowChampByGold()
        val showChampByName = ShowChampByName()
        val showRecommendTeam = ShowRecommendTeam()
        val adapter = ViewPagerAdapter(supportFragmentManager, arrayListOf(), arrayListOf())
        adapter.add(fragment = showChampByGold, title = "Gold")
        adapter.add(fragment = showChampByName, title = "Gold")
        adapter.add(fragment = showRecommendTeam, title = "Gold")
        viewPager.adapter = adapter
    }
}