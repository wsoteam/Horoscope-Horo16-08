package com.wsoteam.horoscopes.presentation.crystals.shop

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.presentation.crystals.shop.controller.ListShopAdapter
import kotlinx.android.synthetic.main.list_activity.*

class ListActivity : AppCompatActivity(R.layout.list_activity) {

    var imgsIds: IntArray? = null
    var names: Array<String>? = null
    var props: Array<String>? = null

    var adapter : ListShopAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        imgsIds = getIndexes(resources.getIntArray(R.array.crystals_ids))
        names = resources.getStringArray(R.array.crystals_names)
        props = resources.getStringArray(R.array.crystals_prop)

        adapter = ListShopAdapter(imgsIds!!, names!!, props!!)
        rvCrystalsShop.layoutManager = GridLayoutManager(this, 2)
        rvCrystalsShop.adapter = adapter


    }

    private fun getIndexes(intArray: IntArray): IntArray? {
        var indexes = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0)
        for (i in intArray.indices){
            indexes[i] = resources.obtainTypedArray(R.array.crystals_ids).getResourceId(i, -1)
        }
        return indexes
    }
}