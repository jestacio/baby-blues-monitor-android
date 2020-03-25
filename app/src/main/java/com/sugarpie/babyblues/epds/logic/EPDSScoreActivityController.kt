package com.sugarpie.babyblues.epds.logic

import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.sugarpie.babyblues.Log
import com.sugarpie.babyblues.R
import com.sugarpie.babyblues.Utils
import com.sugarpie.babyblues.epds.view.EPDSResourcesActivity

open class EPDSScoreActivityController {

    fun updateViews(score: Int, button_finish: Button, text_result: TextView) {
        if (score >= MIN_HIGH_SCORE) {
            button_finish.setText(R.string.get_help)
            text_result.setText(R.string.epds_score_high)
        } else {
            button_finish.setText(R.string.finish)
            text_result.setText(R.string.epds_score_low)
        }
    }

    fun getShareClickListener(epdsText: String): View.OnClickListener {
        return View.OnClickListener {
            it ?: return@OnClickListener

            val act = Utils.getActivityFromView(it)

            act ?: return@OnClickListener

            val res = act.resources

            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, epdsText)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent,
                res.getString(R.string.epds_assessment_name))
            act.startActivity(shareIntent)
        }
    }

    fun getFinishClickListener(score: Int): View.OnClickListener {
        if (score < 0) {
            Log.e(TAG, "getFinishClickListener() score is negative! $score")
        }

        return View.OnClickListener {

            val act = Utils.getActivityFromView(it)

            act ?: return@OnClickListener

            when {
                score >= MIN_HIGH_SCORE -> {
                    val intent = Intent().apply {
                        setClass(act.applicationContext, EPDSResourcesActivity::class.java)
                    }
                    act.startActivity(intent)
                    act.finish()
                }
                else -> {
                    act.finish()
                }
            }
        }
    }

    companion object {
        const val TAG = "EPDSScoreActivityController"
        const val MIN_HIGH_SCORE = 10 // 10 or higher indicates possible depression
    }
}
