package com.umain.data.di

import com.umain.data.mapper.FilterMapper
import com.umain.data.mapper.RestaurantMapper
import com.umain.data.mapper.RestaurantOpenStatusMapper
import com.umain.data.network.FoodDeliveryApi
import com.umain.data.repositories.FoodDeliveryRepositoryImpl
import com.umain.domain.repositories.IFoodDeliveryRepository
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