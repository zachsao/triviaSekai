package com.example.triviasekai.androidApp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AboutScreen(onClick: () -> Unit) {
    Box(Modifier.fillMaxSize()) {
        IconButton(onClick = onClick, Modifier.padding(16.dp)) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "arrow back", tint = MaterialTheme.colors.primary)
        }
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(painter = painterResource(id = R.drawable.logo), contentDescription = "logo")
            Text(text = "v${BuildConfig.VERSION_NAME}")
        }
        Credits()
    }
}

@Composable
private fun Credits() {
    Column(
        Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(text = stringResource(id = R.string.icons_made_by))
        stringArrayResource(id = R.array.icon_credits).forEach {
            Text(text = it, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun AboutPreview() {
    AboutScreen{}
}