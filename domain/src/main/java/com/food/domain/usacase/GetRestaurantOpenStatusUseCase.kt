package com.food.domain.usacase

import com.food.domain.ResponseState
import com.food.domain.entities.RestaurantOpenStatus
import com.food.domain.repositories.IFoodDeliveryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRestaurantOpenStatusUseCase @Inject constructor(private val foodDeliveryRepository: IFoodDeliveryRepository) {

    suspend operator fun invoke(restaurantId: String): Flow<ResponseState<RestaurantOpenStatus>> {
        return flow {
            try {
                emit(ResponseState.Loading())
                emit(
                    ResponseState.Success(
                        foodDeliveryRepository.getRestaurantOpenStatus(restaurantId)
                    )
                )

            } catch (e: Exception) {
                emit(ResponseState.Error(e))
            }
        }
    }

}