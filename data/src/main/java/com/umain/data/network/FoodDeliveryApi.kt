package com.umain.data.network

import com.umain.data.network.entities.FilterDTO
import com.umain.data.network.entities.RestaurantsDTO
import com.umain.data.network.entities.RestaurantOpenStatusDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface FoodDeliveryApi {

    @GET("filter/{id}")
    suspend fun getFilter(@Path("id") id: String): FilterDTO

    @GET("restaurants")
    suspend fun getRestaurants(): RestaurantsDTO

    @GET("open/{id}")
    suspend fun getOpenStatus(@Path("id") id: String): RestaurantOpenStatusDTO
}