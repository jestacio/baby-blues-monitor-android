package com.sugarpie.babyblues.epds.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sugarpie.babyblues.R
import kotlinx.android.synthetic.main.activity_edpsresources.*

class EPDSResourcesActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_edpsresources)

        button_ok.setOnClickListener {
            finish()
        }
    }
}