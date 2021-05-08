package com.example.triviasekai.shared.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Response(
    @SerialName("response_code")
    val responseCode: Int,
    @SerialName("results")
    val results: List<TriviaResult>
)

@Serializable
data class TriviaResult(
    @SerialName("category")
    val category: String,
    @SerialName("question")
    val question: String,
    @SerialName("difficulty")
    val difficulty: String,
    @SerialName("correct_answer")
    val correctAnswer: String,
    @SerialName("incorrect_answers")
    val incorrectAnswers: List<String>
)

@Serializable
data class TriviaCategories(
    @SerialName("trivia_categories") val categories: List<Category>
)

@Serializable
data class Category(@SerialName("id")val id: Int, val name: String)

enum class Difficulty {
    Easy, Medium, Hard
}