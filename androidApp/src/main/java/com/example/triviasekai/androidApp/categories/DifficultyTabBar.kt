package com.example.triviasekai.androidApp.categories

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TabPosition
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.triviasekai.androidApp.ui.Green700
import com.example.triviasekai.androidApp.ui.Red700
import com.example.triviasekai.androidApp.ui.Yellow700
import com.example.triviasekai.shared.model.Difficulty
import java.util.*

@Composable
fun DifficultyTabBar(
    backgroundColor: Color,
    tabPage: Difficulty,
    onTabSelected: (difficulty: Difficulty) -> Unit
) {
    TabRow(
        selectedTabIndex = tabPage.ordinal,
        backgroundColor = backgroundColor,
        indicator = { tabPositions ->
            HomeTabIndicator(tabPositions, tabPage)
        },
    ) {
        DifficultyTab(
            title = Difficulty.Easy.name.capitalize(Locale.ROOT),
            onClick = { onTabSelected(Difficulty.Easy) }
        )
        DifficultyTab(
            title = Difficulty.Medium.name.capitalize(Locale.ROOT),
            onClick = { onTabSelected(Difficulty.Medium) }
        )
        DifficultyTab(
            title = Difficulty.Hard.name.capitalize(Locale.ROOT),
            onClick = { onTabSelected(Difficulty.Hard) }
        )
    }
}

@Composable
private fun HomeTabIndicator(
    tabPositions: List<TabPosition>,
    tabPage: Difficulty
) {
    val transition = updateTransition(tabPage, label = "Tab indicator")
    val indicatorLeft by transition.animateDp(
        label = "indicator left",
        transitionSpec = {
            if (Difficulty.Easy isTransitioningTo Difficulty.Medium || Difficulty.Medium isTransitioningTo Difficulty.Hard || Difficulty.Easy isTransitioningTo Difficulty.Hard) {
                // Indicator moves to the right.
                // The left edge moves slower than the right edge.
                spring(stiffness = Spring.StiffnessVeryLow)
            } else {
                // Indicator moves to the left.
                // The left edge moves faster than the right edge.
                spring(stiffness = Spring.StiffnessMedium)
            }
        }
    ) { page ->
        tabPositions[page.ordinal].left
    }
    val indicatorRight by transition.animateDp(
        transitionSpec = {
            if (Difficulty.Easy isTransitioningTo Difficulty.Medium || Difficulty.Medium isTransitioningTo Difficulty.Hard || Difficulty.Easy isTransitioningTo Difficulty.Hard) {
                // Indicator moves to the right
                // The right edge moves faster than the left edge.
                spring(stiffness = Spring.StiffnessMedium)
            } else {
                // Indicator moves to the left.
                // The right edge moves slower than the left edge.
                spring(stiffness = Spring.StiffnessVeryLow)
            }
        },
        label = "Indicator right"
    ) { page ->
        tabPositions[page.ordinal].right
    }
    val color by transition.animateColor(label = "border color") { page ->
        if (page == Difficulty.Easy) Green700 else if (page == Difficulty.Medium) Yellow700 else Red700
    }
    Box(
        Modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.BottomStart)
            .offset(x = indicatorLeft)
            .width(indicatorRight - indicatorLeft)
            .padding(4.dp)
            .fillMaxSize()
            .border(
                BorderStroke(2.dp, color),
                RoundedCornerShape(4.dp)
            )
    )
}

@Composable
private fun DifficultyTab(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title)
    }
}