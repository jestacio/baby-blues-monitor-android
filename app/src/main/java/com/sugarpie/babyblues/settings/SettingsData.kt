package com.sugarpie.babyblues.settings

import com.sugarpie.babyblues.reminders.logic.ReminderUtils

class SettingsData(val map: Map<String, Any>)  {

    /**
     * Returns false if all settings match the default values, true otherwise
     */
    fun reminderTimestampHasBeenSet(): Boolean {
        val enabled = map[ReminderUtils.KEY_REMINDER_ENABLED]
        val dayOfWeek = map[ReminderUtils.KEY_REMINDER_DAYOFWEEK]
        val time = map[ReminderUtils.KEY_REMINDER_TIME]

        return !(enabled == ReminderUtils.DEFAULT_REMINDER_ENABLED &&
                dayOfWeek == ReminderUtils.DEFAULT_REMINDER_DAYOFWEEK &&
                time == ReminderUtils.DEFAULT_REMINDER_TIME)
    }
}
