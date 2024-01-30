package com.food.data.di

import com.food.data.mapper.FilterMapper
import com.food.data.mapper.RestaurantMapper
import com.food.data.mapper.RestaurantOpenStatusMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Provides
    fun provideRestaurantMapper(): RestaurantMapper {
        return RestaurantMapper()
    }

    @Provides
    fun provideFilterMapper(): FilterMapper {
        return FilterMapper()
    }

    @Provides
    fun provideRestaurantOpenStatusMapper(): RestaurantOpenStatusMapper {
        return RestaurantOpenStatusMapper()
    }
}