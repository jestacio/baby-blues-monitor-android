package com.sugarpie.babyblues.home.logic

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.TextView
import com.sugarpie.babyblues.Log
import com.sugarpie.babyblues.R
import com.sugarpie.babyblues.settings.SettingsData
import com.sugarpie.babyblues.epds.view.EPDSAssessmentActivity
import com.sugarpie.babyblues.Utils
import java.util.*

class HomeFragmentController {

    fun updateViews(it: SettingsData, textView: TextView) {
        val settingsMap = it.map
        val reminderTimestamp = settingsMap[SettingsData.KEY_REMINDERTIMESTAMP] as GregorianCalendar
        textView.text = if (it.reminderTimestampHasBeenSet())
            "The next time we'll check on your mental health will be ${reminderTimestamp.get(
                Calendar.YEAR)} ${reminderTimestamp.get(Calendar.MONTH)} ${reminderTimestamp.get(
                Calendar.DAY_OF_MONTH)}"
        else
            "Go to Settings to set up your reminder."
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