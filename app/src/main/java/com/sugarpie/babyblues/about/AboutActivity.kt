package com.sugarpie.babyblues.about

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sugarpie.babyblues.R
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        toolbar.title = getString(R.string.setting_about_title)

        textView.setText(R.string.setting_about_text)
    }
}