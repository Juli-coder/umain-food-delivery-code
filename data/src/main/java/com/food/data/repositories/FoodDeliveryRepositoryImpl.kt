package com.food.data.repositories

import com.food.data.mapper.FilterMapper
import com.food.data.mapper.RestaurantMapper
import com.food.data.mapper.RestaurantOpenStatusMapper
import com.food.data.network.FoodDeliveryApi
import com.food.domain.entities.FilterInfo
import com.food.domain.entities.Restaurant
import com.food.domain.entities.RestaurantOpenStatus
import com.food.domain.repositories.IFoodDeliveryRepository
import javax.inject.Inject

class FoodDeliveryRepositoryImpl @Inject constructor(
    private val foodDeliveryApi: FoodDeliveryApi,
    private val restaurantMapper: RestaurantMapper,
    private val filterMapper: FilterMapper,
    private val restaurantOpenStatusMapper: RestaurantOpenStatusMapper
) : IFoodDeliveryRepository {

    override suspend fun getFilterById(id: String): FilterInfo {
        return filterMapper.map(foodDeliveryApi.getFilter(id))
    }

    override suspend fun getRestaurant(): List<Restaurant> {
        return restaurantMapper.map(foodDeliveryApi.getRestaurants())
    }

    override suspend fun getRestaurantOpenStatus(restaurantId: String): RestaurantOpenStatus {
        return restaurantOpenStatusMapper.map(foodDeliveryApi.getOpenStatus(restaurantId))
    }
}