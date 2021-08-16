package com.wsoteam.horoscopes.presentation.crystals.shop.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wsoteam.horoscopes.R
import kotlinx.android.synthetic.main.item_list_shop.view.*

class ListShopVH(layoutInflater: LayoutInflater, viewGroup: ViewGroup) : RecyclerView.ViewHolder(
    layoutInflater.inflate(R.layout.item_list_shop, viewGroup, false)
) {


    fun bind(imgId: Int, name: String, prop: String) {
        itemView.tvName.text = name
        itemView.tvProp.text = prop
        itemView.ivMain.setImageResource(imgId)
    }


}