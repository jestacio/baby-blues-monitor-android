package com.sugarpie.babyblues.reminders.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.preference.DialogPreference

/**
 * Converted from https://stackoverflow.com/a/34398747/535638
 */
open class TimePreference(ctx: Context?, attrs: AttributeSet?) : DialogPreference(ctx, attrs) {
    var hour = 0
    var minute = 0

    override fun onGetDefaultValue(a: TypedArray, index: Int): Any? {
        return a.getString(index)
    }

    override fun onSetInitialValue(restoreValue: Boolean, defaultValue: Any?) {
        val value: String =
            if (restoreValue) {
                if (defaultValue == null) getPersistedString(DEFAULT_VALUE)
                else getPersistedString(defaultValue.toString())
            } else {
                defaultValue.toString()
            }

        hour = parseHour(value)
        minute = parseMinute(value)
    }

    open fun persistStringValue(value: String?) {
        persistString(value)
    }

    companion object {
        const val DEFAULT_VALUE = "12:00"

        fun parseHour(value: String): Int {
            return try {
                val time = value.split(":").toTypedArray()
                time[0].toInt()
            } catch (e: Exception) {
                0
            }
        }

        fun parseMinute(value: String): Int {
            return try {
                val time = value.split(":").toTypedArray()
                time[1].toInt()
            } catch (e: Exception) {
                0
            }
        }

        fun timeToString(h: Int, m: Int): String {
            return String.format("%02d", h) + ":" + String.format("%02d", m)
        }
    }
}