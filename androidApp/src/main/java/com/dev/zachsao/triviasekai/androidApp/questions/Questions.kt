package com.dev.zachsao.triviasekai.androidApp.questions

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dev.zachsao.triviasekai.androidApp.TriviaViewModel
import com.dev.zachsao.triviasekai.androidApp.ui.Green200
import com.dev.zachsao.triviasekai.androidApp.ui.Red200
import com.dev.zachsao.triviasekai.androidApp.ui.Yellow200
import com.dev.zachsao.triviasekai.shared.model.Difficulty
import com.dev.zachsao.triviasekai.shared.model.TriviaResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

@ExperimentalAnimationApi
@Composable
fun QuestionsScreen(
    viewModel: TriviaViewModel,
    onBackClick: () -> Unit,
    onLastAnswerClicked: () -> Unit
) {
    val questionState = viewModel.currentQuestionSharedFlow().collectAsState(initial = null)
    Content(
        questionState.value,
        onBackClick
    ) { index, isCorrect ->
        viewModel.nextQuestion(index, isCorrect, onLastAnswerClicked)
    }
}

@ExperimentalAnimationApi
@Composable
private fun Content(
    question: Pair<TriviaResult, Int>?, onBackClick: () -> Unit,
    onAnswerClicked: (Int, Boolean) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = question?.first?.category ?: "") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "arrow back"
                        )
                    }
                }
            )
        },
    ) {
        question?.let { (result, index) ->
            QuestionContent(result) { isCorrect -> onAnswerClicked(index.inc(), isCorrect) }
        } ?: run {
            Column(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
private fun QuestionContent(
    currentResult: TriviaResult,
    onAnswerClicked: (Boolean) -> Unit
) {
    var visible by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()
    val backgroundColor = difficultyColor(currentResult.difficulty)
    Surface(
        Modifier.fillMaxSize(),
        color = backgroundColor,
    ) {}
    Column(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AnimatedVisibility(
            visible = visible,
            enter = slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = spring(stiffness = Spring.StiffnessLow)
            ),
            exit = slideOutHorizontally(
                targetOffsetX = { -it },
                animationSpec = spring(stiffness = Spring.StiffnessLow)
            ),
            modifier = Modifier.weight(1f)
        ) {
            Card(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .wrapContentHeight(),
                elevation = 4.dp,
                shape = RoundedCornerShape(10.dp)
            ) {
                LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
                    item {
                        Text(
                            text = currentResult.question,
                            modifier = Modifier.padding(16.dp),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    items(currentResult.shuffleAnswers()) { (answer, isCorrect) ->
                        OutlinedButton(
                            onClick = {
                                scope.launch {
                                    visible = false
                                    delay(500L)
                                    onAnswerClicked(isCorrect)
                                    visible = true
                                }
                            },
                            Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Text(text = answer)
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Preview(showSystemUi = true)
@Composable
fun QuestionPreview() {
    QuestionContent(
        currentResult = TriviaResult(
            question = "Question ?",
            difficulty = "easy",
            incorrectAnswers = listOf("abc", "xyz"),
            correctAnswer = "def",
            category = "category"
        )
    ) {}
}

fun TriviaResult.shuffleAnswers(): List<Pair<String, Boolean>> {
    return incorrectAnswers.map { Pair(it, false) }.plus(Pair(correctAnswer, true)).shuffled()
}

private fun difficultyColor(difficulty: String): Color {
    return when (Difficulty.valueOf(difficulty.capitalize(Locale.ROOT))) {
        Difficulty.Easy -> Green200
        Difficulty.Medium -> Yellow200
        Difficulty.Hard -> Red200
    }
}