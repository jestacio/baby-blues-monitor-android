package com.sugarpie.babyblues.settings

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.sugarpie.babyblues.R
import com.sugarpie.babyblues.reminders.logic.ReminderUtils
import com.sugarpie.babyblues.reminders.view.TimePreference
import com.sugarpie.babyblues.reminders.view.TimePreferenceDialogFragmentCompat

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
    }

    override fun onDisplayPreferenceDialog(preference: Preference?) {
        if (preference is TimePreference) {
            val dialogFragment = TimePreferenceDialogFragmentCompat()
            val bundle = Bundle(1)
            bundle.putString("key", preference.getKey())
            dialogFragment.arguments = bundle
            dialogFragment.setTargetFragment(this, 0)
            this.fragmentManager?.let { dialogFragment.show(it,
                "androidx.preference.PreferenceFragmentCompat.DIALOG") }
        } else {
            super.onDisplayPreferenceDialog(preference)
        }
    }

    override fun onResume() {
        super.onResume()

        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener {
            sharedPrefs: SharedPreferences, key: String ->
                context?.let { ReminderUtils.setAlarm(it) }
        }
    }
}