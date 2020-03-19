package com.sugarpie.babyblues.logic.epds

import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.core.view.size
import androidx.viewpager2.widget.ViewPager2
import com.sugarpie.babyblues.Log
import com.sugarpie.babyblues.R
import com.sugarpie.babyblues.data.epds.EPDSQuestionData
import com.sugarpie.babyblues.ui.epds.EPDSAssessmentViewModel

class EPDSQuestionPageFragmentController {

    fun getRGOnCheckedChangeListener(viewModel: EPDSAssessmentViewModel, questionIdx: Int,
                                     viewPager: ViewPager2): RadioGroup.OnCheckedChangeListener {
        return RadioGroup.OnCheckedChangeListener { _: RadioGroup, checkedId: Int ->
            val selectedIdx = when (checkedId) {
                R.id.epds_response0 -> 0
                R.id.epds_response1 -> 1
                R.id.epds_response2 -> 2
                R.id.epds_response3 -> 3
                else -> -1
            }

            Log.d(TAG, "Got $selectedIdx from $checkedId")

            if (selectedIdx >= 0) {
                Log.d(TAG, "OnCheckedChangeListener() viewPager.currentItem=${viewPager.currentItem}")
                viewPager.currentItem = viewPager.currentItem + 1
            }

            viewModel.updateResponse(questionIdx, selectedIdx)
        }
    }

    fun updateViews(it: EPDSQuestionData, view: View) {
        val question = view.findViewById<TextView>(R.id.epds_question)
        val rg = view.findViewById<RadioGroup>(R.id.epds_radiogroup)
        val resp0 = view.findViewById<RadioButton>(R.id.epds_response0)
        val resp1 = view.findViewById<RadioButton>(R.id.epds_response1)
        val resp2 = view.findViewById<RadioButton>(R.id.epds_response2)
        val resp3 = view.findViewById<RadioButton>(R.id.epds_response3)

        // set selected radio button, or clear selection
        when (it.selectedResponse) {
            0 -> resp0.isSelected = true
            1 -> resp1.isSelected = true
            2 -> resp2.isSelected = true
            3 -> resp3.isSelected = true
            else -> rg.check(-1)
        }

        question.text = it.question
        resp0.text = it.responses[0].text
        resp1.text = it.responses[1].text
        resp2.text = it.responses[2].text
        resp3.text = it.responses[3].text
    }

    companion object {
        const val TAG = "EPDSQuestionPageFragmentController"
    }
}