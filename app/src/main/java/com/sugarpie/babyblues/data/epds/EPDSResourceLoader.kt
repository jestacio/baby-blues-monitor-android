package com.sugarpie.babyblues.data.epds

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.sugarpie.babyblues.Log
import com.sugarpie.babyblues.R
import org.json.JSONArray
import org.json.JSONObject

class EPDSResourceLoader {
    fun loadQuestionsAndResponses(ctx: Context): List<MutableLiveData<EPDSQuestionData>> {
        val res = ctx.resources
        val rawArray = res.getStringArray(R.array.epds_questions_and_responses_array)
        val questionList = mutableListOf<MutableLiveData<EPDSQuestionData>>()
        var responseList: MutableList<EPDSResponseData>
        var question: JSONObject
        var response: JSONObject
        var responses: JSONArray
        rawArray.iterator().forEach {
            Log.d(TAG, "it is $it")
            question = JSONObject(it)
            responses = question.getJSONArray(KEY_RESPONSES)

            responseList = mutableListOf()
            for(i in 0 until responses.length()) {
                response = responses.getJSONObject(i)
                responseList.add(EPDSResponseData(
                    response.getString(KEY_TEXT), response.getInt(KEY_SCORE)))
            }

            questionList.add(MutableLiveData<EPDSQuestionData>().apply {
                value = EPDSQuestionData(
                    question.getString(KEY_QUESTION),
                    responseList,
                    -1)
            })
        }

        return questionList
    }

    companion object {
        const val TAG = "EPDSResourceLoader"
        const val KEY_QUESTION = "question"
        const val KEY_RESPONSES = "responses"
        const val KEY_TEXT = "text"
        const val KEY_SCORE = "score"
    }
}
