package com.nikhilbytes.composeassignment.ui.screens

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.ShoeScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope,
    onClick: (name: String, rotation: Float) -> Unit
) {
    Scaffold(topBar = { AppBar() }) {padding ->
        Surface {
            Column(modifier = Modifier.padding(padding)) {
                Title()
                ShoeModelNameListComposable()

                ShoeCarouselView(animatedVisibilityScope, onClick)

                MoreShoeVerticalListComposable()
            }
        }

    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar() {
    TopAppBar(
        title = { },
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            IconButton(onClick = { /* Handle search */ }) {
                Icon(Icons.Default.Search, contentDescription = "Search")
            }
        })
}

@Composable
fun Title() {
    Text(
        modifier = Modifier
            .padding(start = 16.dp, top = 16.dp)
            .fillMaxWidth(),
        text = "Shoes",
        color = Color.Black,
        fontSize = 26.sp,
        fontWeight = FontWeight.Bold,
    )
}

@Preview
@Composable
fun PreviewShoeScreen() {
//    ShoeScreen {
//
//    }
}

