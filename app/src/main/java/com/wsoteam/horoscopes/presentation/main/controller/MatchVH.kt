package com.wsoteam.horoscopes.presentation.main.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wsoteam.horoscopes.R
import kotlinx.android.synthetic.main.match_vh.view.*

class MatchVH(layoutInflater: LayoutInflater, viewGroup: ViewGroup) : RecyclerView.ViewHolder(
    layoutInflater.inflate(
        R.layout.match_vh, viewGroup, false
    )
) {
    fun bind(loveId: Int, friendId: Int, careerId: Int) {
        itemView.ivLove.setImageResource(
            itemView.resources.obtainTypedArray(R.array.imgs_signs_matches)
                .getResourceId(loveId - 1, -1)
        )
        itemView.ivCareer.setImageResource(
            itemView.resources.obtainTypedArray(R.array.imgs_signs_matches)
                .getResourceId(careerId - 1, -1)
        )
        itemView.ivFriend.setImageResource(
            itemView.resources.obtainTypedArray(R.array.imgs_signs_matches)
                .getResourceId(friendId - 1, -1)
        )

        itemView.tvCareer.text =
            itemView.resources.getStringArray(R.array.names_signs)[careerId - 1]
        itemView.tvFriend.text =
            itemView.resources.getStringArray(R.array.names_signs)[friendId - 1]
        itemView.tvLove.text = itemView.resources.getStringArray(R.array.names_signs)[loveId - 1]
    }
}