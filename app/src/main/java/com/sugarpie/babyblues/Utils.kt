package com.sugarpie.babyblues

import android.app.Activity
import android.content.ContextWrapper
import android.view.View

class Utils {
    companion object {
        const val TAG = "Utils"

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
