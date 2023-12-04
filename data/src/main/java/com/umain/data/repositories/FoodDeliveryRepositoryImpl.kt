package com.umain.data.repositories

import com.umain.data.mapper.FilterMapper
import com.umain.data.mapper.RestaurantMapper
import com.umain.data.mapper.RestaurantOpenStatusMapper
import com.umain.data.network.FoodDeliveryApi
import com.umain.domain.ResponseState
import com.umain.domain.entities.FilterInfo
import com.umain.domain.entities.Restaurant
import com.umain.domain.entities.RestaurantOpenStatus
import com.umain.domain.repositories.IFoodDeliveryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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