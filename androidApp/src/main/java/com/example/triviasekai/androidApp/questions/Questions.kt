package com.example.triviasekai.androidApp.questions

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.triviasekai.androidApp.TriviaViewModel
import com.example.triviasekai.shared.model.TriviaResult

@Composable
fun QuestionsScreen(viewModel: TriviaViewModel, category: String) {
    val questionState = viewModel.currentQuestionSharedFlow().collectAsState(initial = null)
    Content(questionState.value, category)
}

@Composable
private fun Content(question: Pair<TriviaResult, Int>?, category: String) {
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = category) }) }
    ) {
        question?.let { (result, index) ->
            QuestionContent(result, index)
        } ?: run {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
private fun QuestionContent(currentResult: TriviaResult, index: Int) {
    Column(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Difficulty: ${currentResult.difficulty}", Modifier.padding(16.dp))
        Text(text = "$index/10", fontSize = 20.sp, fontWeight = FontWeight.Bold)
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
                        onClick = { /*TODO*/ },
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
        ),
        index = 0
    )
}

fun TriviaResult.shuffleAnswers(): List<Pair<String, Boolean>> {
    return incorrectAnswers.map { Pair(it, false) }.plus(Pair(correctAnswer, true)).shuffled()
}