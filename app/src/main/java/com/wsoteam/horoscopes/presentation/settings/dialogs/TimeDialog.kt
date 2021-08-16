package com.wsoteam.horoscopes.presentation.settings.dialogs

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.presentation.settings.SettingsFragment
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.loger.L
import kotlinx.android.synthetic.main.dialog_time.*

class TimeDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.dialog_time, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(0))
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tvSave.setOnClickListener {
            val time =
                "${"%02d".format(tpNotif.currentHour)}:${"%02d".format(tpNotif.currentMinute)}"
            L.log(time)
            PreferencesProvider.setNotifTime(time)
            (targetFragment as SettingsFragment).setTime(time)
            dismiss()
        }
    }
}