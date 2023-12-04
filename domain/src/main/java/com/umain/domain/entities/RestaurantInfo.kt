package com.umain.domain.entities

data class RestaurantInfo(
    val filter: List<FilterInfo> = listOf(),
    val restaurants: List<Restaurant> = listOf()
)
