package com.sugarpie.babyblues.epds.view

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.appcompat.app.AppCompatActivity
import com.sugarpie.babyblues.Log
import com.sugarpie.babyblues.R
import com.sugarpie.babyblues.SharedExtras
import com.sugarpie.babyblues.epds.logic.EPDSResultActivityController
import kotlinx.android.synthetic.main.activity_edpsresult.*
import kotlinx.android.synthetic.main.activity_edpsscore.*
import kotlinx.android.synthetic.main.activity_edpsscore.button_finish
import kotlinx.android.synthetic.main.activity_edpsscore.button_share
import kotlinx.android.synthetic.main.activity_edpsscore.text_result

class EPDSResultActivity : AppCompatActivity() {

    private val controller = EPDSResultActivityController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")

        setContentView(R.layout.activity_edpsresult)

        val epdsText = intent.getStringExtra(SharedExtras.EXTRA_COMPLETED_EPDS_TEXT)
        val score = intent.getIntExtra(SharedExtras.EXTRA_SCORE, -1)

        controller.updateViews(score, epdsText, button_finish, text_result)
        text_result.movementMethod = ScrollingMovementMethod()

        button_share.setOnClickListener(controller.getShareClickListener(epdsText))
        button_finish.setOnClickListener(controller.getFinishClickListener(score))
    }

    companion object {
        const val TAG = "EPDSScoreActivity"
    }
}