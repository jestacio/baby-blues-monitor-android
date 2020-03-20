package com.sugarpie.babyblues.epds.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.sugarpie.babyblues.R
import com.sugarpie.babyblues.epds.logic.EPDSSubmitPageFragmentController
import com.sugarpie.babyblues.epds.viewmodel.EPDSAssessmentViewModel

class EPDSSubmitPageFragment(private val viewModel: EPDSAssessmentViewModel) : Fragment() {

    private val controller =
        EPDSSubmitPageFragmentController()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_edps_submit_page, container, false)
        val viewGroup = view.findViewById<ViewGroup>(R.id.content)
        viewGroup.layoutTransition?.setAnimateParentHierarchy(false)
        val textInstructions = view.findViewById<TextView>(R.id.text_instructions)
        val buttonFinish = view.findViewById<Button>(R.id.button_finish)

        viewModel.getScore().observe(this, Observer {
            if (it != null) {
                controller.updateViews(it, textInstructions, buttonFinish)
            }
        })

        buttonFinish.setOnClickListener(controller.getFinishOnClickListener(viewModel))

        return view
    }
}