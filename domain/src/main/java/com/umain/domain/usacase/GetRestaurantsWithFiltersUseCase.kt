package com.umain.domain.usacase

import com.umain.domain.ResponseState
import com.umain.domain.entities.FilterInfo
import com.umain.domain.entities.RestaurantInfo
import com.umain.domain.repositories.IFoodDeliveryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRestaurantsWithFiltersUseCase @Inject constructor(
    private val foodDeliveryRepository: IFoodDeliveryRepository
) {

    suspend operator fun invoke(): Flow<ResponseState<RestaurantInfo>> {
        return flow {
            try {
                emit(ResponseState.Loading())
                val restaurants = foodDeliveryRepository.getRestaurant()

                val filterIds = restaurants.map { restaurant ->
                    restaurant.filterIds
                }.flatten().distinct()

                val allFilters = mutableListOf<FilterInfo>()

                filterIds.forEach { filterId ->
                    allFilters.add(foodDeliveryRepository.getFilterById(filterId))
                }

                restaurants.forEach { restaurant ->

                    restaurant.filterNames = allFilters.filter { filterInfo ->
                        restaurant.filterIds.contains(filterInfo.id)
                    }.map { it.name }

                }

                val restaurantInfo = RestaurantInfo(allFilters, restaurants)
                emit(ResponseState.Success(restaurantInfo))

            } catch (e: Exception) {
                emit(ResponseState.Error(e))
            }
        }
    }
}