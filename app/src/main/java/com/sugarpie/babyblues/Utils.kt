package com.sugarpie.babyblues

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.View
import java.io.File

class Utils {
    companion object {
        const val TAG = "Utils"
        const val EPDS_RESULTS_DIR_NAME = "epds_results"

        fun getEPDSResultsDir(ctx: Context): File {
            val appFilesDir = ctx.filesDir
            val epdsResultsDir = appFilesDir.absolutePath + "/" + EPDS_RESULTS_DIR_NAME
            return File(epdsResultsDir)
        }

        fun getActivityFromView(it: View): Activity? {
            var activity: Activity? = null
            var context = it.context
            while (context is ContextWrapper) {
                if (context is Activity) {
                    activity = context
                }
                context = context.baseContext
            }

            Log.d(TAG, "Got activity $activity from view $it")

            return activity
        }
    }
}
