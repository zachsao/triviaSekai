package com.example.triviasekai.androidApp.categories

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.triviasekai.androidApp.R
import com.example.triviasekai.shared.model.Difficulty
import java.util.*

@Composable
fun DifficultyDialog(show: Boolean, onDismiss: () -> Unit, onPositiveClick: (Difficulty) -> Unit) {
    if (show) {
        var selectedLevel by remember { mutableStateOf(Difficulty.Easy) }
        val onLevelSelected: (Difficulty) -> Unit = { selectedLevel = it }
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = stringResource(R.string.dialog_difficulty_title)) },
            buttons = {
                DialogContent(selectedLevel, onLevelSelected, onDismiss, onPositiveClick)
            }
        )
    }

}

@Composable
private fun DialogContent(
    selectedLevel: Difficulty,
    onLevelSelected: (Difficulty) -> Unit,
    onDismiss: () -> Unit,
    onPositiveClick: (Difficulty) -> Unit
) {
    LazyColumn {
        items(Difficulty.values().toList()) { level ->
            Row(
                Modifier
                    .padding(start = 8.dp)
                    .fillMaxWidth()
                    .selectable(
                        selected = selectedLevel == level,
                        onClick = { onLevelSelected(level) }
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(
                    modifier = Modifier.padding(16.dp),
                    selected = selectedLevel == level,
                    onClick = { onLevelSelected(level) },
                    colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colors.primary)
                )
                Text(text = level.name.capitalize(Locale.ROOT), fontSize = 16.sp)
            }
        }
        item {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                TextButton(modifier = Modifier.padding(16.dp), onClick = onDismiss) {
                    Text(text = stringResource(R.string.cancel))
                }
                TextButton(
                    modifier = Modifier.padding(16.dp),
                    onClick = { onPositiveClick(selectedLevel) }) {
                    Text(text = stringResource(R.string.ok))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LevelDialogPreview() {
    DialogContent(selectedLevel = Difficulty.Easy, onLevelSelected = {}, onDismiss = {}) {}
}