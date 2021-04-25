package com.example.triviasekai.androidApp.categories

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.triviasekai.androidApp.TriviaViewModel
import com.example.triviasekai.shared.model.Category

@Composable
fun CategoriesScreen(viewModel: TriviaViewModel) {
    val categoriesState = viewModel.categoriesSharedFlow().collectAsState(initial = listOf())
    viewModel.getCategories()
    CategoriesContent(list = categoriesState.value)
}

@Composable
fun CategoriesContent(list: List<Category>) {
    Column {
        Text(
            text = "Select a category",
            Modifier
                .fillMaxWidth(1f)
                .padding(top = 16.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        CategoriesList(list = list)
    }
}

@Composable
fun CategoriesList(list: List<Category>) {
    LazyColumn(
        Modifier
            .fillMaxHeight(1f)
            .padding(16.dp)
    ) {
        items(list) {
            Text(
                text = it.name,
                Modifier
                    .fillMaxWidth(1f)
                    .padding(top = 8.dp, bottom = 8.dp),
                textAlign = TextAlign.Center
            )
            Divider()
        }
    }
}

@Preview
@Composable
fun ScreenPreview() {
    CategoriesContent(
        list = listOf(
            Category(0, "Entertainment"),
            Category(0, "Sports"),
            Category(0, "Maths"),
            Category(0, "Gen. Knowledge")
        )
    )
}