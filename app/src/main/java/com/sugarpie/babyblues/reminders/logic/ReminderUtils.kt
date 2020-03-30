package com.sugarpie.babyblues.reminders.logic

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.preference.PreferenceManager
import com.sugarpie.babyblues.Log
import com.sugarpie.babyblues.R
import com.sugarpie.babyblues.epds.view.EPDSAssessmentActivity
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
        private const val NOTIFICATION_CHANNEL_EPDS_REMINDER = "NOTIFICATION_CHANNEL_EPDS_REMINDER"
        private const val NOTIFICATION_ID_EPDS_REMINDER = 0

        private fun convertDayOfWeekFromString(str: String) : Int {
            val retval = when (str) {
                "Monday" -> 1
                "Tuesday" -> 2
                "Wednesday" -> 3
                "Thursday" -> 4
                "Friday" -> 5
                "Saturday" -> 6
                else -> 7
            }

            Log.d(TAG, "convertDayOfWeekFromString $str $retval")

            return retval
        }

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
            val timeTokens = time!!.split(":")
            val hourOfDay = timeTokens[0].toInt()
            val minute = timeTokens[1].toInt()
            val calendar = GregorianCalendar.getInstance()
            val intervalWeek = AlarmManager.INTERVAL_DAY * 7
            val bootReceiver = ComponentName(appCtx, ReminderBootReceiver::class.java)
            val broadcastIntent = Intent(appCtx, ReminderAlarmReceiver::class.java)
            val pIntent = PendingIntent.getBroadcast(appCtx, REQUESTCODE_EPDS_REMINDER,
                broadcastIntent, 0)

            broadcastIntent.action = ACTION_EPDS_REMINDER
            broadcastIntent.flags = Intent.FLAG_RECEIVER_REPLACE_PENDING

            Log.d(TAG, "setAlarm $enabled $dayOfWeek $time")

            calendar.set(Calendar.DAY_OF_WEEK, convertDayOfWeekFromString(dayOfWeek!!))
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.HOUR_OF_DAY, minute)

            Log.d(TAG, "setAlarm System.currentTimeMillis ${System.currentTimeMillis()}")
            Log.d(TAG, "setAlarm calendar time ${calendar.timeInMillis}")

            if (enabled) {
                // turn on boot receiver to enable alarms across power cycles
                appCtx.packageManager.setComponentEnabledSetting(bootReceiver,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP)

                alarmManager.setRepeating(AlarmManager.RTC, calendar.timeInMillis,
                    intervalWeek, pIntent)

                Log.d(TAG, "setAlarm() enabled boot receiver for reminder alarms")
                Log.d(TAG, "setAlarm() set alarm with interval $intervalWeek")
            } else {
                // disable unnecessary boot receiver, disable any previous alarms, then return
                appCtx.packageManager.setComponentEnabledSetting(bootReceiver,
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP)

                alarmManager.cancel(pIntent)

                Log.d(TAG, "setAlarm() disabled boot receiver and canceled pending intent")
            }
        }

        private fun remindersAreEnabled(ctx: Context) : Boolean {
            val appCtx = ctx.applicationContext
            val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(appCtx)

            return sharedPrefs.getBoolean(KEY_REMINDER_ENABLED, DEFAULT_REMINDER_ENABLED)
        }

        fun createNotificationChannel(ctx: Context) {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                return
            }

            val appCtx = ctx.applicationContext
            val res = appCtx.resources
            val name = res.getString(R.string.notificationchannel_name_epds_reminder)
            val descriptionText = res.getString(R.string.notificationchannel_description_epds_reminder)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_EPDS_REMINDER, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                appCtx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        fun showReminder(ctx: Context) {
            val appCtx = ctx.applicationContext
            val res = appCtx.resources
            val title = res.getString(R.string.reminder_epds_weekly_title)
            val text = res.getString(R.string.reminder_epds_weekly_text)

            // Create an explicit intent for an Activity in your app
            val intent = Intent(appCtx, EPDSAssessmentActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pIntent: PendingIntent = PendingIntent.getActivity(appCtx, 0, intent, 0)

            val builder = NotificationCompat.Builder(appCtx, NOTIFICATION_CHANNEL_EPDS_REMINDER)
                .setSmallIcon(R.drawable.ic_favorite_black_24dp)
                .setContentTitle(title)
                .setContentText(text)
                .setStyle(NotificationCompat.BigTextStyle().bigText(text))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pIntent)
                .setAutoCancel(true)

            with(NotificationManagerCompat.from(appCtx)) {
                // notificationId is a unique int for each notification that you must define
                notify(NOTIFICATION_ID_EPDS_REMINDER, builder.build())
            }
        }
    }
}