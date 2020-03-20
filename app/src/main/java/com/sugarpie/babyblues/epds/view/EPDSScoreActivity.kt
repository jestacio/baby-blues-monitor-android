package com.sugarpie.babyblues.epds.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sugarpie.babyblues.Log
import com.sugarpie.babyblues.R
import com.sugarpie.babyblues.epds.logic.EPDSScoreActivityController
import kotlinx.android.synthetic.main.activity_edpsscore.*

class EPDSScoreActivity : AppCompatActivity() {

    private val controller = EPDSScoreActivityController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")

        setContentView(R.layout.activity_edpsscore)

        button_share.setOnClickListener(controller.getShareClickListener())
    }

    companion object {
        const val TAG = "EPDSScoreActivity"
    }
}