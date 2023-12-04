package com.umain.codetest.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideIn
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.umain.codetest.ui.theme.AppTheme
import com.umain.codetest.ui.screens.restaurant.RestaurantScreen
import com.umain.codetest.ui.screens.restaurant.RestaurantViewModel
import com.umain.codetest.ui.screens.restaurantDetail.RestaurantDetailScreen
import com.umain.codetest.ui.screens.restaurantDetail.RestaurantDetailViewModel
import com.umain.domain.entities.Restaurant
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var gson: Gson

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Routes.RESTAURANT_LIST
                ) {
                    composable(route = Routes.RESTAURANT_LIST) {
                        val viewModel = hiltViewModel<RestaurantViewModel>()
                        val viewState = viewModel.viewData.collectAsState()

                        RestaurantScreen(
                            viewData = viewState.value,
                            onEvent = { viewModel.onEvent(it) },
                            onRestaurantSelect = { restaurant ->
                                val json = gson.toJson(restaurant)
                                val route = Routes.RESTAURANT_DETAIL.replace("{detail}", json)
                                navController.navigate(route)
                            })
                    }

                    composable(
                        route = Routes.RESTAURANT_DETAIL,
                        enterTransition = {
                            slideIntoContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Up,
                                animationSpec = tween(700)
                            )
                        },
                        exitTransition = {
                            slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Down,
                                animationSpec = tween(700)
                            )
                        }
                    ) {
                        val jsonDetail = it.arguments?.getString("detail").orEmpty()
                        val restaurant = gson.fromJson(jsonDetail, Restaurant::class.java)
                        val viewModel = hiltViewModel<RestaurantDetailViewModel>()
                        RestaurantDetailScreen(restaurant, viewModel, onBackPress = {
                            navController.popBackStack()
                        })
                    }
                }
            }
        }
    }
}