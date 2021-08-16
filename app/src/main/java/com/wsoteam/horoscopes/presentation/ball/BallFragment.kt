package com.wsoteam.horoscopes.presentation.ball

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.gms.ads.AdRequest
import com.wsoteam.horoscopes.Config
import com.wsoteam.horoscopes.MainActivity
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.presentation.ball.dialogs.NoAttemptsDialogFragment
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.ads.AdWorker
import com.wsoteam.horoscopes.utils.analytics.Analytic
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_magic_ball.*
import java.util.*

class BallFragment : Fragment(R.layout.fragment_magic_ball) {

    private lateinit var answersArray: Array<String>
    private lateinit var animator: AnimatorSet

    private var counter = 0

    private var firstShow = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bigSize(answerField)

        answersArray = resources.getStringArray(R.array.answers)
        animator = tapAnimator()

        ballBackground.setOnClickListener(this::clickOnBall)
        actionPremium.setOnClickListener(this::openPremium)

        updateUI()

        if (!firstShow) {
            firstShow = true
        }
    }

    private fun setButtonEnabled(isEnabled: Boolean) {
        actionPremium.isEnabled = isEnabled
        ballBackground.isEnabled = isEnabled
    }

    private fun openPremium(view: View) {
        PreferencesProvider.setBeforePremium(Analytic.ball_premium)
        (activity as MainActivity).openPrem()
    }

    private fun clickOnBall(view: View) {
        Analytic.touchBalls()
        counter++
        setButtonEnabled(false)
        animator.start()
    }

    private fun updateState() {

        if (PreferencesProvider.attempts < Config.ATTEMPTS_FOR_DAY) {
            val unlockDate = Calendar.getInstance()
            unlockDate.timeInMillis =
                PreferencesProvider.timeMillis + Config.MILLIS_FOR_NEW_ATTEMPTS

            if (Calendar.getInstance().after(unlockDate)) {
                PreferencesProvider.attempts = Config.ATTEMPTS_FOR_DAY
            }
        }

        updateUI()
    }

    private fun updateUI() {
        if (!PreferencesProvider.isADEnabled()) {
            attemptsText.visibility = View.INVISIBLE
            actionPremium.visibility = View.INVISIBLE
        } else {
            attemptsText.visibility = View.VISIBLE
            actionPremium.visibility = View.VISIBLE
            attemptsText.text =
                getString(R.string.d_of_d, PreferencesProvider.attempts, Config.ATTEMPTS_FOR_DAY)
        }
    }

    private val timeTickBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            updateState()
        }
    }

    override fun onStart() {
        super.onStart()
        activity?.registerReceiver(
            timeTickBroadcastReceiver,
            IntentFilter(Intent.ACTION_TIME_TICK)
        )
    }

    override fun onResume() {
        super.onResume()
        updateState()
    }

    override fun onStop() {
        super.onStop()
        try {
            activity?.unregisterReceiver(timeTickBroadcastReceiver)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun bigSize(textView: TextView) {
        val span: Spannable = SpannableString(textView.text)
        span.setSpan(
            RelativeSizeSpan(2.0f),
            0,
            textView.text.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        textView.text = span
    }

    private fun nextAnswer() {
        if (!PreferencesProvider.isADEnabled()) {
            answerField.text = answersArray.random()
            PreferencesProvider.attempts = Config.ATTEMPTS_FOR_DAY
            PreferencesProvider.timeMillis = Calendar.getInstance().timeInMillis
            return
        }

        if (PreferencesProvider.attempts > 0) {
            answerField.text = answersArray.random()
//            answerField.text = answersArray[PreferencesProvider.attempts %  answersArray.size]  //TODO для теста, меняет слова по очереди
            PreferencesProvider.attempts--
            PreferencesProvider.timeMillis = Calendar.getInstance().timeInMillis
            updateState()
        } else {
            answerField.text = getString(R.string.answer_init)
            bigSize(answerField)
            NoAttemptsDialogFragment.show(childFragmentManager).lifecycle.addObserver(object :
                LifecycleObserver {
                @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                fun onDestroy() {
                    updateState()
                }
            })
        }
    }

    private fun tapAnimator(): AnimatorSet {

        fun rotate(view: View, p1: Float, p2: Float, duration: Long) =
            ObjectAnimator.ofFloat(view, "rotation", p1, p2).setDuration(duration)

        fun rotationSet(p1: Float, p2: Float, duration: Long = 200) = AnimatorSet().apply {
            play(rotate(ballForeground, p1, p2, duration))
                .with(rotate(ballTriangle, p1, p2, duration))
                .with(rotate(answerField, p1, p2, duration))
        }

        fun scale(view: View, duration: Long, scale: Float) = AnimatorSet().apply {
            play(ObjectAnimator.ofFloat(view, "scaleX", scale).setDuration(duration))
                .with(ObjectAnimator.ofFloat(view, "scaleY", scale).setDuration(duration))
        }

        fun scaleSet(duration: Long, value: Float) = AnimatorSet().apply {
            play(scale(ballTriangle, duration, value))
                .with(scale(answerField, duration, value))
                .with(ObjectAnimator.ofFloat(answerField, "alpha", value))
        }

        val rSet = AnimatorSet().apply {
            playSequentially(
                listOf(
                    rotationSet(0f, 20f),
                    rotationSet(20f, -10f),
                    rotationSet(-10f, 20f),
                    rotationSet(20f, -5f),
                    rotationSet(-5f, 10f),
                    rotationSet(10f, 0f)
                )
            )
        }

        val sSet = AnimatorSet().apply {
            play(scaleSet(400, 0.0f))
                .before(scaleSet(400, 1.0f).apply {
                    addListener(object : Animator.AnimatorListener {
                        override fun onAnimationRepeat(animation: Animator?) {}

                        override fun onAnimationEnd(animation: Animator?) {
                            setButtonEnabled(true)
                            if (PreferencesProvider.isADEnabled()) {
                                AdWorker.showInter()
                            }
                        }

                        override fun onAnimationCancel(animation: Animator?) {}

                        override fun onAnimationStart(animation: Animator?) {
                            nextAnswer()
                        }
                    })
                })
        }

        return AnimatorSet().apply {
            play(rSet).before(sSet)
        }
    }
}