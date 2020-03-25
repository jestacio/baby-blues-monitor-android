package com.sugarpie.babyblues.history.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sugarpie.babyblues.Log
import com.sugarpie.babyblues.Utils

class HistoryViewModel : ViewModel() {

    private val _filenames = MutableLiveData<List<String>>().apply {
        value = listOf("")
    }

    val filenames: LiveData<List<String>> = _filenames

    fun scanFiles(ctx: Context) {
        val resultsDir = Utils.getEPDSResultsDir(ctx)
        val fileList = resultsDir.list().toList()
        _filenames.apply {
            value = fileList
        }

        Log.d(TAG, "scanFiles() list size=${fileList.size}")
    }

    companion object {
        const val TAG = "HistoryViewModel"
    }
}