package com.sugarpie.babyblues.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sugarpie.babyblues.R
import com.sugarpie.babyblues.home.logic.HomeFragmentController
import com.sugarpie.babyblues.settings.SettingsViewModel

class HomeFragment : Fragment() {

    private lateinit var viewModel: SettingsViewModel
    private val controller = HomeFragmentController()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(SettingsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        val btnCheckMentalHealth: Button = root.findViewById(R.id.button_checkhealth)
        if (context != null) {
            viewModel.handleSettingsUpdated(context!!) // needed since we can't load without context
            viewModel.settings.observe(this, Observer {
                if (it != null) {
                    controller.updateViews(context!!, it, textView)
                }
            })
        }

        btnCheckMentalHealth.setOnClickListener(controller.getClickListener(btnCheckMentalHealth))
        return root
    }
}
