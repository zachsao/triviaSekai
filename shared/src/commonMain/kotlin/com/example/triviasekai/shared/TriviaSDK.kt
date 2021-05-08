package com.example.triviasekai.shared

import android.text.Html
import com.example.triviasekai.shared.model.Difficulty
import com.example.triviasekai.shared.model.Response
import com.example.triviasekai.shared.model.TriviaCategories
import com.example.triviasekai.shared.network.TriviaApi

class TriviaSDK {
    private val api = TriviaApi()

    @Throws(Exception::class)
    suspend fun getQuestions(categoryId: Int, difficulty: String): Response {
        val response = api.getQuestions(categoryId, difficulty)
        val decodedResults = response.results.map { result ->
            result.copy(
                question = Html.fromHtml(result.question, Html.FROM_HTML_MODE_LEGACY).toString(),
                incorrectAnswers = result.incorrectAnswers.map {
                    Html.fromHtml(it, Html.FROM_HTML_MODE_LEGACY).toString()
                },
                correctAnswer = Html.fromHtml(result.correctAnswer, Html.FROM_HTML_MODE_LEGACY)
                    .toString()
            )
        }

        return response.copy(results = decodedResults)
    }

    @Throws(Exception::class)
    suspend fun getCategories(): TriviaCategories {
        return api.getCategories()
    }
}