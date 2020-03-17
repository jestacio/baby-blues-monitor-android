package com.sugarpie.babyblues.data.epds

/**
 * Data class for holding contents of each question of the Edinburgh Depression Scale questionnaire
 */
data class EPDSQuestionData(
    val question: String,
    val response0: String,
    val response1: String,
    val response2: String,
    val response3: String,
    val selectedResponse: Int)
