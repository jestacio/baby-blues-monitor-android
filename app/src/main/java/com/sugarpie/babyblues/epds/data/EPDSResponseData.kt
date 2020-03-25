package com.sugarpie.babyblues.epds.data

data class EPDSResponseData(
    val id: Int,
    val text: String,
    val score: Int) {

    override fun toString(): String {
        val sb = StringBuilder()

        sb.append("Response - ")
        sb.append(text)
        sb.append(" ($score)")

        return sb.toString()
    }

    companion object {
        const val KEY_ID = "id"
        const val KEY_TEXT = "text"
        const val KEY_SCORE = "score"
    }
}