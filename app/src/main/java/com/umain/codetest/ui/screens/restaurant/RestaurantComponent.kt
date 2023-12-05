package com.umain.codetest.ui.screens.restaurant

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.umain.domain.entities.Restaurant

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