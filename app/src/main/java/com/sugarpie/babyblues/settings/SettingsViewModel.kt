package com.sugarpie.babyblues.settings

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import com.sugarpie.babyblues.Log
import com.sugarpie.babyblues.reminders.logic.ReminderUtils

class SettingsViewModel : ViewModel() {

    val settings = MutableLiveData<SettingsData>().apply {
        value = SettingsData(mapOf(
            ReminderUtils.KEY_REMINDER_ENABLED to ReminderUtils.DEFAULT_REMINDER_ENABLED,
            ReminderUtils.KEY_REMINDER_DAYOFWEEK to ReminderUtils.DEFAULT_REMINDER_DAYOFWEEK,
            ReminderUtils.KEY_REMINDER_TIME to ReminderUtils.DEFAULT_REMINDER_TIME))
    }

    fun handleSettingsUpdated(ctx: Context) {
        val appCtx = ctx.applicationContext
        val prefs = PreferenceManager.getDefaultSharedPreferences(appCtx)

        val enabled = prefs.getBoolean(ReminderUtils.KEY_REMINDER_ENABLED,
            ReminderUtils.DEFAULT_REMINDER_ENABLED)
        val dayOfWeek = prefs.getString(ReminderUtils.KEY_REMINDER_DAYOFWEEK,
            ReminderUtils.DEFAULT_REMINDER_DAYOFWEEK)
        val time = prefs.getString(ReminderUtils.KEY_REMINDER_TIME,
            ReminderUtils.DEFAULT_REMINDER_TIME)

        Log.d(TAG, "handleSettingsUpdated $enabled $dayOfWeek $time")

        settings.apply {
            value = SettingsData(mapOf(
                ReminderUtils.KEY_REMINDER_ENABLED to enabled,
                ReminderUtils.KEY_REMINDER_DAYOFWEEK to dayOfWeek,
                ReminderUtils.KEY_REMINDER_TIME to time))
        }
    }

    companion object {
        const val TAG = "SettingsViewModel"
    }
}