package com.sugarpie.babyblues.logic.epds

import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import com.sugarpie.babyblues.R
import com.sugarpie.babyblues.data.epds.EPDSQuestionData

class EPDSQuestionPageFragmentController {

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
        resp0.text = it.response0
        resp1.text = it.response1
        resp2.text = it.response2
        resp3.text = it.response3
    }
}