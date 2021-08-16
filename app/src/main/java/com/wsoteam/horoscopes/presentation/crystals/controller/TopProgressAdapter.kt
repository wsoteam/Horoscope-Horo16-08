package com.wsoteam.horoscopes.presentation.crystals.controller

import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wsoteam.horoscopes.presentation.crystals.IStories

class TopProgressAdapter(val iStories: IStories) : RecyclerView.Adapter<TopProgressVH>() {

    val COUNT = 5
    var currentProgressBar = 0
    val statesArray = arrayListOf<Int>(
        ProgressConfig.EMPTY,
        ProgressConfig.EMPTY,
        ProgressConfig.EMPTY,
        ProgressConfig.EMPTY,
        ProgressConfig.EMPTY
    )


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopProgressVH {
        return TopProgressVH(LayoutInflater.from(parent.context), parent)
    }

    override fun getItemCount(): Int {
        return COUNT
    }

    override fun onBindViewHolder(holder: TopProgressVH, position: Int) {
        holder.bind(statesArray[position], object : ICounter {
            override fun endCount() {
                statesArray[position] = ProgressConfig.FIN
                if (position < COUNT) {
                    if (position < COUNT - 1) {
                        statesArray[position + 1] = ProgressConfig.NEED_START
                        iStories.onNext()
                        currentProgressBar = position + 1
                    }
                    notifyDataSetChanged()
                } else {
                    finishLoad()
                }
            }
        })
    }

    private fun finishLoad() {
        iStories.onFinish()
    }

    fun start() {
        statesArray[0] = ProgressConfig.NEED_START
        notifyDataSetChanged()

    }

    fun next() {
        if (currentProgressBar < COUNT - 1) {
            statesArray[currentProgressBar] = ProgressConfig.FIN
            currentProgressBar++
            statesArray[currentProgressBar] = ProgressConfig.NEED_START
            notifyDataSetChanged()
            iStories.onNext()
            logList()
        } else {
            finishLoad()
        }
    }

    private fun logList() {
        Log.e("LOL",  "states --- ${statesArray[0]} -- ${statesArray[1]} -- ${statesArray[2]} -- ${statesArray[3]} -- ${statesArray[4]} -- ")
    }


    fun previous() {
        if (currentProgressBar > 0) {
            statesArray[currentProgressBar] = ProgressConfig.EMPTY
            currentProgressBar--
            statesArray[currentProgressBar] = ProgressConfig.NEED_START
            notifyDataSetChanged()
            iStories.onPrevious()
        } else {
            statesArray[currentProgressBar] = ProgressConfig.NEED_START
            notifyDataSetChanged()
        }
    }
}