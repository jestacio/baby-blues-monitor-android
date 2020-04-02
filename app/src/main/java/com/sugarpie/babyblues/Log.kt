package com.sugarpie.babyblues

import android.util.Log

class Log {

    companion object {
        fun v(tag: String?, msg: String?) {
            if (BuildConfig.DEBUG) {
                Log.v(tag, msg)
            }
        }

        fun d(tag: String?, msg: String?) {
            if (BuildConfig.DEBUG) {
                Log.d(tag, msg)
            }
        }

        fun w(tag: String?, msg: String?) {
            Log.w(tag, msg)
        }

        fun e(tag: String?, msg: String?) {
            Log.e(tag, msg)
        }
    }
}