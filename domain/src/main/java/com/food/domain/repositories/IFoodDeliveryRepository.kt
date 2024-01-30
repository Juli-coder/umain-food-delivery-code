package com.food.domain.repositories

import com.food.domain.entities.FilterInfo
import com.food.domain.entities.Restaurant
import com.food.domain.entities.RestaurantOpenStatus

interface IFoodDeliveryRepository {
    suspend fun getFilterById(id: String): FilterInfo
    suspend fun getRestaurant(): List<Restaurant>
    suspend fun getRestaurantOpenStatus(restaurantId:String): RestaurantOpenStatus
}