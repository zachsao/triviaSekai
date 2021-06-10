package com.dev.zachsao.triviasekai.shared

import android.text.Html
import com.dev.zachsao.triviasekai.shared.cache.Database
import com.dev.zachsao.triviasekai.shared.cache.DatabaseDriverFactory
import com.dev.zachsao.triviasekai.shared.model.HighScore
import com.dev.zachsao.triviasekai.shared.model.Response
import com.dev.zachsao.triviasekai.shared.model.TriviaCategories
import com.dev.zachsao.triviasekai.shared.network.TriviaApi

class TriviaSDK(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = Database(databaseDriverFactory)
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

    fun getHighScores(): List<HighScore> = database.getHighScores()

    fun addHighScore(highScore: HighScore) {
        database.insertHighScore(highScore)
    }

    fun updateHighScore(highScore: HighScore) {
        database.updateHighScore(highScore)
    }
}