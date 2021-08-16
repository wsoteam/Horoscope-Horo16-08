package com.wsoteam.horoscopes.notification

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import com.wsoteam.horoscopes.utils.PreferencesProvider


class NotificationService: JobIntentService() {

    companion object{
        private const val JOB_ID = 1

        fun  enqueueWork(context: Context, work: Intent){
            enqueueWork(context, NotificationService::class.java, JOB_ID, work)
        }
    }

    override fun onHandleWork(intent: Intent) {
        if(PreferencesProvider.getNotifStatus()){
            val (hours, minutes) = PreferencesProvider.getNotifTime().split(":").map { it.toInt() }
            AlarmReceiver.startNotification(applicationContext, hours, minutes)
            EveningAlarmReceiver.startNotification(applicationContext, 18, 0)
        }
    }

}