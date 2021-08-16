package com.wsoteam.horoscopes.presentation.form.dialogs

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.presentation.form.FormActivity
import com.wsoteam.horoscopes.presentation.settings.SettingsFragment
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.analytics.Analytic
import kotlinx.android.synthetic.main.dialog_date.*

class DateDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.dialog_date, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(0))
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tvCancel.setOnClickListener {
            dismiss()
        }

        tvOk.setOnClickListener {
            val date = "%02d.%02d.%d".format(dpCalendar.dayOfMonth, dpCalendar.month + 1, dpCalendar.year)
//            "${"%02d".format(dpCalendar.dayOfMonth)}.${"%02d".format(dpCalendar.month + 1)}.${dpCalendar.year}"

            if(PreferencesProvider.getBirthday() != ""){
                Analytic.changeSign()
            }

            if (activity is FormActivity) {
                (activity as FormActivity).setDate(date)
            } else {
                PreferencesProvider.setBirthday(date)
                (targetFragment as SettingsFragment).setDate(date)
            }
            dismiss()
        }
    }
}