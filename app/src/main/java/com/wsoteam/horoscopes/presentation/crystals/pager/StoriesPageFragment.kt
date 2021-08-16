package com.wsoteam.horoscopes.presentation.crystals.pager

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.wsoteam.horoscopes.R
import kotlinx.android.synthetic.main.stories_page_fragment.*
import kotlinx.android.synthetic.main.text_vh.*

class StoriesPageFragment : Fragment(R.layout.stories_page_fragment) {

    companion object {

        const val TAG_IMAGE = "TAG_IMAGE"
        const val TAG_TEXT = "TAG_TEXT"

        fun newInstance(text: String, idImage: Int): StoriesPageFragment {
            var bundle = Bundle()
            bundle.putString(TAG_TEXT, text)
            bundle.putInt(TAG_IMAGE, idImage)
            var storiesPageFragment = StoriesPageFragment()
            storiesPageFragment.arguments = bundle
            return storiesPageFragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvStories.text = requireArguments().getString(TAG_TEXT)
        ivStories.setImageResource(requireArguments().getInt(TAG_IMAGE))
    }
}