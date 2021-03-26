package com.example.triviasekai.shared.network

import com.example.triviasekai.shared.model.Response
import com.example.triviasekai.shared.model.TriviaCategories
import io.ktor.client.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.*
import kotlinx.serialization.json.Json

private const val BASE_URL = "https://opentdb.com/"

class TriviaApi {
    private val httpClient = HttpClient {
        install(JsonFeature) {
            val json = Json { ignoreUnknownKeys = true }
            serializer = KotlinxSerializer(json)
        }
    }

    private val triviaEndpoint: (Int, Int) -> String = { categoryId, amount ->
        "https://opentdb.com/api.php?amount=$amount&category=$categoryId"
    }

    suspend fun getCategories(): TriviaCategories {
        return httpClient.get(BASE_URL + "api_category.php")
    }

    suspend fun getQuestions(categoryId: Int = 31, amount: Int = 10): Response {
        return httpClient.get(triviaEndpoint(categoryId, amount))
    }
}