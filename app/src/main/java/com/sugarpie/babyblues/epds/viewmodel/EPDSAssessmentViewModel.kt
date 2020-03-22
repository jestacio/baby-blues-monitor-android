package com.sugarpie.babyblues.epds.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sugarpie.babyblues.Log
import com.sugarpie.babyblues.Utils
import com.sugarpie.babyblues.epds.data.EPDSQuestionData
import com.sugarpie.babyblues.epds.logic.EPDSResourceLoader
import com.sugarpie.babyblues.epds.data.EPDSResponseData
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.text.StringBuilder

class EPDSAssessmentViewModel : ViewModel() {

    private var timestamp: String = ""
    private var list: List<MutableLiveData<EPDSQuestionData>>? = null
    private var score: MutableLiveData<Int> = MutableLiveData<Int>()

    fun saveToFile(ctx: Context) {
        val resultsDir = Utils.getEPDSResultsDir(ctx)
        val newFilename = resultsDir.absolutePath + "/" + timestamp

        Log.d(TAG, "newFilename=$newFilename")

        val newFile = File(newFilename)

        if (!newFile.exists()) {
            newFile.createNewFile()
        }

        newFile.writeText(this.toJsonStr())
    }

    private fun toJsonStr(): String {
        val jsonObject = JSONObject()
        jsonObject.put(KEY_VERSION, VALUE_VERSION)
        jsonObject.put(KEY_TIMESTAMP, timestamp)
        jsonObject.put(KEY_SCORE, score.value)
        jsonObject.put(KEY_QUESTIONS, questionsAsJsonArray())
        val jsonStr = jsonObject.toString(2)

        Log.d(TAG, "jsonStr: $jsonStr")

        return jsonStr
    }

    private fun setTimestamp(ts: String) {
        timestamp = ts
        Log.d(TAG, "setTimestamp $ts")
    }

    private fun questionsAsJsonArray(): JSONArray {
        val jsonArray = JSONArray()

        list?.forEach {
            val questionData = it.value
            val jsonObj = questionData?.toJsonObj()

            jsonArray.put(jsonObj)
        }

        return jsonArray
    }

    /**
     * Reloads from the string array resource file
     */
    fun reset(ctx: Context) {
        val loader = EPDSResourceLoader()
        list = loader.loadQuestionsAndResponses(ctx)
        score.apply { value = -1 }

        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-EEE", Locale.ENGLISH)
        val prettyTimestamp = simpleDateFormat.format(GregorianCalendar().time)
        setTimestamp(prettyTimestamp)
    }

    fun getQuestionData(idx: Int): LiveData<EPDSQuestionData> {
        return when (list) {
            null -> MutableLiveData<EPDSQuestionData>().apply {
                value = EPDSQuestionData(
                    0, "", "", listOf(
                        EPDSResponseData(0, "", 0),
                        EPDSResponseData(0, "", 0),
                        EPDSResponseData(0, "", 0),
                        EPDSResponseData(0, "", 0)
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
                    0, "", "", listOf(
                        EPDSResponseData(0, "", 0),
                        EPDSResponseData(0, "", 0),
                        EPDSResponseData(0, "", 0),
                        EPDSResponseData(0, "", 0)
                    ),
                    -1
                )
            }
            else -> list!![idx]
        }
    }

    fun updateResponse(questionIdx: Int, selectedIdx: Int) {
        val mutableLiveData = getMutableQuestionData(questionIdx)
        mutableLiveData.apply { value?.selectedResponseId = selectedIdx }

        if (list == null) {
            Log.w(TAG, "updateResponse(): list is null")
            return
        }

        var newScore = 0
        list?.forEach {
            Log.d(TAG, "updateResponse(): $questionIdx selectedResponse=${it.value?.selectedResponseId!!}")
            if (it.value?.selectedResponseId!! < 0) {
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
                newScore += it.value?.responses?.get(it.value!!.selectedResponseId)?.score!!
            }

            Log.d(TAG, "updateResponse(): calculated newScore=$newScore")

            value = newScore
        }
    }

    fun getScore(): LiveData<Int> {
        return score
    }

    fun toText(): String {
        val stringBuilder = StringBuilder()

        stringBuilder.append("Edinburgh Postnatal Depression Scale\n")
        stringBuilder.append("Date: ")
        stringBuilder.append(timestamp)
        stringBuilder.append("\n\n")

        stringBuilder.append("score: ")
        stringBuilder.append(score.value)
        stringBuilder.append("\n\n")

        list?.forEach {
            val liveData = it
            val questionData = liveData.value
            val text = questionData?.toText()

            stringBuilder.append(text)
            stringBuilder.append("\n\n")
        }

        return stringBuilder.toString()
    }

    companion object {
        const val TAG = "EPDSAssessmentViewModel"

        const val VALUE_VERSION = "1.0"
        const val KEY_VERSION = "version"
        const val KEY_TIMESTAMP = "timestamp"
        const val KEY_QUESTIONS = "questions"
        const val KEY_SCORE = "score"

        fun fromJsonStr(jsonStr: String): EPDSAssessmentViewModel {
            return EPDSAssessmentViewModel()
        }
    }
}
