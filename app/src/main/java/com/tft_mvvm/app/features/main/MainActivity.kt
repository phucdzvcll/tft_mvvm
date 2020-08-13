package com.tft_mvvm.app.features.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.tft_mvvm.app.features.main.adapter.ViewPagerAdapter
import com.tft_mvvm.app.features.main.fragment.ShowChampByGoldFragment
import com.tft_mvvm.app.features.main.fragment.ShowChampByRankFragment
import com.tft_mvvm.app.features.main.fragment.ShowRecommendTeamFragment
import com.tft_mvvm.champ.R

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addTab(viewPager)
        tabLayout?.setupWithViewPager(viewPager)
    }

    private fun addTab(viewPager: ViewPager) {
        val showChampByGoldFragment =
            ShowChampByGoldFragment()
        val showChampByNameFragment =
            ShowChampByRankFragment()
        val showRecommendTeamFragment =
            ShowRecommendTeamFragment()
        val adapter = ViewPagerAdapter(
            supportFragmentManager,
            arrayListOf(),
            arrayListOf()
        )
        adapter.add(fragment = showChampByGoldFragment, title = "Tướng")
        adapter.add(fragment = showChampByNameFragment, title = "Xếp Loại")
        adapter.add(fragment = showRecommendTeamFragment, title = "Đội Hình")
        viewPager.adapter = adapter
    }
}