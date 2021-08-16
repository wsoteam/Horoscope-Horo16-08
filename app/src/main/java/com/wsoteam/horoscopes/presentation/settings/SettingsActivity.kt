package com.wsoteam.horoscopes.presentation.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.utils.analytics.Analytic
import kotlinx.android.synthetic.main.settings_activity.*

class SettingsActivity : AppCompatActivity(R.layout.settings_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Analytic.openSettings()
        supportFragmentManager.beginTransaction().replace(R.id.flContainer, SettingsFragment()).commit()

        ivBack.setOnClickListener {
            onBackPressed()
        }
    }
}