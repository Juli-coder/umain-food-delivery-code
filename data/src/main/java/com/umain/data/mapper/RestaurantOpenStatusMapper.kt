package com.umain.data.mapper

import com.app.data.base.Mapper
import com.umain.data.network.entities.RestaurantOpenStatusDTO
import com.umain.domain.entities.RestaurantOpenStatus

class RestaurantOpenStatusMapper : Mapper<RestaurantOpenStatusDTO, RestaurantOpenStatus> {
    override fun map(input: RestaurantOpenStatusDTO): RestaurantOpenStatus {
        return RestaurantOpenStatus(input.isCurrentlyOpen ?: false, input.restaurantId.orEmpty())
    }
}