package com.nikhilbytes.composeassignment.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nikhilbytes.composeassignment.R
import com.nikhilbytes.composeassignment.model.ShoeData


@Composable
fun MoreShoeVerticalListComposable() {
    val shoesList = listOf(
        ShoeData(
            "Undercover React Presto",
            Color(0xFF1F2732),
            "₹12,995",
            R.drawable.shoe_1,
            Color(0xFFE24C4D)
        ),
        ShoeData(
            "Air Zoom Pegasus 37",
            Color(0xFF1F2732),
            "₹12,995",
            R.drawable.shoe_2,
            Color(0xFFE24C4D)
        ),
        ShoeData(
            "Undercover React Presto",
            Color(0xFF1F2732),
            "₹12,995",
            R.drawable.shoe_1,
            Color(0xFFE24C4D)
        ),
        ShoeData(
            "Air Zoom Pegasus 37",
            Color(0xFF1F2732),
            "₹12,995",
            R.drawable.shoe_2,
            Color(0xFFE24C4D)
        ),
        ShoeData(
            "Undercover React Presto",
            Color(0xFF1F2732),
            "₹12,995",
            R.drawable.shoe_1,
            Color(0xFFE24C4D)
        ),
        ShoeData(
            "Air Zoom Pegasus 37",
            Color(0xFF1F2732),
            "₹12,995",
            R.drawable.shoe_2,
            Color(0xFFE24C4D)
        ),
        ShoeData(
            "Undercover React Presto",
            Color(0xFF1F2732),
            "₹12,995",
            R.drawable.shoe_1,
            Color(0xFFE24C4D)
        ),
        ShoeData(
            "Air Zoom Pegasus 37",
            Color(0xFF1F2732),
            "₹12,995",
            R.drawable.shoe_2,
            Color(0xFFE24C4D)
        ),
    )

    LazyColumn() {
        items(shoesList) {
            MoreShoeItem(it)
        }
    }
}


@Composable
fun MoreShoeItem(shoeCarouselItem: ShoeData) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .background(Color.White)
    ) {

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp),
            color = Color(0xFFF4F4F4)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly

        ) {
            
            Image(
                painter = painterResource(id = shoeCarouselItem.drawable),
                contentDescription = "Shoe Image",
            )

            Column(modifier = Modifier
                .padding(start = 16.dp)
                .fillMaxSize()) {

                Text(
                    text = shoeCarouselItem.name,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF1F2732),
                    fontSize = 16.sp,
                    lineHeight = 18.sp
                )

                Text(
                    text = shoeCarouselItem.price,
                    fontWeight = FontWeight.ExtraLight,
                    color = Color(0xFF1F2732),
                    fontSize = 14.sp
                )

            }
        }
    }
}

@Preview
@Composable
fun MoreShoeItemComposable() {
    MoreShoeVerticalListComposable()
}