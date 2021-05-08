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

val Red100 = Color(0xffffcdd2)
val Pink100 = Color(0xfff8bbd0)
val Purple100 = Color(0xffe1bee7)
val DeepPurple100 = Color(0xffd1c4e9)
val Indigo100 = Color(0xffc5cae9)
val Blue100 = Color(0xffbbdefb)
val LightBlue100 = Color(0xffb3e5fc)
val Cyan100 = Color(0xffb2ebf2)
val Teal100 = Color(0xffb2dfdb)
val Green100 = Color(0xffc8e6c9)
val LightGreen100 = Color(0xffdcedc8)
val Lime100 = Color(0xfff0f4c3)
val Yellow100 = Color(0xfffff9c4)
val Amber100 = Color(0xffffecb3)
val Orange100 = Color(0xffffe0b2)
val DeepOrange100 = Color(0xffffccbc)
val Brown100 = Color(0xffd7ccc8)
val Grey100 = Color(0xfff5f5f5)
val BlueGrey100 = Color(0xffcfd8dc)


@Composable
fun TriviaTheme(content: @Composable () -> Unit) {
    MaterialTheme(content = content)
}