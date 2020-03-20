package com.sugarpie.babyblues.epds.logic

import android.content.Intent
import android.view.View
import com.sugarpie.babyblues.R
import com.sugarpie.babyblues.Utils

class EPDSScoreActivityController {

    fun getFinishClickListener(score: Int): View.OnClickListener {
        return View.OnClickListener {
            if (score >= 10) {

            }
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
}
