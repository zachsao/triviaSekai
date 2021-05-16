package com.example.triviasekai.androidApp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.*
import com.example.triviasekai.androidApp.categories.CategoriesScreen
import com.example.triviasekai.androidApp.questions.QuestionsScreen
import com.example.triviasekai.androidApp.ui.TriviaTheme
import com.example.triviasekai.shared.model.Difficulty

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<TriviaViewModel>()

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

@Composable
fun HomeScreen(onClick: () -> Unit) {
    Column(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            fontSize = 48.sp,
            fontFamily = FontFamily(Font(R.font.roboto)),
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = onClick, shape = CircleShape) {
            Row(Modifier.padding(start = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                Text(text = "START", fontSize = 24.sp)
                Icon(Icons.Rounded.PlayArrow, contentDescription = "", Modifier.size(48.dp))
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen {}
}