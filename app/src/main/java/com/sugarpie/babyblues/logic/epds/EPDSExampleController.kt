package com.sugarpie.babyblues.logic.epds

import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import com.sugarpie.babyblues.Log
import com.sugarpie.babyblues.R

class EPDSExampleController {

    fun getClickListener(rb: RadioButton, rg: RadioGroup): View.OnClickListener {
        return View.OnClickListener {
            Log.d(TAG, "clicked ${rb.id}")

            val alwaysSelected = rg.findViewById<RadioButton>(R.id.epds_response1)
            alwaysSelected.isChecked = true
        }
    }

    companion object {
        const val TAG = "EPDSExampleController"
    }
}