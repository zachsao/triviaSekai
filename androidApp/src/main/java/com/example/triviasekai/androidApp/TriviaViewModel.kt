package com.example.triviasekai.androidApp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.triviasekai.shared.TriviaSDK
import com.example.triviasekai.shared.model.Category
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class TriviaViewModel : ViewModel() {

    private val categoriesSharedFlow = MutableSharedFlow<List<Category>>()
    private val triviaSDK = TriviaSDK()

    fun categoriesSharedFlow(): SharedFlow<List<Category>> = categoriesSharedFlow.asSharedFlow()

    fun getCategories() {
        viewModelScope.launch {
            val categories = triviaSDK.getCategories().categories
            Log.d("zsao", "${categories.size} results retrieved")
            categoriesSharedFlow.emit(categories)
        }
    }
}