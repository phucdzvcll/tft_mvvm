package com.tft_mvvm.app.features.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import java.text.FieldPosition
import java.util.*

@Suppress("DEPRECATION")
class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    private val titleList: ArrayList<String>,
    private val fragmentList: ArrayList<Fragment>
) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment = fragmentList[position]

    override fun getCount(): Int = fragmentList.size

    override fun getPageTitle(position:Int):CharSequence = titleList[position]

    fun add(fragment: Fragment, title:String){
        fragmentList.add(fragment)
        titleList.add(title)
    }
}