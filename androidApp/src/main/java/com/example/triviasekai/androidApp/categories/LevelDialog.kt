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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.triviasekai.shared.model.Difficulty

@Composable
fun LevelDialog(show: Boolean, onDismiss: () -> Unit, onPositiveClick: (Int) -> Unit) {
    if (show) {
        val selectedLevel = remember { mutableStateOf(0) }
        val onLevelSelected: (Int) -> Unit = { index -> selectedLevel.value = index }
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = "Choose a difficulty") },
            buttons = {
                LazyColumn {
                    items(Difficulty.values().toList()) { level ->
                        Row(
                            Modifier
                                .padding(start = 8.dp)
                                .fillMaxWidth()
                                .selectable(
                                    selected = selectedLevel.value == level.id,
                                    onClick = { onLevelSelected(level.id) }
                                ),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            RadioButton(
                                modifier = Modifier.padding(16.dp),
                                selected = selectedLevel.value == level.id,
                                onClick = { onLevelSelected(level.id) }
                            )
                            Text(text = level.name)
                        }
                    }
                    item {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            TextButton(contentPadding = PaddingValues(16.dp), onClick = onDismiss) {
                                Text(text = "CANCEL")
                            }
                            TextButton(contentPadding = PaddingValues(16.dp), onClick = { onPositiveClick(selectedLevel.value) }) {
                                Text(text = "OK")
                            }
                        }
                    }
                }
            }
        )
    }

}

@Preview
@Composable
fun LevelDialogPreview() {
    LevelDialog(true, {}) {}
}