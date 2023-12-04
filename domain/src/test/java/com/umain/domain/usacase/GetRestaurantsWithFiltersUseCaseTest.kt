package com.umain.domain.usacase

import app.cash.turbine.test
import com.umain.domain.ResponseState
import com.umain.domain.entities.FilterInfo
import com.umain.domain.entities.Restaurant
import com.umain.domain.repositories.IFoodDeliveryRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.anyString
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


class GetRestaurantsWithFiltersUseCaseTest {

    @Mock
    private lateinit var repository: IFoodDeliveryRepository

    private lateinit var useCaseToTest: GetRestaurantsWithFiltersUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        useCaseToTest = GetRestaurantsWithFiltersUseCase(repository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testRestaurantWithFilterSuccessFlow() {
        runTest {
            val fakeRestaurants = getFakeRestaurants()
            val uniqueFiltersSize = 4 //Based on the fakeRestaurants
            `when`(repository.getRestaurant()).thenReturn(fakeRestaurants)
            `when`(repository.getFilterById(anyString())).then {
                val filterId = it.getArgument<String>(0)
                getFilterInfo(filterId)
            }

            val flow = useCaseToTest.invoke()

            flow.test {
                var emittedItem = awaitItem()
                assertEquals(true, emittedItem is ResponseState.Loading)

                emittedItem = awaitItem()
                assertEquals(true, emittedItem is ResponseState.Success)
                emittedItem = emittedItem as ResponseState.Success
                assertEquals(fakeRestaurants.size, emittedItem.data.restaurants.size)
                assertEquals(uniqueFiltersSize, emittedItem.data.filter.size)

                cancelAndIgnoreRemainingEvents()
            }

            verify(repository, times(1)).getRestaurant()
            verify(repository, times(uniqueFiltersSize)).getFilterById(anyString())

        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testRestaurantWithFilterFailureFlow() {
        runTest {
            val fakeThrowable = Exception("Something went wrong")
            `when`(repository.getRestaurant()).thenThrow(fakeThrowable)
            `when`(repository.getFilterById(anyString())).then {
                val filterId = it.getArgument<String>(0)
                getFilterInfo(filterId)
            }

            val flow = useCaseToTest.invoke()

            flow.test {
                var emittedItem = awaitItem()
                assertEquals(true, emittedItem is ResponseState.Loading)

                emittedItem = awaitItem()
                assertEquals(true, emittedItem is ResponseState.Error)
                emittedItem = emittedItem as ResponseState.Error
                assertEquals(fakeThrowable.message, emittedItem.throwable.message)

                cancelAndIgnoreRemainingEvents()
            }

            verify(repository, times(1)).getRestaurant()

        }
    }

    private fun getFakeRestaurants() = listOf(
        Restaurant(id = "1", filterIds = listOf("1", "2", "3")),
        Restaurant(id = "2", filterIds = listOf("1", "3")),
        Restaurant(id = "3", filterIds = listOf("4")),
        Restaurant(id = "4", filterIds = listOf("1", "2", "3")),
        Restaurant(id = "5", filterIds = listOf("1", "2")),
        Restaurant(id = "6", filterIds = listOf()),
    )

    private fun getFilterInfo(id: String) = FilterInfo(id)

}