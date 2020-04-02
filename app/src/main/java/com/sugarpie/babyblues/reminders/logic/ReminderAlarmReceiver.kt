package com.sugarpie.babyblues.reminders.logic

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.sugarpie.babyblues.Log

class ReminderAlarmReceiver : BroadcastReceiver() {

    override fun onReceive(ctx: Context, intent: Intent) {
        Log.d(TAG, "onReceive ${intent.action}")

        ReminderUtils.showReminder(ctx)

        // since we can't repeat exact alarms, set it again
        ReminderUtils.setAlarm(ctx)
    }

    companion object {
        const val TAG = "ReminderAlarmReceiver"
    }
}