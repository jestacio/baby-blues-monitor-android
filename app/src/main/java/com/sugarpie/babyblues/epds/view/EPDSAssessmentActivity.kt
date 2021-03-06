package com.sugarpie.babyblues.epds.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.widget.ViewPager2
import com.sugarpie.babyblues.Log
import com.sugarpie.babyblues.R
import com.sugarpie.babyblues.epds.viewmodel.EPDSAssessmentViewModel
import com.sugarpie.babyblues.epds.logic.EPDSPagerAdapter
import kotlinx.android.synthetic.main.activity_edpsassessment.*

class EPDSAssessmentActivity : AppCompatActivity() {

    private lateinit var viewModel: EPDSAssessmentViewModel

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next assessment questions.
     */
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_edpsassessment)

        toolbar.title = getString(R.string.empty)

        setSupportActionBar(toolbar)
        container.layoutTransition?.setAnimateParentHierarchy(false)

        viewModel = ViewModelProviders.of(this).get(EPDSAssessmentViewModel::class.java)
        viewModel.reset(applicationContext)

        // Instantiate a ViewPager2 and a PagerAdapter.
        viewPager = findViewById(R.id.pager)

        // The pager adapter, which provides the pages to the view pager widget.
        val pagerAdapter = EPDSPagerAdapter(
            this,
            viewModel,
            viewPager
        )
        viewPager.adapter = pagerAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.epds_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onBackPressed() {
        if (viewPager.currentItem == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed()
        } else {
            // Otherwise, select the previous step.
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items in the activity class
        // Move to separate controller class if this gets more complicated
        when (item.itemId) {
            R.id.action_next -> {
                viewPager.currentItem = viewPager.currentItem + 1
                return true
            }
            R.id.action_prev -> {
                viewPager.currentItem = viewPager.currentItem - 1
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val TAG = "EPDSAssessmentActivity"
    }
}
