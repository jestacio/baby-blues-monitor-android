package com.sugarpie.babyblues.reminders.logic

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.sugarpie.babyblues.Log

class ReminderAlarmReceiver : BroadcastReceiver() {

    override fun onReceive(ctx: Context, intent: Intent) {
        Log.d(TAG, "onReceive")
    }

    companion object {
        const val TAG = "ReminderAlarmReceiver"
    }
}