package com.food.data.di

import com.food.data.mapper.FilterMapper
import com.food.data.mapper.RestaurantMapper
import com.food.data.mapper.RestaurantOpenStatusMapper
import com.food.data.network.FoodDeliveryApi
import com.food.data.repositories.FoodDeliveryRepositoryImpl
import com.food.domain.repositories.IFoodDeliveryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideFoodDeliveryRepository(
        foodDeliveryApi: FoodDeliveryApi,
        restaurantMapper: RestaurantMapper,
        filterMapper: FilterMapper,
        restaurantOpenStatusMapper: RestaurantOpenStatusMapper
    ): IFoodDeliveryRepository {
        return FoodDeliveryRepositoryImpl(
            foodDeliveryApi,
            restaurantMapper,
            filterMapper,
            restaurantOpenStatusMapper
        )
    }
}