package com.example.triviasekai.androidApp.questions

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.triviasekai.androidApp.TriviaViewModel
import com.example.triviasekai.androidApp.ui.*
import com.example.triviasekai.shared.model.Difficulty
import com.example.triviasekai.shared.model.TriviaResult
import java.util.*

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

@Composable
private fun QuestionContent(
    currentResult: TriviaResult,
    onAnswerClicked: (Boolean) -> Unit
) {
    Column(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val (textBackgroundColor, borderColor) = difficultyColor(currentResult.difficulty)
        Surface(
            Modifier.padding(top = 16.dp),
            color = textBackgroundColor,
            border = BorderStroke(2.dp, borderColor),
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(
                text = currentResult.difficulty.capitalize(Locale.ROOT),
                Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
            )
        }
        Card(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .weight(1f)
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
                        onClick = { onAnswerClicked(isCorrect) },
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

private fun difficultyColor(difficulty: String): Pair<Color, Color> {
    return when (Difficulty.valueOf(difficulty.capitalize(Locale.ROOT))) {
        Difficulty.Easy -> Pair(Green200, Green700)
        Difficulty.Medium -> Pair(Yellow200, Yellow700)
        Difficulty.Hard -> Pair(Red200, Red700)
    }
}