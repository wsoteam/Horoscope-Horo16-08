package com.wsoteam.horoscopes.presentation.ball.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import com.wsoteam.horoscopes.Config
import com.wsoteam.horoscopes.MainActivity
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.ads.AdWorker
import com.wsoteam.horoscopes.utils.analytics.Analytic
import fr.tvbarthel.lib.blurdialogfragment.SupportBlurDialogFragment
import kotlinx.android.synthetic.main.dialog_fragment_no_attempts.*
import java.util.*
import java.util.concurrent.TimeUnit

class NoAttemptsDialogFragment : SupportBlurDialogFragment() {
    companion object {
        private const val TAG = "NoAttemptsDialogFragment"

        fun show(fragmentManager: FragmentManager?) = NoAttemptsDialogFragment().apply {
            fragmentManager?.also { show(it, TAG) }
        }
    }

    private var timer: CountDownTimer? = null
    private lateinit var time: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        dialog?.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            requestFeature(Window.FEATURE_NO_TITLE)
        }
        return inflater.inflate(R.layout.dialog_fragment_no_attempts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.actionClose).setOnClickListener {
            dismiss()
            AdWorker.showInter()
        }
        view.findViewById<View>(R.id.actionPremium).setOnClickListener {  }
        time = view.findViewById(R.id.timer)

        actionPremium.setOnClickListener {
            PreferencesProvider.setBeforePremium(Analytic.ball_alert_premium)
            (activity as MainActivity).openPrem()
        }
    }

    override fun onStart() {
        super.onStart()
        startTimer()
    }

    override fun onStop() {
        super.onStop()
        timer?.cancel()
    }

    private fun startTimer() {

        val millis =
            PreferencesProvider.timeMillis + Config.MILLIS_FOR_NEW_ATTEMPTS - Calendar.getInstance().timeInMillis


        timer = object : CountDownTimer(millis, 1000) {
            override fun onFinish() {
                PreferencesProvider.attempts = Config.ATTEMPTS_FOR_DAY
                updateTime(0)
            }

            override fun onTick(millisUntilFinished: Long) {
                updateTime(millisUntilFinished)
            }
        }
        timer?.start()
    }

    private fun updateTime(millis: Long){

        val hours = TimeUnit.MILLISECONDS.toHours(millis)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(millis) % 60
        val seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % 60

        time.text = getString(R.string.time_format, hours, minutes, seconds)
    }
}