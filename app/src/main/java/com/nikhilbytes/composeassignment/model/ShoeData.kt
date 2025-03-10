package com.nikhilbytes.composeassignment.model

import androidx.compose.ui.graphics.Color

data class ShoeData(
    val name: String,
    val nameColor : Color = Color.White,
    val price: String,
    val drawable: Int = 0,
    val bgcolor: Color = Color(0xFFE57373),
    val detailDrawable:Int = 0
)
