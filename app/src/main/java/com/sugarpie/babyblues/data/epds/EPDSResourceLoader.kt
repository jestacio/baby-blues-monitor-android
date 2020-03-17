package com.sugarpie.babyblues.data.epds

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.sugarpie.babyblues.Log
import com.sugarpie.babyblues.R
import org.json.JSONObject

class EPDSResourceLoader {
    fun loadQuestionsAndResponses(ctx: Context): List<MutableLiveData<EPDSQuestionData>> {
        val res = ctx.resources
        val rawArray = res.getStringArray(R.array.epds_questions_and_responses_array)
        val list = mutableListOf<MutableLiveData<EPDSQuestionData>>()
        var jsonObject: JSONObject
        rawArray.iterator().forEach {
            Log.d(TAG, "it is $it")
            jsonObject = JSONObject(it)
            list.add(MutableLiveData<EPDSQuestionData>().apply {
                value = EPDSQuestionData(
                    jsonObject.getString(KEY_QUESTION),
                    jsonObject.getString(KEY_RESPONSE0),
                    jsonObject.getString(KEY_RESPONSE1),
                    jsonObject.getString(KEY_RESPONSE2),
                    jsonObject.getString(KEY_RESPONSE3),
                    -1)
            })
        }

        return list
    }

    companion object {
        const val TAG = "EPDSResourceLoader"
        const val KEY_QUESTION = "question"
        const val KEY_RESPONSE0 = "response0"
        const val KEY_RESPONSE1 = "response1"
        const val KEY_RESPONSE2 = "response2"
        const val KEY_RESPONSE3 = "response3"
    }
}
