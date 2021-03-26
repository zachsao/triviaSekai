package com.example.triviasekai.androidApp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable

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
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = title)
            })
        }
    ) {
        Text(text = "Home screen")
    }
}
