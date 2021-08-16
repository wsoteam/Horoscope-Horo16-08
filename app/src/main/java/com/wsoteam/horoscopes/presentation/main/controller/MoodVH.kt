package com.wsoteam.horoscopes.presentation.main.controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wsoteam.horoscopes.R
import kotlinx.android.synthetic.main.mood_vh.view.*

class MoodVH(
    layoutInflater: LayoutInflater,
    viewGroup: ViewGroup,
    val iGetPrem: IGetPrem
) :
    RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.mood_vh, viewGroup, false)) {
    fun bind(
        sex: Int,
        hustle: Int,
        vibe: Int,
        success: Int,
        isLocked: Boolean
    ) {
        itemView.rvSex.setRating(sex)
        itemView.rvHustle.setRating(hustle)
        itemView.rvVibe.setRating(vibe)
        itemView.rvSuccess.setRating(success)

        if (isLocked) {
            itemView.ivBlur.visibility = View.VISIBLE
            Glide.with(itemView.context).load(R.drawable.blur).into(itemView.ivBlur)
            itemView.llLock.visibility = View.VISIBLE
            itemView.btnLockPrem.setOnClickListener { iGetPrem.getPrem() }
        }else{
            itemView.ivBlur.visibility = View.GONE
            itemView.llLock.visibility = View.GONE

        }
    }
}
