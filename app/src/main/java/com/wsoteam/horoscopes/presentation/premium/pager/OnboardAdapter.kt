package com.wsoteam.horoscopes.presentation.premium.pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class OnboardAdapter(
    fragmentManager: FragmentManager,
    val onboardImagesIds: List<Int>,
    val stringArray: Array<String>
) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return PageFragment.newInstance(onboardImagesIds[position], stringArray[position])
    }

    override fun getCount(): Int {
        return 3
    }
}