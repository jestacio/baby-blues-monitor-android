package com.sugarpie.babyblues.data

import java.util.*

class Settings(
    val map: Map<String, Any>)  {

    fun reminderTimestampHasBeenSet(): Boolean {
        val timestamp = map.get(KEY_REMINDERTIMESTAMP) as Calendar
        return !(timestamp.get(Calendar.DAY_OF_MONTH) == REMINDER_DAY_OF_MONTH_DEFAULT &&
                timestamp.get(Calendar.MONTH) == REMINDER_MONTH_DEFAULT &&
                timestamp.get(Calendar.YEAR) == REMINDER_YEAR_DEFAULT)
    }

    companion object {
        const val VERSION = "1.0"
        const val KEY_VERSION = "version"
        const val KEY_REMINDERENABLED = "reminderEnabled"
        const val KEY_REMINDERTIMESTAMP = "reminderTimestamp"
        const val REMINDER_DAY_OF_MONTH_DEFAULT = 23
        const val REMINDER_MONTH_DEFAULT = 10
        const val REMINDER_YEAR_DEFAULT = 1990
        const val REMINDER_ENABLED_DEFAULT = false
    }
}
