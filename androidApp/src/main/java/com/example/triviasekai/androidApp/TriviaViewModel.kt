package com.example.triviasekai.androidApp

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

    private val categoriesSharedFlow = MutableSharedFlow<List<Category>>()
    private val currentQuestionSharedFlow = MutableSharedFlow<Pair<TriviaResult, Int>>()
    private val triviaSDK = TriviaSDK()

    private var questions: List<TriviaResult>? = null

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
        viewModelScope.launch {
            val results = triviaSDK.getQuestions(categoryId).results
            Log.d("zsao", "${results.size} results retrieved")
            questions = results
            currentQuestionSharedFlow.emit(Pair(results.first(), 0))
        }
    }
}