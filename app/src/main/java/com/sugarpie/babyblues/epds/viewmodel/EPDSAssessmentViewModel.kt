package com.sugarpie.babyblues.epds.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sugarpie.babyblues.Log
import com.sugarpie.babyblues.epds.data.EPDSQuestionData
import com.sugarpie.babyblues.epds.logic.EPDSResourceLoader
import com.sugarpie.babyblues.epds.data.EPDSResponseData

class EPDSAssessmentViewModel : ViewModel() {

    private var list: List<MutableLiveData<EPDSQuestionData>>? = null
    private var score: MutableLiveData<Int> = MutableLiveData<Int>()

    /**
     * Reloads from the string array resource file
     */
    fun reset(ctx: Context) {
        val loader = EPDSResourceLoader()
        list = loader.loadQuestionsAndResponses(ctx)
        score.apply { value = -1 }
    }

    fun getQuestionData(idx: Int): LiveData<EPDSQuestionData> {
        return when (list) {
            null -> MutableLiveData<EPDSQuestionData>().apply {
                value = EPDSQuestionData(
                    "", listOf(
                        EPDSResponseData("", 0),
                        EPDSResponseData("", 0),
                        EPDSResponseData("", 0),
                        EPDSResponseData("", 0)
                    ),
                    -1
                )
            }
            else -> list!![idx]
        }
    }

    private fun getMutableQuestionData(idx: Int): MutableLiveData<EPDSQuestionData> {
        return when (list) {
            null -> MutableLiveData<EPDSQuestionData>().apply {
                value = EPDSQuestionData(
                    "", listOf(
                        EPDSResponseData("", 0),
                        EPDSResponseData("", 0),
                        EPDSResponseData("", 0),
                        EPDSResponseData("", 0)
                    ),
                    -1
                )
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

        var newScore = 0
        list?.forEach {
            Log.d(TAG, "updateResponse(): $questionIdx selectedResponse=${it.value?.selectedResponse!!}")
            if (it.value?.selectedResponse!! < 0) {
                newScore = -1
                return@forEach
            }
        }

        Log.d(TAG, "updateResponse(): initial newScore=$newScore")

        score.apply {
            if (list == null || newScore == -1) {
                value = -1
                return@apply
            }

            list!!.forEach {
                // wow Kotlin is so idiomatic... /s
                // I must be doing something wrong cause this is some banging code...
                newScore += it.value?.responses?.get(it.value!!.selectedResponse)?.score!!
            }

            Log.d(TAG, "updateResponse(): calculated newScore=$newScore")

            value = newScore
        }
    }

    fun getScore(): LiveData<Int> {
        return score
    }

    companion object {
        const val TAG = "EPDSAssessmentViewModel"
    }
}
