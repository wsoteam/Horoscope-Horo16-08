package com.wsoteam.horoscopes.presentation.main.pager

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.wsoteam.horoscopes.view.WrapVP

class TabsAdapter(fragmentManager: FragmentManager,
                  val listFragments : List<Fragment>) : FragmentPagerAdapter(fragmentManager) {

    private var currentPosition = -1

    override fun getItem(position: Int): Fragment {
        return listFragments[position]
    }

    override fun getCount(): Int {
        return listFragments.size
    }

    override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
        super.setPrimaryItem(container, position, `object`)
        if (position != currentPosition) {
            val fragment = `object` as Fragment
            val pager = container as WrapVP
            if (fragment.view != null) {
                currentPosition = position
                pager.measureCurrentView(fragment.view)
            }
        }
    }
}