package com.sugarpie.babyblues.logic.epds

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sugarpie.babyblues.Log
import com.sugarpie.babyblues.ui.epds.*

/**
 * Pager adapter that represents the questionnaire for the Edinburgh postnatal depression scale
 */
class EPDSPagerAdapter(fa: FragmentActivity, private val viewModel: EPDSAssessmentViewModel) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = NUM_PAGES

    override fun createFragment(position: Int): Fragment {
        Log.d(TAG, "createFragment at position $position")

        // 0: Instructions
        // 1: Example Response
        // 2-11: Questions
        // 12: Submit
        return when (position) {
            0 -> EPDSInstructionsPageFragment()
            1 -> EPDSExampleResponsePageFragment()
            12 -> EPDSSubmitPageFragment()
            else -> EPDSQuestionPageFragment(position, viewModel) // 10 questions
        }
    }

    companion object {
        const val TAG = "EPDSPagerAdapter"
        const val NUM_PAGES = 13

        /**
         * Number of pages before the first question. Used to shift position for indexing questions
         * in the EPDSQuestionPageFragment class.
         */
        const val OFFSET_QUESTIONS = 2
    }
}