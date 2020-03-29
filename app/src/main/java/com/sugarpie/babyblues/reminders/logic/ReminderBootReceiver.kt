package com.sugarpie.babyblues.reminders.logic

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.sugarpie.babyblues.Log

/**
 * This receiver will re-register the alarms after a power cycle of the device if needed. The logic
 * for checking the user settings to see if the reminder is enabled is in the ReminderUtils class.
 */
class ReminderBootReceiver : BroadcastReceiver() {

    override fun onReceive(ctx: Context, intent: Intent) {
        Log.d(TAG, "onReceive")
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            ReminderUtils.setAlarm(ctx)
        }
    }

    companion object {
        const val TAG = "ReminderBootReceiver"
    }
}