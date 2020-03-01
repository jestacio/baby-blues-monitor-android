package com.sugarpie.babyblues.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sugarpie.babyblues.R
import com.sugarpie.babyblues.data.Settings
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.settings.observe(this, Observer {
            val settingsMap = it.map
            val reminderTimestamp = settingsMap.get(Settings.KEY_REMINDERTIMESTAMP) as GregorianCalendar
            textView.text = if (it.reminderTimestampHasBeenSet())
                "The next time we'll check on your mental health will be ${reminderTimestamp.get(Calendar.YEAR)} ${reminderTimestamp.get(Calendar.MONTH)} ${reminderTimestamp.get(Calendar.DAY_OF_MONTH)}"
            else
                "Go to Settings to set up your reminder."
        })
        return root
    }
}