package com.sugarpie.babyblues.logic.epds

import android.view.View
import android.widget.Button
import android.widget.TextView
import com.sugarpie.babyblues.Log
import com.sugarpie.babyblues.R
import com.sugarpie.babyblues.logic.Utils

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

    fun getFinishOnClickListener(): View.OnClickListener {
        return View.OnClickListener {
            if (it == null) {
                Log.e(TAG, "Null view clicked!")
                return@OnClickListener
            }

            val act = Utils.getActivityFromView(it)
            act?.finish()
        }
    }

    companion object {
        const val TAG = "EPDSSubmitPageFragmentController"
    }
}