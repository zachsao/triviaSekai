package com.dev.zachsao.triviasekai.androidApp.categories


import com.dev.zachsao.triviasekai.androidApp.ui.TriviaColors
import com.dev.zachsao.triviasekai.androidApp.ui.TriviaIcons

data class CategoryViewItem(
    val id: Int,
    val name: String,
    val icon: TriviaIcons,
    val color: TriviaColors
)
