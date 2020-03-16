package com.sugarpie.babyblues.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sugarpie.babyblues.data.Settings
import java.util.*

class HomeViewModel : ViewModel() {

    private val _settings = MutableLiveData<Settings>().apply {
        val defaultTimestamp = GregorianCalendar(0, 0, 0)
        defaultTimestamp.set(Calendar.YEAR, Settings.REMINDER_YEAR_DEFAULT)
        defaultTimestamp.set(Calendar.MONTH, Settings.REMINDER_MONTH_DEFAULT)
        defaultTimestamp.set(Calendar.DAY_OF_MONTH, Settings.REMINDER_DAY_OF_MONTH_DEFAULT)
        value = Settings(mapOf(
            Settings.KEY_VERSION to Settings.VERSION,
            Settings.KEY_REMINDERENABLED to Settings.REMINDER_ENABLED_DEFAULT,
            Settings.KEY_REMINDERTIMESTAMP to defaultTimestamp)) }

    val settings: LiveData<Settings> = _settings
}