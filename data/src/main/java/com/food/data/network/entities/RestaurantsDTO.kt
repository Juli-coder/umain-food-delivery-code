package com.food.data.network.entities

import com.google.gson.annotations.SerializedName

data class RestaurantsDTO(
    val restaurants: List<RestaurantDTO>?
)

data class RestaurantDTO(

    @SerializedName("delivery_time_minutes")
    val deliveryTimeMinutes: Int?,

    @SerializedName("filterIds")
    val filterIds: List<String>?,

    @SerializedName("id")
    val id: String?,

    @SerializedName("image_url")
    val imageUrl: String?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("rating")
    val rating: Float?,
)