package com.dev.zachsao.triviasekai.androidApp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dev.zachsao.triviasekai.shared.model.Difficulty
import com.dev.zachsao.triviasekai.shared.model.HighScore

@Composable
fun EndGameScreen(viewModel: TriviaViewModel, onClick: () -> Unit) {
    val (score, total) = viewModel.score()
    EndGameContent(score, total, viewModel.highScores(), onClick)
}

@Composable
fun EndGameContent(score: Int, total: Int, highScores: List<HighScore>, onClick: () -> Unit) {
    Column(
        Modifier.padding(16.dp),
    ) {
        Text(
            text = "Game Over",
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(R.font.chewy))
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Score: $score/$total",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.chewy))
            )
            OutlinedButton(
                onClick = onClick,
                shape = CircleShape,
                border = BorderStroke(2.dp, MaterialTheme.colors.primary)
            ) {
                Text(text = "TRY AGAIN", fontFamily = FontFamily(Font(R.font.chewy)))
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Scoreboard",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(R.font.chewy))
        )
        Spacer(modifier = Modifier.height(16.dp))
        Scoreboard(highScores = highScores)
    }
}

@Composable
fun Scoreboard(highScores: List<HighScore>) {
    LazyColumn {
        item {
            Column {
                Row(Modifier.fillMaxWidth()) {
                    Text(text = "Category", Modifier.weight(2f), fontSize = 18.sp)
                    Text(text = "Difficulty", Modifier.weight(1f), textAlign = TextAlign.Center, fontSize = 18.sp)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Score", Modifier.weight(1f), textAlign = TextAlign.Center, fontSize = 18.sp)
                }
                Divider()
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        items(highScores) {
            Row(Modifier.fillMaxWidth()) {
                Text(text = it.category, Modifier.weight(2f), fontSize = 18.sp)
                Text(text = it.difficulty.name, Modifier.weight(1f), textAlign = TextAlign.Center, fontSize = 18.sp)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "${it.score}/10", Modifier.weight(1f), textAlign = TextAlign.Center, fontSize = 18.sp)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EndGameContentPreview() {
    EndGameContent(score = 5, total = 10, listOf(HighScore("Knowledge", 5, Difficulty.Easy))) {}
}