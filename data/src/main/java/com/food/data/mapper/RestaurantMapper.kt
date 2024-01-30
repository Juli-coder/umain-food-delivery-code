package com.food.data.mapper

import com.app.data.base.Mapper
import com.food.data.network.entities.RestaurantsDTO
import com.food.domain.entities.Restaurant

class RestaurantMapper : Mapper<RestaurantsDTO, List<Restaurant>> {
    override fun map(input: RestaurantsDTO): List<Restaurant> {
        return input.restaurants?.map { restaurant ->
            Restaurant(
                restaurant.deliveryTimeMinutes ?: 0,
                restaurant.filterIds.orEmpty(),
                restaurant.id.orEmpty(),
                restaurant.imageUrl.orEmpty(),
                restaurant.name.orEmpty(),
                restaurant.rating ?: 0.0f,
            )
        }.orEmpty()
    }
}