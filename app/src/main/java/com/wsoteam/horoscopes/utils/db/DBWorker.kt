package com.wsoteam.horoscopes.utils.db

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object DBWorker {
    fun requestPercent(path : String, dbCallbacks: DBCallbaks) {


        FirebaseDatabase
            .getInstance("https://horoscopeweb2-default-rtdb.firebaseio.com/")
            .reference
            .child(path)
            .addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onCancelled(p0: DatabaseError) {
                    dbCallbacks.onError()
                    Log.e("LOL", "onCancelled")
                }

                override fun onDataChange(p0: DataSnapshot) {
                    Log.e("LOL", "onDataChange")
                    if (p0.getValue(String::class.java) != null) {
                        val url : String = p0.getValue(String::class.java) ?: ""
                        Log.e("LOL", url)
                        if (url == ""){
                            dbCallbacks.onError()
                        }else{
                            dbCallbacks.onSuccess(url)
                        }
                    } else {
                        dbCallbacks.onError()
                    }
                }
            })
    }
}