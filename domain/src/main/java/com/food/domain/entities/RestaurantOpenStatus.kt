package com.food.domain.entities

data class RestaurantOpenStatus(
    val isCurrentlyOpen: Boolean = false,
    val restaurantId: String = ""
)