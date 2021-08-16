package com.wsoteam.horoscopes.presentation.premium

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.wsoteam.horoscopes.MainActivity
import com.wsoteam.horoscopes.R

class PaySuccessActivity : AppCompatActivity(R.layout.pay_success_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var cdTimer = object : CountDownTimer(2000, 100){
            override fun onFinish() {
                startActivity(Intent(this@PaySuccessActivity, MainActivity::class.java))
                finishAffinity()
            }

            override fun onTick(millisUntilFinished: Long) {
            }
        }
        cdTimer.start()
    }

    override fun onBackPressed() {
        //super.onBackPressed()
    }
}