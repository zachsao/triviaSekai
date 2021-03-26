package com.example.triviasekai.androidApp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.triviasekai.androidApp.categories.CategoriesScreen

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface {
                HomeScreen(getString(R.string.app_name))
            }
        }
    }
}

@Composable
fun HomeScreen(title: String) {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = title)
            })
        }
    ) {
        NavHost(navController = navController, startDestination = "categories") {
            composable("categories") { CategoriesScreen() }
        }
    }
}
