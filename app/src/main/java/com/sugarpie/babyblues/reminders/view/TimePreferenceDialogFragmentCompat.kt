package com.sugarpie.babyblues.reminders.view

import android.content.Context
import android.view.View
import android.widget.TimePicker
import androidx.preference.DialogPreference
import androidx.preference.Preference
import androidx.preference.PreferenceDialogFragmentCompat

class TimePreferenceDialogFragmentCompat : PreferenceDialogFragmentCompat(),
    DialogPreference.TargetFragment {
    private var timePicker: TimePicker? = null
    override fun onCreateDialogView(context: Context): View {
        timePicker = TimePicker(context)
        return timePicker as TimePicker
    }

    @Suppress("DEPRECATION")
    override fun onBindDialogView(v: View) {
        super.onBindDialogView(v)
        timePicker!!.setIs24HourView(true)
        val pref = preference as TimePreference
        timePicker!!.currentHour = pref.hour
        timePicker!!.currentMinute = pref.minute
        timePicker!!.hour = pref.hour
        timePicker!!.minute = pref.minute
    }

    @Suppress("DEPRECATION")
    override fun onDialogClosed(positiveResult: Boolean) {
        if (positiveResult) {
            val pref = preference as TimePreference
            pref.hour = timePicker!!.currentHour
            pref.minute = timePicker!!.currentMinute
            pref.hour = timePicker!!.hour
            pref.minute = timePicker!!.minute
            val value: String = TimePreference.timeToString(pref.hour, pref.minute)
            if (pref.callChangeListener(value)) pref.persistStringValue(value)
        }
    }

    override fun <T : Preference?> findPreference(key: CharSequence): T? {

        // unchecked cast needed since we can't modify the library code to call this with the
        // correct type parameter, and we can't modify the function signature
        @Suppress("UNCHECKED_CAST")
        return this.preference as T
    }
}