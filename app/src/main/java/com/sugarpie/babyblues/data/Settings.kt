package com.sugarpie.babyblues.data

import java.util.*

class Settings(
    val map: Map<String, Any>)  {

    fun reminderTimestampHasBeenSet(): Boolean {
        val timestamp = map.get(KEY_REMINDERTIMESTAMP) as Calendar
        return !(timestamp.get(Calendar.DAY_OF_MONTH) == 23 &&
                timestamp.get(Calendar.MONTH) == 10 &&
                timestamp.get(Calendar.YEAR) == 1990)
    }

    companion object {
        const val KEY_VERSION = "version"
        const val KEY_REMINDERENABLED = "reminderEnabled"
        const val KEY_REMINDERTIMESTAMP = "reminderTimestamp"
    }
}
