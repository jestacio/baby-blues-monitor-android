package com.sugarpie.babyblues.epds.data

/**
 * Data class for holding contents of each question of the Edinburgh Depression Scale questionnaire
 */
data class EPDSQuestionData(
    val question: String,
    val responses: List<EPDSResponseData>,
    var selectedResponse: Int) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EPDSQuestionData

        if (question != other.question) return false
        if (responses != other.responses) return false
        if (selectedResponse != other.selectedResponse) return false

        return true
    }

    override fun hashCode(): Int {
        var result = question.hashCode()
        result = 31 * result + responses.hashCode()
        result = 31 * result + selectedResponse
        return result
    }
}
