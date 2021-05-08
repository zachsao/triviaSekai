package com.example.triviasekai.androidApp.ui

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.triviasekai.androidApp.R

@Composable
fun TriviaIcon(
    resourceId: Int,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = resourceId),
        contentDescription = "image",
        modifier = modifier
    )
}

enum class TriviaIcons(val id: Int, val resourceId: Int) {
    Knowledge(9, R.drawable.ic_knowledge),
    Books(10, R.drawable.ic_books),
    Films(11, R.drawable.ic_films),
    Music(12, R.drawable.ic_music),
    Theatre(13, R.drawable.ic_theatre),
    Tv(14, R.drawable.ic_television),
    Games(15, R.drawable.ic_games),
    BoardGames(16, R.drawable.ic_board_games),
    Nature(17, R.drawable.ic_nature),
    Computer(18, R.drawable.ic_computer),
    Maths(19, R.drawable.ic_maths),
    Mythology(20, R.drawable.ic_mythology),
    Sports(21, R.drawable.ic_sports),
    Geography(22, R.drawable.ic_geography),
    History(23, R.drawable.ic_history),
    Politics(24, R.drawable.ic_politics),
    Art(25, R.drawable.ic_art),
    Celebrity(26, R.drawable.ic_celebrity),
    Animals(27, R.drawable.ic_animals),
    Vehicle(28, R.drawable.ic_vehicle),
    Comic(29, R.drawable.ic_comic),
    Gadget(30, R.drawable.ic_gadget),
    Anime(31, R.drawable.ic_anime),
    Cartoons(32, R.drawable.ic_cartoons)
}