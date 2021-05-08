package com.example.triviasekai.androidApp

import android.text.Html
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.triviasekai.shared.TriviaSDK
import com.example.triviasekai.shared.model.Category
import com.example.triviasekai.shared.model.TriviaResult
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class TriviaViewModel : ViewModel() {

    private val categoriesSharedFlow = MutableSharedFlow<List<Category>>(replay = 1)
    private val currentQuestionSharedFlow = MutableSharedFlow<Pair<TriviaResult, Int>>()
    private val triviaSDK = TriviaSDK()

    private var questions: List<TriviaResult>? = null
    private var currentCategory: Int? = null
    private var score = 0

    fun categoriesSharedFlow(): SharedFlow<List<Category>> = categoriesSharedFlow.asSharedFlow()
    fun currentQuestionSharedFlow(): SharedFlow<Pair<TriviaResult, Int>> =
        currentQuestionSharedFlow.asSharedFlow()

    fun getCategories() {
        viewModelScope.launch {
            val categories = triviaSDK.getCategories().categories
            Log.d("zsao", "${categories.size} results retrieved")
            categoriesSharedFlow.emit(categories)
        }
    }

    fun getQuestions(categoryId: Int) {
        currentCategory = categoryId
        viewModelScope.launch {
            val results = triviaSDK.getQuestions(categoryId).results
            Log.d("zsao", "${results.size} results retrieved")
            questions = results.map {
                it.copy(
                    question = Html.fromHtml(it.question, Html.FROM_HTML_MODE_LEGACY).toString(),
                    incorrectAnswers = it.incorrectAnswers.map { Html.fromHtml(it, Html.FROM_HTML_MODE_LEGACY).toString() },
                    correctAnswer = Html.fromHtml(it.correctAnswer, Html.FROM_HTML_MODE_LEGACY).toString()
                )
            }
            currentQuestionSharedFlow.emit(Pair(results.first(), 0))
        }
    }

    fun nextQuestion(index: Int, isCorrect: Boolean, navigate: () -> Unit) {
        if (isCorrect) score += 1
        viewModelScope.launch {
            questions?.let {
                if (index < it.size) {
                    currentQuestionSharedFlow.emit(Pair(it[index], index))
                } else {
                    navigate()
                }
            }
        }
    }

    fun score() = Pair(score, questions?.size)

    fun startOver() {
        score = 0
        currentCategory?.let { getQuestions(it) }
    }
}