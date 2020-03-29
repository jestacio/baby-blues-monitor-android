package com.sugarpie.babyblues.reminders.logic

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.preference.PreferenceManager
import com.sugarpie.babyblues.Log
import com.sugarpie.babyblues.reminders.view.TimePreference
import java.util.*

class ReminderUtils {

    companion object {

        const val TAG = "ReminderUtils"
        private const val KEY_REMINDER_ENABLED = "reminder_enabled"
        private const val KEY_REMINDER_DAYOFWEEK = "reminder_dayofweek"
        private const val KEY_REMINDER_TIME = "reminder_time"
        private const val DEFAULT_REMINDER_ENABLED = false
        private const val DEFAULT_REMINDER_TIME = TimePreference.DEFAULT_VALUE
        private const val DEFAULT_REMINDER_DAYOFWEEK = "Saturday"
        private const val REQUESTCODE_EPDS_REMINDER = 0
        private const val ACTION_EPDS_REMINDER = "com.sugarpie.babyblues.EPDS_REMINDER_WEEKLY"

        /**
         * Sets the alarm used to show the reminder notification. If no settings for reminders, then
         * this does nothing. Use repository here if we want to extend beyond pure local settings.
         */
        fun setAlarm(ctx: Context) {
            val appCtx = ctx.applicationContext
            val alarmManager = appCtx.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val prefs = PreferenceManager.getDefaultSharedPreferences(appCtx)
            val enabled = remindersAreEnabled(appCtx)
            val dayOfWeek = prefs.getString(KEY_REMINDER_DAYOFWEEK, DEFAULT_REMINDER_DAYOFWEEK)
            val time = prefs.getString(KEY_REMINDER_TIME, DEFAULT_REMINDER_TIME)

            Log.d(TAG, "setAlarm $enabled $dayOfWeek $time")

            val receiver = ComponentName(appCtx, ReminderBootReceiver::class.java)
            val broadcastIntent = Intent().apply {
                action = ACTION_EPDS_REMINDER
            }
            val pIntent = PendingIntent.getBroadcast(appCtx, REQUESTCODE_EPDS_REMINDER,
                broadcastIntent, 0)

            if (enabled) {
                // turn on boot receiver to enable alarms across power cycles
                appCtx.packageManager.setComponentEnabledSetting(
                    receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP)
            } else {
                // disable unnecessary boot receiver, disable any previous alarms, then return
                appCtx.packageManager.setComponentEnabledSetting(
                    receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                    PackageManager.DONT_KILL_APP)

                alarmManager.cancel(pIntent)

                Log.d(TAG, "setAlarm() reminders not enabled, returning")
                return
            }

            val calendar = GregorianCalendar.getInstance()
            val intervalWeek = AlarmManager.INTERVAL_FIFTEEN_MINUTES / 5 //AlarmManager.INTERVAL_DAY * 7

            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                intervalWeek,
                pIntent
            )
        }

        private fun remindersAreEnabled(ctx: Context) : Boolean {
            val appCtx = ctx.applicationContext
            val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(appCtx)

            return sharedPrefs.getBoolean(KEY_REMINDER_ENABLED, DEFAULT_REMINDER_ENABLED)
        }
    }
}