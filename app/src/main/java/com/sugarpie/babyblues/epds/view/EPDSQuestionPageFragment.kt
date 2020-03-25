package com.sugarpie.babyblues.epds.view

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
import com.sugarpie.babyblues.epds.viewmodel.EPDSAssessmentViewModel
import com.sugarpie.babyblues.epds.logic.EPDSPagerAdapter
import com.sugarpie.babyblues.epds.logic.EPDSQuestionPageFragmentController

class EPDSQuestionPageFragment(private val position: Int,
                               private val viewModel: EPDSAssessmentViewModel,
                               private val viewPager: ViewPager2) : Fragment() {

    private val controller =
        EPDSQuestionPageFragmentController()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_edps_question_page, container, false)
        val viewGroup = view.findViewById<ViewGroup>(R.id.content)
        viewGroup.layoutTransition?.setAnimateParentHierarchy(false)
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

        return view
    }

    companion object {
        const val TAG = "EPDSQuestionPageFragment"
    }
}