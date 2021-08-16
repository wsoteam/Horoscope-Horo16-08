package com.wsoteam.horoscopes.presentation.crystals

import android.animation.Animator
import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.CountDownTimer
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.presentation.crystals.controller.TopProgressAdapter
import com.wsoteam.horoscopes.presentation.crystals.pager.StoriesPageFragment
import com.wsoteam.horoscopes.presentation.crystals.pager.StoriesPagerAdapter
import kotlinx.android.synthetic.main.stories_onboard_activity.*


class StoriesOnboardActivity : AppCompatActivity(R.layout.stories_onboard_activity) {

    var topProgressAdapter: TopProgressAdapter? = null
    var imgsIds = listOf(
        R.drawable.img_tutor_1,
        R.drawable.img_tutor_2,
        R.drawable.img_tutor_3,
        R.drawable.img_tutor_4,
        R.drawable.img_tutor_5
    )

    var textsList : Array<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        textsList = resources.getStringArray(R.array.stories_onboard_texts)

        rvTopProgress.layoutManager = GridLayoutManager(this, 5)
        topProgressAdapter = TopProgressAdapter(object : IStories{
            override fun onFinish() {
                close()
            }

            override fun onNext() {
                vpStories.setCurrentItem(vpStories.currentItem + 1, true)
            }

            override fun onPrevious() {
                vpStories.setCurrentItem(vpStories.currentItem - 1, true)
            }
        })
        
        rvTopProgress.adapter = topProgressAdapter
        topProgressAdapter!!.start()

        vpStories.adapter = StoriesPagerAdapter(getFragmentsList(), supportFragmentManager)
        vpStories.setOnTouchListener { v, event ->
            return@setOnTouchListener true
        }

        flNext.setOnClickListener {
            topProgressAdapter!!.next()
        }

        flPrevious.setOnClickListener {
            topProgressAdapter!!.previous()
        }
    }

    private fun getFragmentsList(): List<Fragment> {
        var list = arrayListOf<Fragment>()
        for (i in 0..4){
            list.add(StoriesPageFragment.newInstance(textsList!![i], imgsIds[i]))
        }
        return list
    }

    private fun close(){

    }
}