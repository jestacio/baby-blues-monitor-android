package com.sugarpie.babyblues.logic.assess

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sugarpie.babyblues.Log
import com.sugarpie.babyblues.ui.assess.ScreenSlidePageFragment

/**
 * Pager adapter that represents the questionnaire for the Edinburgh postnatal depression scale
 */
class EPDSPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = NUM_PAGES

    override fun createFragment(position: Int): Fragment {
        Log.d(TAG, "createFragment at position $position")

        // 0: Instructions
        // 1-10: Questions
        // 11: Submit
        return when (position) {
            0 -> ScreenSlidePageFragment() // todo implement different fragment for this
            11 -> ScreenSlidePageFragment() // todo implement different fragment for this
            else -> ScreenSlidePageFragment()
        }
    }

    companion object {
        const val TAG = "EPDSPagerAdapter"
        const val NUM_PAGES = 11
    }
}