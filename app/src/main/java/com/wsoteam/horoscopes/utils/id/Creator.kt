package com.wsoteam.horoscopes.utils.id

import com.wsoteam.horoscopes.utils.PreferencesProvider
import java.util.*

object Creator {

    fun getId() : String{
        var id = ""
        if (PreferencesProvider.userID == PreferencesProvider.ID_EMPTY){
            PreferencesProvider.userID = UUID.randomUUID().toString()
            id = PreferencesProvider.userID
        }else{
            id = PreferencesProvider.userID
        }
        return id
    }

}