package com.wsoteam.horoscopes.presentation.premium.pager

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.wsoteam.horoscopes.R
import kotlinx.android.synthetic.main.page_onboard_fragment.*

class PageFragment : Fragment(R.layout.page_onboard_fragment) {

    companion object{
        private var TAG_ID = "TAG_ID"
        private var TAG_STRING = "TAG_STRING"

        fun newInstance(id : Int, text : String) : PageFragment{
            var bundle = Bundle()
            bundle.putInt(TAG_ID, id)
            bundle.putString(TAG_STRING, text)
            var pageFragment = PageFragment()
            pageFragment.arguments = bundle
            return pageFragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(requireContext()).load(arguments!!.getInt(TAG_ID)).into(ivPageImage)

        tvTitle.text = arguments!!.getString(TAG_STRING)
        Log.e("LOL", tvTitle.text.toString())
    }
}