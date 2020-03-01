package com.sugarpie.babyblues.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sugarpie.babyblues.data.Settings
import java.util.*

class HomeViewModel : ViewModel() {

    private val _settings = MutableLiveData<Settings>().apply {
        val defaultTimestamp = GregorianCalendar(0, 0, 0)
        defaultTimestamp.set(Calendar.YEAR, 1990)
        defaultTimestamp.set(Calendar.MONTH, 10)
        defaultTimestamp.set(Calendar.DAY_OF_MONTH, 23)
        value = Settings(mapOf(
            Settings.KEY_VERSION to "1",
            Settings.KEY_REMINDERENABLED to false,
            Settings.KEY_REMINDERTIMESTAMP to defaultTimestamp)) }

    val settings: LiveData<Settings> = _settings
}