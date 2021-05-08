package com.example.triviasekai.androidApp.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

enum class TriviaColors(val id: Int, val color: Long) {
    Red(9, 0xffffcdd2),
    Pink(10, 0xfff8bbd0),
    Purple(11,0xffe1bee7),
    DeepPurple(12, 0xffd1c4e9),
    Indigo(13, 0xffc5cae9),
    Blue(14, 0xffbbdefb),
    LightBlue(15, 0xffb3e5fc),
    Cyan(16, 0xffb2ebf2),
    Teal(17, 0xffb2dfdb),
    Green(18, 0xffc8e6c9),
    LightGreen(19, 0xffdcedc8),
    Lime(20, 0xfff0f4c3),
    Yellow(21, 0xfffff9c4),
    Amber(22, 0xffffecb3),
    Orange(23, 0xffffe0b2),
    DeepOrange(24, 0xffffccbc),
    Brown(25, 0xffd7ccc8),
    Grey(26, 0xfff5f5f5),
    BlueGrey(27, 0xffcfd8dc),
    White(0, 0xffffffff)
}

val Green700 = Color(0xff388e3c)
val Green200 = Color(0xffa5d6a7)

val Yellow700 = Color(0xfffbc02d)
val Yellow200 = Color(0xfffff59d)

val Red200 = Color(0xffef9a9a)
val Red700 = Color(0xffd32f2f)


@Composable
fun TriviaTheme(content: @Composable () -> Unit) {
    MaterialTheme(content = content)
}