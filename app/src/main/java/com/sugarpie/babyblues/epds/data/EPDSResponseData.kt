package com.sugarpie.babyblues.epds.data

data class EPDSResponseData(
    val id: Int,
    val text: String,
    val score: Int) {

    companion object {
        const val KEY_ID = "id"
        const val KEY_TEXT = "text"
        const val KEY_SCORE = "score"
    }
}