package com.sugarpie.babyblues.ui.epds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.sugarpie.babyblues.Log
import com.sugarpie.babyblues.R
import com.sugarpie.babyblues.logic.epds.EPDSPagerAdapter
import com.sugarpie.babyblues.logic.epds.EPDSQuestionPageFragmentController

class EPDSQuestionPageFragment(private val position: Int,
                               private val viewModel: EPDSAssessmentViewModel,
                               private val viewPager: ViewPager2) : Fragment() {

    private val controller = EPDSQuestionPageFragmentController()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_edps_question_page, container, false)
        val index = position - EPDSPagerAdapter.OFFSET_QUESTIONS
        Log.d(TAG, "Got index $index from position $position using offset ${EPDSPagerAdapter.OFFSET_QUESTIONS}")

        viewModel.getQuestionData(index).observe(this, Observer {
            if (it != null) {
                controller.updateViews(it, view)
            }
        })

        val rg = view.findViewById<RadioGroup>(R.id.epds_radiogroup)
        rg.setOnCheckedChangeListener(controller.getRGOnCheckedChangeListener(viewModel, index,
            viewPager))

        when(index) {
            0 -> view.findViewById<TextView>(R.id.text_history)?.setText(R.string.app_name)
        }

        return view
    }

    companion object {
        const val TAG = "EPDSQuestionPageFragment"
    }
}