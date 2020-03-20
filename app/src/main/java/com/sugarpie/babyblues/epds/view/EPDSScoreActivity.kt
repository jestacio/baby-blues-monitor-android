package com.sugarpie.babyblues.epds.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sugarpie.babyblues.Log
import com.sugarpie.babyblues.R
import com.sugarpie.babyblues.SharedExtras
import com.sugarpie.babyblues.epds.logic.EPDSScoreActivityController
import kotlinx.android.synthetic.main.activity_edpsscore.*

class EPDSScoreActivity : AppCompatActivity() {

    private val controller = EPDSScoreActivityController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")

        setContentView(R.layout.activity_edpsscore)

        val epdsText = intent.getStringExtra(SharedExtras.EXTRA_COMPLETED_EPDS_TEXT)

        button_share.setOnClickListener(controller.getShareClickListener(epdsText))
    }

    companion object {
        const val TAG = "EPDSScoreActivity"
    }
}