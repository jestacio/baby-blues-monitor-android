package com.sugarpie.babyblues.ui.epds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import com.sugarpie.babyblues.R
import com.sugarpie.babyblues.logic.epds.EPDSExampleController

class EPDSExampleResponsePageFragment : Fragment() {

    private val controller = EPDSExampleController()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_edps_example_response_page, container, false)
        val viewGroup = view.findViewById<ViewGroup>(R.id.content)
        viewGroup.layoutTransition?.setAnimateParentHierarchy(false)

        val rg = view.findViewById<RadioGroup>(R.id.epds_radiogroup)
        val rb0 = view.findViewById<RadioButton>(R.id.epds_response0)
        val rb1 = view.findViewById<RadioButton>(R.id.epds_response1)
        val rb2 = view.findViewById<RadioButton>(R.id.epds_response2)
        val rb3 = view.findViewById<RadioButton>(R.id.epds_response3)

        rb0.setOnClickListener(controller.getClickListener(rb0, rg))
        rb1.setOnClickListener(controller.getClickListener(rb1, rg))
        rb2.setOnClickListener(controller.getClickListener(rb2, rg))
        rb3.setOnClickListener(controller.getClickListener(rb3, rg))

        rb0.isEnabled = false
        rb2.isEnabled = false
        rb3.isEnabled = false

        return view
    }
}