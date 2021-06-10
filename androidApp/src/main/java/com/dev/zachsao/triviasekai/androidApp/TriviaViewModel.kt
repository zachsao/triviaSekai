package com.dev.zachsao.triviasekai.androidApp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.zachsao.triviasekai.androidApp.categories.CategoryViewItem
import com.dev.zachsao.triviasekai.androidApp.ui.TriviaColors
import com.dev.zachsao.triviasekai.androidApp.ui.TriviaIcons
import com.dev.zachsao.triviasekai.shared.TriviaSDK
import com.dev.zachsao.triviasekai.shared.model.Category
import com.dev.zachsao.triviasekai.shared.model.Difficulty
import com.dev.zachsao.triviasekai.shared.model.HighScore
import com.dev.zachsao.triviasekai.shared.model.TriviaResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TriviaViewModel @Inject constructor(private val triviaSDK: TriviaSDK) : ViewModel() {

    private val categoriesSharedFlow = MutableSharedFlow<List<CategoryViewItem>>(replay = 1)
    private val currentQuestionSharedFlow = MutableSharedFlow<Pair<TriviaResult, Int>>()

    private var questions: List<TriviaResult>? = null
    private var currentCategory: Int? = null
    private var currentDifficulty: Difficulty = Difficulty.Easy
    private var score = 0

    fun categoriesSharedFlow(): SharedFlow<List<CategoryViewItem>> =
        categoriesSharedFlow.asSharedFlow()

    fun currentQuestionSharedFlow(): SharedFlow<Pair<TriviaResult, Int>> =
        currentQuestionSharedFlow.asSharedFlow()

    fun getCategories() {
        viewModelScope.launch {
            val categories = triviaSDK.getCategories().categories.map { it.toViewItem() }
            categoriesSharedFlow.emit(categories)
        }
    }

    fun getQuestions(categoryId: Int) {
        viewModelScope.launch {
            score = 0
            currentCategory = categoryId
            val results =
                triviaSDK.getQuestions(
                    categoryId,
                    currentDifficulty.name.toLowerCase(Locale.ROOT)
                ).results
            questions = results
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
                    updateHighScores()
                }
            }
        }
    }

    fun score() = Pair(score, questions?.size ?: error("questions is empty"))

    fun startOver() {
        currentCategory?.let { getQuestions(it) }
    }

    fun highScores() = triviaSDK.getHighScores()

    private fun updateHighScores() {
        val highScores = triviaSDK.getHighScores().toMutableList()
        val newHighScore = HighScore(questions?.first()?.category ?: error("category is null"), score, currentDifficulty)
        if (
            highScores.none { it.category == newHighScore.category && it.difficulty == newHighScore.difficulty }
        ) {
            triviaSDK.addHighScore(newHighScore)
        } else if (
            highScores.any { it.category == newHighScore.category && it.difficulty == newHighScore.difficulty && it.score < score}
        ) {
            triviaSDK.updateHighScore(newHighScore)
        }
    }

    private fun Category.toViewItem(): CategoryViewItem {
        return CategoryViewItem(
            id,
            name,
            icon = TriviaIcons.values().find { it.id == id } ?: TriviaIcons.Knowledge,
            TriviaColors.values().find { it.id == id } ?: TriviaColors.values().random()
        )
    }

    fun selectDifficulty(difficulty: Difficulty) {
        currentDifficulty = difficulty
    }
}