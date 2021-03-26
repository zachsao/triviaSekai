package com.example.triviasekai.shared

import com.example.triviasekai.shared.model.Response
import com.example.triviasekai.shared.model.TriviaCategories
import com.example.triviasekai.shared.network.TriviaApi

class TriviaSDK {
    private val api = TriviaApi()

    @Throws(Exception::class)
    suspend fun getQuestions(): Response {
        return api.getQuestions()
    }

    @Throws(Exception::class)
    suspend fun getCategories(): TriviaCategories {
        return api.getCategories()
    }
}