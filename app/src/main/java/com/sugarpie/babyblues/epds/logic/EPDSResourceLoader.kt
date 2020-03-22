package com.sugarpie.babyblues.epds.logic

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.sugarpie.babyblues.Log
import com.sugarpie.babyblues.R
import com.sugarpie.babyblues.epds.data.EPDSResponseData
import com.sugarpie.babyblues.epds.data.EPDSQuestionData
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
            responses = question.getJSONArray(EPDSQuestionData.KEY_RESPONSES)

            responseList = mutableListOf()
            for(i in 0 until responses.length()) {
                response = responses.getJSONObject(i)
                responseList.add(
                    EPDSResponseData(
                        response.getInt(EPDSResponseData.KEY_ID),
                        response.getString(EPDSResponseData.KEY_TEXT),
                        response.getInt(EPDSResponseData.KEY_SCORE)
                    )
                )
            }

            questionList.add(MutableLiveData<EPDSQuestionData>().apply {
                value = EPDSQuestionData(
                    question.getInt(EPDSQuestionData.KEY_ID),
                    question.getString(EPDSQuestionData.KEY_VERSION),
                    question.getString(EPDSQuestionData.KEY_QUESTION),
                    responseList,
                    -1
                )
            })
        }

        return questionList
    }

    companion object {
        const val TAG = "EPDSResourceLoader"
    }
}
