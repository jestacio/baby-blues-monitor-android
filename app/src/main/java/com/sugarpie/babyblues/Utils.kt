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

        fun prettyTimestamp(timestamp: String): String {
            // saved as:
            // [0]<YEAR>-
            // [1]<MONTH>-
            // [2]<DAY OF MONTH>-
            // [3]<24 HOUR>-
            // [4]<MIN>-
            // [5]<SEC>-
            // [6]<DAY OF WEEK>

            val tokens = timestamp.split("-")

            val stringBuilder = StringBuilder()
            stringBuilder.append(tokens[6])
            stringBuilder.append(" ")
            stringBuilder.append(tokens[1])
            stringBuilder.append("/")
            stringBuilder.append(tokens[2])
            stringBuilder.append("/")
            stringBuilder.append(tokens[0])
            stringBuilder.append(" ")
            stringBuilder.append(tokens[3])
            stringBuilder.append(":")
            stringBuilder.append(tokens[4])
            stringBuilder.append(":")
            stringBuilder.append(tokens[5])

            return stringBuilder.toString()
        }

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
