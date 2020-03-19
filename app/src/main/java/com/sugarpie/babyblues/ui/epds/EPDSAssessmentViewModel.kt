package com.sugarpie.babyblues.ui.epds

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sugarpie.babyblues.Log
import com.sugarpie.babyblues.data.epds.EPDSQuestionData
import com.sugarpie.babyblues.data.epds.EPDSResourceLoader

class EPDSAssessmentViewModel : ViewModel() {

    private var list: List<MutableLiveData<EPDSQuestionData>>? = null
    private var completedState: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

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

    private fun getMutableQuestionData(idx: Int): MutableLiveData<EPDSQuestionData> {
        return when (list) {
            null -> MutableLiveData<EPDSQuestionData>().apply {
                value = EPDSQuestionData("", "", "", "", "", -1)
            }
            else -> list!![idx]
        }
    }

    fun updateResponse(questionIdx: Int, selectedIdx: Int) {
        val mutableLiveData = getMutableQuestionData(questionIdx)
        mutableLiveData.apply { value?.selectedResponse = selectedIdx }

        if (list == null) {
            Log.w(TAG, "updateResponse(): list is null")
            return
        }

        var completed = true
        list?.forEach {
            Log.d(TAG, "updateResponse(): $questionIdx selectedResponse=${it.value?.selectedResponse!!}")
            if (it.value?.selectedResponse!! < 0) {
                completed = false
                return@forEach
            }
        }

        Log.d(TAG, "updateResponse(): completed? $completed")

        completedState.apply {
            value = completed
        }
    }

    fun getCompletedState(): LiveData<Boolean> {
        return completedState
    }

    companion object {
        const val TAG = "EPDSAssessmentViewModel"
    }
}
