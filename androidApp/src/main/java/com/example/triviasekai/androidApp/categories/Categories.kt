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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CategoriesScreen() {
    val list = listOf("Entertainment", "Sports", "Mathematics", "General knowledge")
    Column {
        Text(
            text = "Select a category",
            Modifier.fillMaxWidth(1f).padding(top = 16.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        CategoriesList(list = list)
    }
}

@Composable
fun CategoriesList(list: List<String>) {
    LazyColumn(
        Modifier
            .fillMaxHeight(1f)
            .padding(16.dp)
    ) {
        items(list) {
            Text(
                text = it,
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
    CategoriesScreen()
}

@Preview
@Composable
fun CategoriesListPreview() {
    CategoriesList(listOf("Entertainment", "Sports", "Mathematics", "General knowledge"))
}