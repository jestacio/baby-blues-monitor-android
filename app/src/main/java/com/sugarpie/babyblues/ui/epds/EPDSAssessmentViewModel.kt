package com.sugarpie.babyblues.ui.epds

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sugarpie.babyblues.data.epds.EPDSQuestionData
import com.sugarpie.babyblues.data.epds.EPDSResourceLoader

class EPDSAssessmentViewModel : ViewModel() {

    private var list: List<MutableLiveData<EPDSQuestionData>>? = null

    /**
     * Reloads from the string array resource file
     */
    fun reset(ctx: Context) {
        val loader = EPDSResourceLoader()
        list = loader.loadQuestionsAndResponses(ctx)
    }

    fun getQuestionData(idx: Int): LiveData<EPDSQuestionData> {
        return when (list) {
            null -> MutableLiveData<EPDSQuestionData>().apply {
                value = EPDSQuestionData("", "", "", "", "", -1)
            }
            else -> list!![idx]
        }
    }
}