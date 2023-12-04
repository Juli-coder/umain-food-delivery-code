package com.umain.data.network.entities

import com.google.gson.annotations.SerializedName

data class RestaurantOpenStatusDTO(
    @SerializedName("is_currently_open")
    val isCurrentlyOpen: Boolean?,
    @SerializedName("restaurant_id")
    val restaurantId: String?
)