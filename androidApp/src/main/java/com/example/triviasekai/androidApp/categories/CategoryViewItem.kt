package com.example.triviasekai.androidApp.categories


import com.example.triviasekai.androidApp.ui.TriviaColors
import com.example.triviasekai.androidApp.ui.TriviaIcons

data class CategoryViewItem(
    val id: Int,
    val name: String,
    val icon: TriviaIcons,
    val color: TriviaColors
)
