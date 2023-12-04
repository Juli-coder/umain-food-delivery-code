package com.umain.domain.usacase

import app.cash.turbine.test
import com.umain.domain.ResponseState
import com.umain.domain.entities.RestaurantOpenStatus
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


class GetRestaurantOpenStatusUseCaseTest {

    @Mock
    private lateinit var repository: IFoodDeliveryRepository

    private lateinit var useCaseToTest: GetRestaurantOpenStatusUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        useCaseToTest = GetRestaurantOpenStatusUseCase(repository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testRestaurantStatusSuccessFlow() {
        runTest {
            val restaurantId = "123"
            `when`(repository.getRestaurantOpenStatus(anyString())).then {
                val restaurantId = it.getArgument<String>(0)
                getFakeRestaurantStatus(restaurantId)
            }

            val flow = useCaseToTest.invoke(restaurantId)

            flow.test {
                var emittedItem = awaitItem()
                assertEquals(true, emittedItem is ResponseState.Loading)

                emittedItem = awaitItem()
                assertEquals(true, emittedItem is ResponseState.Success)
                emittedItem = emittedItem as ResponseState.Success
                assertEquals(restaurantId, emittedItem.data.restaurantId)

                cancelAndIgnoreRemainingEvents()
            }

            verify(repository, times(1)).getRestaurantOpenStatus(anyString())
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testRestaurantOpenStatusFailureFlow() {
        runTest {
            val fakeRestaurantId = "123"
            val fakeThrowable = Exception("Something went wrong")
            `when`(repository.getRestaurantOpenStatus(anyString())).thenAnswer {
                throw fakeThrowable
            }
            val flow = useCaseToTest.invoke(fakeRestaurantId)

            flow.test {
                var emittedItem = awaitItem()
                assertEquals(true, emittedItem is ResponseState.Loading)

                emittedItem = awaitItem()
                assertEquals(true, emittedItem is ResponseState.Error)
                emittedItem = emittedItem as ResponseState.Error
                assertEquals(fakeThrowable.message, emittedItem.throwable.message)

                cancelAndIgnoreRemainingEvents()
            }

            verify(repository, times(1)).getRestaurantOpenStatus(anyString())

        }
    }

    private fun getFakeRestaurantStatus(id: String) = RestaurantOpenStatus(
        restaurantId = id,
        isCurrentlyOpen = false
    )
}