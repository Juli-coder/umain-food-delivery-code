package com.umain.data.repositories

import com.umain.data.mapper.FilterMapper
import com.umain.data.mapper.RestaurantMapper
import com.umain.data.mapper.RestaurantOpenStatusMapper
import com.umain.data.network.FoodDeliveryApi
import com.umain.data.network.entities.FilterDTO
import com.umain.data.network.entities.RestaurantDTO
import com.umain.data.network.entities.RestaurantOpenStatusDTO
import com.umain.data.network.entities.RestaurantsDTO
import com.umain.domain.entities.FilterInfo
import com.umain.domain.entities.Restaurant
import com.umain.domain.entities.RestaurantOpenStatus
import com.umain.domain.repositories.IFoodDeliveryRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.anyString
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class FoodDeliveryRepositoryImplTest {

    @Mock
    private lateinit var foodDeliveryApi: FoodDeliveryApi

    @Mock
    private lateinit var restaurantMapper: RestaurantMapper

    @Mock
    private lateinit var filterMapper: FilterMapper

    @Mock
    private lateinit var restaurantOpenStatusMapper: RestaurantOpenStatusMapper

    private lateinit var repositoryImplTest: IFoodDeliveryRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repositoryImplTest = FoodDeliveryRepositoryImpl(
            foodDeliveryApi,
            restaurantMapper,
            filterMapper,
            restaurantOpenStatusMapper
        )
    }

    @Test
    fun getFilterById() {
        runTest {
            val filterId = "123"
            val filterDTO = FilterDTO(filterId, "", "")
            val filterInfo = FilterInfo(id = filterId)
            `when`(foodDeliveryApi.getFilter(anyString())).then { filterDTO }
            `when`(filterMapper.map(filterDTO)).then { filterInfo }

            val result = repositoryImplTest.getFilterById(filterId)
            assertEquals(result, filterInfo)
            verify(filterMapper).map(filterDTO)
        }
    }

    @Test
    fun getRestaurant() {
        runTest {
            val restaurantDTO = RestaurantsDTO(
                listOf(
                    RestaurantDTO(0, listOf(), "1", "", "", 0f),
                    RestaurantDTO(0, listOf(), "2", "", "", 0f),
                    RestaurantDTO(0, listOf(), "3", "", "", 0f),
                )
            )
            val restaurants = listOf(
                Restaurant(0, listOf(), "1", "", "", 0f),
                Restaurant(0, listOf(), "2", "", "", 0f),
                Restaurant(0, listOf(), "3", "", "", 0f),
            )
            `when`(foodDeliveryApi.getRestaurants()).then { restaurantDTO }
            `when`(restaurantMapper.map(restaurantDTO)).then { restaurants }

            val result = repositoryImplTest.getRestaurant()
            assertEquals(result, restaurants)
            verify(restaurantMapper).map(restaurantDTO)
        }
    }

    @Test
    fun getRestaurantOpenStatus() {
        runTest {
            val restaurantStatusDTO = RestaurantOpenStatusDTO(false, "1")
            val restaurantStatus = RestaurantOpenStatus(false, "1")
            `when`(foodDeliveryApi.getOpenStatus(anyString())).then { restaurantStatusDTO }
            `when`(restaurantOpenStatusMapper.map(restaurantStatusDTO)).then { restaurantStatus }

            val result = repositoryImplTest.getRestaurantOpenStatus("1")
            assertEquals(result, restaurantStatus)
            verify(restaurantOpenStatusMapper).map(restaurantStatusDTO)
        }
    }
}