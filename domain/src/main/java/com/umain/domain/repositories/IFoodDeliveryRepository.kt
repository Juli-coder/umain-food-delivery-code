package com.umain.domain.repositories

import com.umain.domain.entities.FilterInfo
import com.umain.domain.entities.Restaurant
import com.umain.domain.entities.RestaurantOpenStatus

interface IFoodDeliveryRepository {
    suspend fun getFilterById(id: String): FilterInfo
    suspend fun getRestaurant(): List<Restaurant>
    suspend fun getRestaurantOpenStatus(restaurantId:String): RestaurantOpenStatus
}