package com.example.triviasekai.androidApp.categories

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    val showDialog = remember { mutableStateOf(false) }
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "Select a category") }) }
    ) {
        Column {
            CategoriesList(list = list) { showDialog.value = true }
            LevelDialog(showDialog.value) { showDialog.value = false }
        }
    }

}

@Composable
fun CategoriesList(list: List<Category>, onItemClick: () -> Unit) {
    LazyColumn(
        Modifier.fillMaxHeight(1f),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(list) {
            Text(
                text = it.name,
                Modifier
                    .fillMaxWidth(1f)
                    .padding(top = 8.dp, bottom = 8.dp)
                    .clickable(onClick = onItemClick),
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