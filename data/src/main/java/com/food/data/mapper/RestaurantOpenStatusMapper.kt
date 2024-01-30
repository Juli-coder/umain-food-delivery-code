package com.food.data.mapper

import com.app.data.base.Mapper
import com.food.data.network.entities.RestaurantOpenStatusDTO
import com.food.domain.entities.RestaurantOpenStatus

class RestaurantOpenStatusMapper : Mapper<RestaurantOpenStatusDTO, RestaurantOpenStatus> {
    override fun map(input: RestaurantOpenStatusDTO): RestaurantOpenStatus {
        return RestaurantOpenStatus(input.isCurrentlyOpen ?: false, input.restaurantId.orEmpty())
    }
}