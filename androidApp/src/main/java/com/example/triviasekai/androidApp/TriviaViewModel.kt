package com.example.triviasekai.androidApp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.triviasekai.shared.TriviaSDK
import com.example.triviasekai.shared.model.Category
import kotlinx.coroutines.launch

class TriviaViewModel : ViewModel() {

    private val categoriesLiveData = MutableLiveData<List<Category>>()
    private val triviaSDK = TriviaSDK()

    fun categoriesLiveData(): LiveData<List<Category>> = categoriesLiveData

    fun getCategories() {
       viewModelScope.launch {
           val categories = triviaSDK.getCategories().categories
           Log.d("zsao", "${categories.size} results retrieved")
           categoriesLiveData.value = categories
       }
    }
}