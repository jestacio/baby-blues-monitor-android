package com.sugarpie.babyblues.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sugarpie.babyblues.settings.SettingsData
import java.util.*

class HomeViewModel : ViewModel() {

    private val _settings = MutableLiveData<SettingsData>().apply {
        val defaultTimestamp = GregorianCalendar(0, 0, 0)
        defaultTimestamp.set(Calendar.YEAR, SettingsData.REMINDER_YEAR_DEFAULT)
        defaultTimestamp.set(Calendar.MONTH, SettingsData.REMINDER_MONTH_DEFAULT)
        defaultTimestamp.set(Calendar.DAY_OF_MONTH, SettingsData.REMINDER_DAY_OF_MONTH_DEFAULT)
        value = SettingsData(
            mapOf(
                SettingsData.KEY_VERSION to SettingsData.VERSION,
                SettingsData.KEY_REMINDERENABLED to SettingsData.REMINDER_ENABLED_DEFAULT,
                SettingsData.KEY_REMINDERTIMESTAMP to defaultTimestamp
            )
        )
    }

    val settings: LiveData<SettingsData> = _settings
}