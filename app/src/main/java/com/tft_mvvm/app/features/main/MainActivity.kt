package com.tft_mvvm.app.features.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.facebook.stetho.Stetho
import com.tft_mvvm.app.features.main.adapter.ViewPagerAdapter
import com.tft_mvvm.champ.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Stetho.initializeWithDefaults(this)
        val listTab = listOf<Tab>(
            Tab(type = Tab.Type.Champion, title = "Tướng"),
            Tab(type = Tab.Type.Ranking, title = "Xếp Loại"),
            Tab(type = Tab.Type.TeamBuilding, title = "Đội Hình")
        )
        val adapter = ViewPagerAdapter(
            supportFragmentManager
        )
        adapter.setItems(listTab = listTab)
        viewPager.adapter = adapter
        tabLayout?.setupWithViewPager(viewPager)
    }


    data class Tab(
        val type: Type,
        val title: String
    ) {
        enum class Type {
            Champion,
            Ranking,
            TeamBuilding
        }
    }
}