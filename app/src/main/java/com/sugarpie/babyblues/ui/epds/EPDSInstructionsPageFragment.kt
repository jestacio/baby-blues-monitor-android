package com.sugarpie.babyblues.ui.epds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sugarpie.babyblues.R

class EPDSInstructionsPageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_edps_instructions_page, container, false)
        val viewGroup = view.findViewById<ViewGroup>(R.id.content)
        viewGroup.layoutTransition?.setAnimateParentHierarchy(false)

        return view
    }
}