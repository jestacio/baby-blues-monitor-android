package com.sugarpie.babyblues.ui.assess

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.sugarpie.babyblues.Log
import com.sugarpie.babyblues.R
import com.sugarpie.babyblues.logic.assess.EPDSPagerAdapter

class EPDSQuestionPageFragment(private val position: Int) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_edps_question_page, container, false)
        val index = position - EPDSPagerAdapter.OFFSET_QUESTIONS
        Log.d(TAG, "Got index $index from position $position using offset $EPDSPagerAdapter.OFFSET_QUESTIONS")

        when(index) {
            0 -> view.findViewById<TextView>(R.id.text_history)?.setText(R.string.app_name)
        }

        return view
    }

    companion object {
        const val TAG = "EPDSQuestionPageFragment"
    }
}