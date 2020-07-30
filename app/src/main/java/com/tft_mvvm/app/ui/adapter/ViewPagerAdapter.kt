package com.tft_mvvm.app.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import java.util.*

class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    private val titleFragment: ArrayList<String>,
    private val fragmentList: ArrayList<Fragment>
) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(p0: Int): Fragment = fragmentList[p0]

    override fun getCount(): Int = fragmentList.size

    override fun getPageTitle(p0:Int):CharSequence = titleFragment[p0]

    fun add(fragment: Fragment, title:String){
        fragmentList.add(fragment)
        titleFragment.add(title)
    }
}