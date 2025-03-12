package com.nikhilbytes.composeassignment.ui.screens

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateBounds
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavHostController
import com.nikhilbytes.composeassignment.R
import com.nikhilbytes.composeassignment.ui.ShoeData


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.ShoeDetailScreen(
    name: String,
    rotation: Float?,
    animatedVisibilityScope: AnimatedVisibilityScope,
    navController: NavHostController,

    ) {

    val shoe = ShoeData.getShoeList().find { it.name == name }

    val shoeDescription =
        "In the game's crucial moments, KD thrives. He takes over on both ends of the court, making defenders fear his unstoppable offense and leaving shooters helpless against his smothering defense."
    val shoeSizes = listOf("UK 6", "UK 7", "UK 8", "UK 9", "UK 10", "UK 11", "UK 12", "UK 13")

    var selectedSize by remember { mutableStateOf("UK 9") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),

        ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f, false)
        ) {

            Column(modifier = Modifier, verticalArrangement = Arrangement.Top) {
                Box(

                ) {
                    CurvedBezierComposable(
                        modifier = Modifier
                            .height(564.dp)
                            .width(564.dp)
                            .absoluteOffset(x = 45.dp, y = (-60).dp)
                            .sharedElement(
                                sharedContentState = rememberSharedContentState(key = "backdrop/${name}"),
                                animatedVisibilityScope = animatedVisibilityScope,
                                boundsTransform = { _, _ ->
                                    tween(durationMillis = 700)
                                }
                            ),
                        color = shoe?.bgcolor ?: Color(0xFF4285F4),
                    )

                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        },
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.TopStart)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }


                    IconButton(
                        onClick = { },
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.TopEnd)
                    ) {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = Color.White
                        )
                    }

                    val widAndHeight = if (shoe?.detailDrawable == R.drawable.red_shoe_bdrop) {
                        Pair(260.dp, 180.dp)
                    } else {
                        Pair(360.dp, 240.dp)
                    }

                    Image(
                        painter = painterResource(
                            id = shoe?.detailDrawable ?: R.drawable.blue_shoe_bdrop
                        ),
                        contentDescription = "Shoe",
                        modifier = Modifier
                            .padding(top = 40.dp)
                            .width(356.dp)
                            .height(242.dp)
                            .animateContentSize()
                            .align(Alignment.TopCenter)
                            .sharedElement(
                                sharedContentState = rememberSharedContentState(key = "shoe/${name}"),
                                animatedVisibilityScope = animatedVisibilityScope,
                                boundsTransform = { initialBounds, targetBounds ->
                                    keyframes {
                                        durationMillis = 700
                                        initialBounds at 0 using FastOutSlowInEasing
                                        targetBounds at 700 using FastOutSlowInEasing
                                    }
                                }
                            ),
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .offset(y = (-300).dp)
                    .align(Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.Top
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = shoe?.name.orEmpty(),
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1F2732)
                    )

                    Text(
                        text = shoe?.price.orEmpty(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1F2732)
                    )
                }

                Text(
                    text = shoeDescription,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraLight,
                    color = Color(0xFF7B8A9E),
                    lineHeight = 15.sp,
                    modifier = Modifier.padding(top = 4.dp),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .border(2.dp, Color(0xFF1F2732), RoundedCornerShape(8.dp))
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.green_shoe),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(4.dp),
                            contentScale = ContentScale.Fit
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.red_shoe),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(4.dp),
                            contentScale = ContentScale.Fit
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.blue_shoe),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(4.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                }

                Text(
                    text = "Select Size",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F2732),
                    modifier = Modifier.padding(top = 24.dp, bottom = 12.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    for (size in shoeSizes.subList(0, 4)) {
                        SizeOption(
                            size = size,
                            isSelected = size == selectedSize,
                            onSizeSelected = { selectedSize = it }
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    for (size in shoeSizes.subList(4, 8)) {
                        SizeOption(
                            size = size,
                            isSelected = size == selectedSize,
                            onSizeSelected = { selectedSize = it }
                        )
                    }
                }
            }
        }

        Button(
            onClick = {},
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1F2732)
            ),

            ) {
            Text(
                text = "Add to Bag",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun SizeOption(
    size: String,
    isSelected: Boolean,
    onSizeSelected: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .width(72.dp)
            .height(36.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(
                if (isSelected) Color(0xFFFFFFFF) else Color(0xFFFFFFFF)
            )
            .border(
                width = 1.dp,
                color = if (isSelected) Color(0xFF1F2732) else Color.LightGray,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onSizeSelected(size) },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = size,
            color = Color(0xFF1F2732),
            fontSize = 14.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            textAlign = TextAlign.Center
        )
    }
}
