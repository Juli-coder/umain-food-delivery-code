package com.food.delivery.ui.screens.restaurant

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.food.delivery.R
import com.food.delivery.ui.theme.AppTheme
import com.food.delivery.utils.UiState
import com.food.domain.entities.Restaurant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantScreen(
    viewData: RestaurantViewModel.ViewData,
    onEvent: (RestaurantViewModel.Event) -> Unit,
    onRestaurantSelect: (Restaurant) -> Unit
) {
    Scaffold{
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            when (viewData.state) {
                UiState.LOADING -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center).testTag("viewLoader")
                    )
                }

                UiState.LOADED -> {
                    Column(modifier = Modifier.fillMaxSize().testTag("successView")) {
                        FilterComponent(
                            filters = viewData.filters,
                            onClick = { clickedFilterId ->
                                onEvent(RestaurantViewModel.Event.FilterClicked(clickedFilterId))
                            }
                        )
                        if (viewData.restaurants.isEmpty()) {
                            Text(
                                text = stringResource(R.string.no_restaurants_found),
                                modifier = Modifier.align(CenterHorizontally).padding(16.dp)
                                    .testTag("noRestaurant"),
                            )
                        } else {
                            RestaurantComponent(
                                restaurants = viewData.restaurants,
                                onRestaurantSelect = onRestaurantSelect
                            )
                        }
                    }
                }

                UiState.ERROR -> {
                    Text(
                        text = stringResource(R.string.something_went_wrong),
                        modifier = Modifier.align(Alignment.Center).testTag("errorView")
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun RestaurantScreenPreview() {
    AppTheme {
        RestaurantScreen(
            viewData = RestaurantViewModel.ViewData(state = UiState.LOADED),
            onEvent = {},
            onRestaurantSelect = {}
        )
    }
}