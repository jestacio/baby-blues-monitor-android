package com.sugarpie.babyblues.epds.data

import org.json.JSONArray
import org.json.JSONObject

/**
 * Data class for holding contents of each question of the Edinburgh Depression Scale questionnaire
 */
data class EPDSQuestionData(
    val id: Int,
    val version: String,
    val question: String,
    val responses: List<EPDSResponseData>,
    var selectedResponseId: Int) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EPDSQuestionData

        if (question != other.question) return false
        if (responses != other.responses) return false
        if (selectedResponseId != other.selectedResponseId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = question.hashCode()
        result = 31 * result + responses.hashCode()
        result = 31 * result + selectedResponseId
        return result
    }

    fun toText(): String {
        val stringBuilder = StringBuilder()

        if (selectedResponseId >= 0) {
            stringBuilder.append(question)
            stringBuilder.append(": ")
            stringBuilder.append(responses[selectedResponseId])
        }

        return stringBuilder.toString()
    }

    fun toJsonObj(): JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put(KEY_VERSION, VALUE_VERSION)
        jsonObject.put(KEY_ID, id)
        jsonObject.put(KEY_QUESTION, question)
        jsonObject.put(KEY_SELECTED_RESPONSE_ID, selectedResponseId)
        jsonObject.put(KEY_RESPONSES, toJsonArray(responses))

        return jsonObject
    }

    companion object {
        const val VALUE_VERSION = "1.0"
        const val KEY_VERSION = "version"
        const val KEY_ID = "id"
        const val KEY_QUESTION = "question"
        const val KEY_RESPONSES = "responses"
        const val KEY_SELECTED_RESPONSE_ID = "selected_response_id"

        fun toJsonArray(responses: List<EPDSResponseData>): JSONArray {
            val jsonArray = JSONArray()

            responses.forEach {
                jsonArray.put(JSONObject().apply {
                    put(EPDSResponseData.KEY_ID, it.id)
                    put(EPDSResponseData.KEY_TEXT, it.text)
                    put(EPDSResponseData.KEY_SCORE, it.score)
                })
            }

            return jsonArray
        }
    }
}
