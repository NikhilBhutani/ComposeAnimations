package com.nikhilbytes.composeassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nikhilbytes.composeassignment.ui.screens.ShoeDetailScreen
import com.nikhilbytes.composeassignment.ui.screens.ShoeScreen
import com.nikhilbytes.composeassignment.ui.theme.ComposeAssignmentTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalSharedTransitionApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeAssignmentTheme {
                SharedTransitionLayout(modifier = Modifier.fillMaxSize()) {

                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "shoe_screen") {
                        composable("shoe_screen") {
                            ShoeScreen(this, this@SharedTransitionLayout) { name, rotation ->
                                navController.navigate("detail_screen/${name}/${rotation}")
                            }
                        }

                        composable(
                            route = "detail_screen/{name}/{rotation}",
                            arguments = listOf(
                                navArgument("name") {
                                    type = NavType.StringType
                                },
                                navArgument("rotation") {
                                    type = NavType.FloatType
                                }
                            )
                        ) {

                            ShoeDetailScreen(
                                it.arguments?.getString("name", "").orEmpty(),
                                it.arguments?.getFloat("rotation", 0f),
                                this, navController)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        ComposeAssignmentTheme {
            Greeting("Android")
        }
    }

}