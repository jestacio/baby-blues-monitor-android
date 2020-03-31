package com.sugarpie.babyblues.home.logic

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.TextView
import com.sugarpie.babyblues.Log
import com.sugarpie.babyblues.R
import com.sugarpie.babyblues.settings.SettingsData
import com.sugarpie.babyblues.epds.view.EPDSAssessmentActivity
import com.sugarpie.babyblues.Utils
import com.sugarpie.babyblues.reminders.logic.ReminderUtils
import java.util.*

class HomeFragmentController {

    fun updateViews(ctx: Context, it: SettingsData, textView: TextView) {
        val appCtx = ctx.applicationContext
        val res = appCtx.resources
        val settingsMap = it.map
        val enabled = settingsMap[ReminderUtils.KEY_REMINDER_ENABLED]
        val dayOfWeek = settingsMap[ReminderUtils.KEY_REMINDER_DAYOFWEEK]
        val time = settingsMap[ReminderUtils.KEY_REMINDER_TIME]

        Log.d(TAG, "updateViews $enabled $dayOfWeek $time")

        textView.text = if (it.reminderTimestampHasBeenSet() && enabled == true) {
            String.format(Locale.getDefault(), res.getString(R.string.text_reminderset),
                dayOfWeek, time)
        } else {
            res.getString(R.string.text_remindernotset)
        }
    }

    fun getClickListener(view: View): View.OnClickListener? {
        return when (view.id) {
            R.id.button_checkhealth -> View.OnClickListener {
                Log.d(TAG, "clicked")
                val activity: Activity? =
                    Utils.getActivityFromView(
                        it
                    )

                activity ?: return@OnClickListener

                val intent = Intent()
                intent.setClass(activity, EPDSAssessmentActivity::class.java)
                activity.startActivity(intent)
            }
            else -> null
        }
    }

    companion object {
        const val TAG = "HomeFragmentController"
    }
}