package com.umain.data.di

import com.umain.data.mapper.FilterMapper
import com.umain.data.mapper.RestaurantMapper
import com.umain.data.mapper.RestaurantOpenStatusMapper
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