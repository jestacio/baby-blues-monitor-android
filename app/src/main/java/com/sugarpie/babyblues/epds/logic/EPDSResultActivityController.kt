package com.sugarpie.babyblues.epds.logic

import android.widget.Button
import android.widget.TextView
import com.sugarpie.babyblues.R

class EPDSResultActivityController: EPDSScoreActivityController() {

    fun updateViews(score: Int, textResult: String, button_finish: Button,
                    text_result: TextView) {
        text_result.text = textResult

        if (score >= MIN_HIGH_SCORE) {
            button_finish.setText(R.string.get_help)
        } else {
            button_finish.setText(R.string.finish)
        }
    }

    companion object {
        const val TAG = "EPDSResultActivityController"
    }
}
