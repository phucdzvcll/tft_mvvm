package com.tft_mvvm.app.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.tft_mvvm.app.ui.adapter.ViewPagerAdapter
import com.tft_mvvm.app.ui.fragment.ShowChampByGoldFragment
import com.tft_mvvm.app.ui.fragment.ShowChampByRankFragment
import com.tft_mvvm.app.ui.fragment.ShowRecommendTeamFragment
import com.tft_mvvm.champ.R
import com.tft_mvvm.champ.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var biding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        biding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        addTab(viewPager)
        biding?.tabLayout?.setupWithViewPager(viewPager)
    }

    private fun addTab(viewPager: ViewPager) {
        val showChampByGoldFragment = ShowChampByGoldFragment()
        val showChampByNameFragment = ShowChampByRankFragment()
        val showRecommendTeamFragment = ShowRecommendTeamFragment()
        val adapter = ViewPagerAdapter(supportFragmentManager, arrayListOf(), arrayListOf())
        adapter.add(fragment = showChampByGoldFragment, title = "Tướng")
        adapter.add(fragment = showChampByNameFragment, title = "Xếp Loại")
        adapter.add(fragment = showRecommendTeamFragment, title = "Đội Hình")
        viewPager.adapter = adapter
    }
}