package com.example.triviasekai.androidApp.questions

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun QuestionsScreen(id: Int) {
    Text(text = "Questions for category $id")
}