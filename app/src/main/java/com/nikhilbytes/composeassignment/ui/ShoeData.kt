package com.nikhilbytes.composeassignment.ui

import androidx.compose.ui.graphics.Color
import com.nikhilbytes.composeassignment.R
import com.nikhilbytes.composeassignment.model.ShoeData

object ShoeData {

    fun getShoeList(): List<ShoeData>{
     return listOf(
         ShoeData(
             "Air Max 97",
             Color.Black,
             "₹11,897",
             R.drawable.yellow_shoe,
             Color(0xFFFDBA62),
             R.drawable.yellow_shoe_bdrop
         ),
         ShoeData(
             "KD13 EP",
             Color.White,
             "₹12,995",
             R.drawable.blue_shoe,
             Color(0xFF4B81F4),
             R.drawable.blue_shoe_bdrop
         ),
         ShoeData(
             "Air Presto",
             Color.White,
             "₹12,995",
             R.drawable.green_shoe,
             Color(0xFF599C99),
             R.drawable.green_shoe_bdrop
         ),
         ShoeData(
             "Alpha Savage",
             Color.White,
             "₹8,895",
             R.drawable.red_shoe,
             Color(0xFFE24C4D),
             R.drawable.red_shoe_bdrop
         ),
         ShoeData(
             "Air Max 97",
             Color.Black,
             "₹11,897",
             R.drawable.yellow_shoe,
             Color(0xFFFDBA62),
             R.drawable.yellow_shoe_bdrop
         ),
         ShoeData(
             "KD13 EP",
             Color.White,
             "₹12,995",
             R.drawable.blue_shoe,
             Color(0xFF4B81F4),
             R.drawable.blue_shoe_bdrop
         ),
         ShoeData(
             "Air Presto",
             Color.White,
             "₹12,995",
             R.drawable.green_shoe,
             Color(0xFF599C99),
             R.drawable.green_shoe_bdrop
         ),
         ShoeData(
             "Alpha Savage",
             Color.White,
             "₹8,895",
             R.drawable.red_shoe,
             Color(0xFFE24C4D),
             R.drawable.red_shoe_bdrop
         ),
        )
    }
}