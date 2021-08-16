package com.wsoteam.horoscopes.presentation.empty

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.wsoteam.horoscopes.MainActivity
import com.wsoteam.horoscopes.R
import kotlinx.android.synthetic.main.connection_fragment.*

class ConnectionFragment : Fragment(R.layout.connection_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvReload.setOnClickListener {
            (activity as MainActivity).reloadNetState()
        }

    }
}