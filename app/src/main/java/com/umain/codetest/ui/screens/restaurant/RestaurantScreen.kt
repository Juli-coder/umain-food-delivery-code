package com.umain.codetest.ui.screens.restaurant

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.umain.codetest.R
import com.umain.codetest.ui.theme.AppTheme
import com.umain.codetest.utils.UiState
import com.umain.domain.entities.FilterInfo
import com.umain.domain.entities.Restaurant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantScreen(
    viewData: RestaurantViewModel.ViewData,
    onEvent: (RestaurantViewModel.Event) -> Unit,
    onRestaurantSelect: (Restaurant) -> Unit
) {
    Scaffold(
        topBar = {
            Image(
                painter = painterResource(R.drawable.icon),
                contentDescription = null,
                modifier = Modifier
                    .size(70.dp)
                    .padding(15.dp)
            )
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            when (viewData.state) {
                UiState.LOADING -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                UiState.LOADED -> {
                    Column {
                        FilterComponent(
                            filters = viewData.filters,
                            onClick = { clickedFilterId ->
                                onEvent(RestaurantViewModel.Event.FilterClicked(clickedFilterId))
                            }
                        )
                        if (viewData.restaurants.isEmpty()) {
                            Text(
                                text = stringResource(R.string.no_restaurants_found),
                                modifier = Modifier.align(CenterHorizontally).padding(16.dp),
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
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Composable
fun FilterComponent(filters: List<FilterInfo>, onClick: (String) -> Unit) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 15.dp)
    ) {
        items(
            items = filters,
            key = { it.id }) { item ->
            FilterItem(
                filterInfo = item,
                onClick = onClick
            )
        }
    }
}

@Composable
fun RestaurantComponent(restaurants: List<Restaurant>, onRestaurantSelect: (Restaurant) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 10.dp),
    ) {
        items(
            items = restaurants,
            key = { it.id }
        ) { item ->
            RestaurantItem(
                restaurant = item,
                onClick = onRestaurantSelect
            )
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