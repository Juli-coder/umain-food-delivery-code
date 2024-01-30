package com.food.domain.usacase

import com.food.domain.ResponseState
import com.food.domain.entities.FilterInfo
import com.food.domain.entities.RestaurantInfo
import com.food.domain.repositories.IFoodDeliveryRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
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

                val allFilters = mutableListOf<Deferred<FilterInfo>>()

                coroutineScope {
                    filterIds.forEach { filterId ->
                        val filters = async {
                            foodDeliveryRepository.getFilterById(filterId)
                        }
                        allFilters.add(filters)
                    }
                }

                val filtersList = allFilters.awaitAll()

                restaurants.forEach { restaurant ->
                    restaurant.filterNames = filtersList.filter { filterInfo ->
                        restaurant.filterIds.contains(filterInfo.id)
                    }.map { it.name }

                }

                val restaurantInfo = RestaurantInfo(filtersList, restaurants)
                emit(ResponseState.Success(restaurantInfo))

            } catch (e: Exception) {
                emit(ResponseState.Error(e))
            }
        }
    }
}