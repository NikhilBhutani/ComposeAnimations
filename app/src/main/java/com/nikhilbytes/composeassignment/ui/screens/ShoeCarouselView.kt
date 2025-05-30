package com.nikhilbytes.composeassignment.ui.screens

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.nikhilbytes.composeassignment.R
import com.nikhilbytes.composeassignment.ui.ShoeData
import com.nikhilbytes.composeassignment.ui.screens.ShoeItemView as ShoeItemView1

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.ShoeCarouselView(
    animatedVisibilityScope: AnimatedVisibilityScope,
    onClick: (name: String, rotation: Float) -> Unit
) {
    val shoesList = ShoeData.getShoeList()
    ShoeCarouselListView(shoesList, animatedVisibilityScope, onClick)
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.ShoeCarouselListView(
    shoesList: List<com.nikhilbytes.composeassignment.model.ShoeData>,
    animatedContentScope: AnimatedVisibilityScope,
    onClick: (name: String, rotation: Float) -> Unit
) {

    val pagerState = rememberPagerState(pageCount = { shoesList.size })

    HorizontalPager(
        state = pagerState,
        pageSpacing = 32.dp,
        pageSize = object : PageSize {
            override fun Density.calculateMainAxisPageSize(
                availableSpace: Int,
                pageSpacing: Int,
            ): Int {

                return (availableSpace * 0.75f).toInt()
            }
        },
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        contentPadding = PaddingValues(end = 32.dp)
    ) { currentPage ->
        val currentPageOffset =
            (pagerState.currentPage + pagerState.currentPageOffsetFraction - currentPage).coerceIn(
                -1f,
                1f
            )

        val shoeRotationZ = lerp(30f, 0f, 1f - currentPageOffset)
        val shoeTranslationX = if (currentPageOffset < 0) {
            currentPageOffset * -150f
        } else {
            0f
        }

        val shoesOffsetX = lerp(30f, 0f, 1f - currentPageOffset)


        ShoeItemView1(
            shoesList[currentPage],
            currentPage,
            shoeRotationZ,
            shoeTranslationX,
            shoesOffsetX,
            onClick,
            animatedContentScope
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.ShoeItemView(
    shoeCarouselItem: com.nikhilbytes.composeassignment.model.ShoeData,
    currentPage: Int,
    shoeRotationZ: Float,
    shoeTranslationX: Float,
    shoesOffsetX: Float,
    onClick: (name: String, rotation: Float) -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {

    Box(
        modifier = Modifier
            .width(294.dp)
            .height(304.dp)

    ) {

        CurvedBezierComposable(modifier = Modifier
            .width(256.dp)
            .height(280.dp)
            .align(Alignment.TopStart)
            .sharedElement(
                sharedContentState = rememberSharedContentState(key = "backdrop/${shoeCarouselItem.name}"),
                animatedVisibilityScope = animatedVisibilityScope,
                boundsTransform = { _, _ ->
                    tween(durationMillis = 700)
                }
            ), color = shoeCarouselItem.bgcolor, isRounded = true)

//        Box(
//            modifier = Modifier
//                .width(226.dp)
//                .height(280.dp)
//                .clip(RoundedCornerShape(16.dp))
//                .background(shoeCarouselItem.bgcolor)
//                .clickable {
//                    onClick(shoeCarouselItem.name, 30f)
//                }.sharedElement(
//                    sharedContentState = rememberSharedContentState(key = "backdrop/${shoeCarouselItem.name}"),
//                    animatedVisibilityScope = animatedVisibilityScope,
//                    boundsTransform = { _, _ ->
//                        tween(durationMillis = 800, )
//                    }
//                ),
//
//
//            )

        Column(modifier = Modifier
            .padding(start = 24.dp, top = 24.dp)
            .clickable {
                onClick(shoeCarouselItem.name, 30f)
            }) {
            Text(
                text = shoeCarouselItem.name,
                fontWeight = FontWeight.Bold,
                color = shoeCarouselItem.nameColor,
                fontSize = 24.sp
            )

            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = shoeCarouselItem.price,
                fontWeight = FontWeight.ExtraLight,
                color = shoeCarouselItem.nameColor,
                fontSize = 18.sp
            )

            Row() {
                Divider(
                    modifier = Modifier
                        .height(155.dp)
                        .width(0.5.dp),
                    color = shoeCarouselItem.nameColor
                )
            }
        }


        val widAndHeight = if (shoeCarouselItem.drawable == R.drawable.red_shoe) {
            Pair(360.dp, 240.dp)
        } else {
            Pair(460.dp, 340.dp)
        }

        Image(
            painter = painterResource(id = shoeCarouselItem.drawable),
            contentDescription = "Shoe Image",
            alignment = Alignment.CenterEnd,
            modifier = Modifier
                .width(widAndHeight.first)
                .height(widAndHeight.second)
                .align(Alignment.CenterEnd)
                .offset(x = 50.dp)
                .graphicsLayer {
                    rotationZ = shoeRotationZ
                    translationX = shoeTranslationX
                }
                .clickable {
                    onClick(shoeCarouselItem.name, 30f)
                }
                .sharedElement(
                    sharedContentState = rememberSharedContentState(key = "shoe/${shoeCarouselItem.name}"),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = { initialBounds, targetBounds ->
                        keyframes {
                            durationMillis = 700
                            initialBounds at 0 using FastOutSlowInEasing
                            targetBounds at 700 using FastOutSlowInEasing
                        }
                    },
                ),
        )
    }
}


@Preview
@Composable
fun ShowCarouselViewPreview() {
//    val animatedVisibilityScope = AnimatedVisibilityScope
//    ShoeCarouselView( {})
}