package com.wsoteam.horoscopes.presentation.crystals.controller

import android.animation.Animator
import android.animation.ObjectAnimator
import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.wsoteam.horoscopes.R
import kotlinx.android.synthetic.main.item_top_progress.view.*

class TopProgressVH(layoutInflater: LayoutInflater, viewGroup: ViewGroup) :
    RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.item_top_progress, viewGroup, false)) {

    var iCounter: ICounter? = null
    var animation: ObjectAnimator? = null

    fun bind(state: Int, iCounter: ICounter) {
        this.iCounter = iCounter
        animation?.removeAllListeners()
        animation?.end()
        animation?.cancel()
        when (state) {
            ProgressConfig.EMPTY -> clearProgresBar()
            ProgressConfig.FIN -> finProgressBar()
            ProgressConfig.NEED_START -> startCountDown()
        }
    }

    private fun startCountDown() {
        animation =
            ObjectAnimator.ofInt(itemView.pbTopProgress, "progress", 0, itemView.pbTopProgress.max)
        animation!!.duration = ProgressConfig.delay
        animation!!.interpolator = DecelerateInterpolator()
        animation!!.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                iCounter!!.endCount()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }
        })
        animation!!.start()
    }

    private fun finProgressBar() {
        itemView.pbTopProgress.progress = itemView.pbTopProgress.max
    }

    private fun clearProgresBar() {
        itemView.pbTopProgress.progress = 0
    }


}