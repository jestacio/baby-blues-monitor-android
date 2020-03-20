package com.sugarpie.babyblues.epds.logic

import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.sugarpie.babyblues.Log
import com.sugarpie.babyblues.R
import com.sugarpie.babyblues.SharedExtras
import com.sugarpie.babyblues.Utils
import com.sugarpie.babyblues.epds.view.EPDSScoreActivity
import com.sugarpie.babyblues.epds.viewmodel.EPDSAssessmentViewModel

class EPDSSubmitPageFragmentController {
    fun updateViews(score: Int, text_instructions: TextView, button_finish: Button) {
        if (score < 0) {
            text_instructions.setText(R.string.epds_submit_instructions_incomplete)
            button_finish.isEnabled = false
        } else {
            text_instructions.setText(R.string.epds_submit_instructions_complete)
            button_finish.isEnabled = true
        }
    }

    fun getFinishOnClickListener(viewModel: EPDSAssessmentViewModel): View.OnClickListener {
        return View.OnClickListener {
            if (it == null) {
                Log.e(TAG, "Null view clicked!")
                return@OnClickListener
            }

            val act = Utils.getActivityFromView(it)

            // todo persist responses for history

            if (act != null) {
                val intent = Intent()
                intent.setClass(act.applicationContext, EPDSScoreActivity::class.java)
                intent.putExtra(SharedExtras.EXTRA_SCORE, viewModel.getScore().value)
                act.startActivity(intent)
            }

            act?.finish()
        }
    }

    companion object {
        const val TAG = "EPDSSubmitPageFragmentController"
    }
}