package com.example.triviasekai.androidApp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.example.triviasekai.androidApp.categories.CategoriesScreen
import com.example.triviasekai.androidApp.home.HomeScreen
import com.example.triviasekai.androidApp.questions.QuestionsScreen
import com.example.triviasekai.androidApp.ui.TriviaTheme
import com.example.triviasekai.shared.model.Difficulty
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<TriviaViewModel>()

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TriviaTheme {
                Surface {
                    MainContainer(viewModel = viewModel)
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun MainContainer(viewModel: TriviaViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen {
                viewModel.getCategories()
                navController.navigate("categories")
            }
        }
        composable("categories") {
            CategoriesScreen(viewModel = viewModel) { categoryId ->
                viewModel.getQuestions(categoryId)
                navController.navigate("questions")
            }
        }
        composable("questions") {
            QuestionsScreen(
                viewModel,
                {
                    navController.popBackStack()
                    viewModel.selectDifficulty(Difficulty.Easy)
                },
                { navController.navigate("endGame") { popUpTo(route = "categories"){} } }
            )
        }
        composable("endGame") {
            EndGameScreen(viewModel = viewModel) {
                viewModel.startOver()
                navController.navigate("questions") { popUpTo(route = "categories"){} }
            }
        }
    }
}