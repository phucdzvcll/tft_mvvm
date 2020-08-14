package com.tft_mvvm.app.features.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.tft_mvvm.app.features.main.MainActivity
import com.tft_mvvm.app.features.main.fragment.ShowChampByGoldFragment
import com.tft_mvvm.app.features.main.fragment.ShowChampByRankFragment
import com.tft_mvvm.app.features.main.fragment.ShowRecommendTeamFragment

class ViewPagerAdapter(
    fragmentManager: FragmentManager
) : FragmentPagerAdapter(fragmentManager,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {

    private val listTab = mutableListOf<MainActivity.Tab>()

    override fun getItem(position: Int): Fragment{
        val tab = listTab.get(position)
        return createFragment(tab.type)
    }

    override fun getCount(): Int = listTab.size

    override fun getPageTitle(position:Int):CharSequence = listTab[position].title

    private fun createFragment(type : MainActivity.Tab.Type) : Fragment {
        return when (type) {
            MainActivity.Tab.Type.Champion -> ShowChampByGoldFragment()
            MainActivity.Tab.Type.Ranking -> ShowChampByRankFragment()
            MainActivity.Tab.Type.TeamBuilding -> ShowRecommendTeamFragment()
        }
    }

    fun setItems(listTab: List<MainActivity.Tab>) {
        this.listTab.clear()
        this.listTab.addAll(listTab)
    }
}