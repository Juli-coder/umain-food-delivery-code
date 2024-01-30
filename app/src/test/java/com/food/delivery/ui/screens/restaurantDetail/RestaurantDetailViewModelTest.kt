package com.food.delivery.ui.screens.restaurantDetail

import app.cash.turbine.test
import com.food.delivery.utils.UiState
import com.food.domain.ResponseState
import com.food.domain.entities.Restaurant
import com.food.domain.entities.RestaurantOpenStatus
import com.food.domain.usacase.GetRestaurantOpenStatusUseCase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class RestaurantDetailViewModelTest {

    @Mock
    lateinit var getRestaurantOpenStatusUseCase: GetRestaurantOpenStatusUseCase

    private lateinit var viewModel: RestaurantDetailViewModel

    private val testDispatchers = StandardTestDispatcher()

    private val viewData = RestaurantDetailViewModel.ViewData()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatchers)
        viewModel = RestaurantDetailViewModel(getRestaurantOpenStatusUseCase)
    }

    @Test
    fun testViewModelWithDefaultValue() {
        assertEquals(viewData.state, viewModel.viewData.value.state)
        assertEquals(viewData.errorMessage, viewModel.viewData.value.errorMessage)
        assertEquals(viewData.openStatus, viewModel.viewData.value.openStatus)
    }


    @Test
    fun `when getRestaurantOpenStatus success, then populate viewData with open status`() {
        runTest {
            val restaurant = Restaurant(id = "1")

            val fakeData = getFakeOpenStatus()
            `when`(getRestaurantOpenStatusUseCase(restaurant.id)).thenReturn(
                flowOf(
                    ResponseState.Loading(),
                    ResponseState.Success(fakeData)
                )
            )

            viewModel.getRestaurantOpenStatus(restaurant)

            viewModel.viewData.test {
                var emittedItem = awaitItem()
                assertEquals(UiState.LOADING, emittedItem.state)

                emittedItem = awaitItem()
                assertEquals(UiState.LOADED, emittedItem.state)
                assertEquals(
                    fakeData.isCurrentlyOpen,
                    emittedItem.openStatus.isCurrentlyOpen
                )
                assertEquals(fakeData.restaurantId, emittedItem.openStatus.restaurantId)
                cancelAndIgnoreRemainingEvents()
            }

        }
    }

    @Test
    fun `when getRestaurantOpenStatus Fails, then populate viewData with error state`() {
        runTest {
            val fakeFailureMsg = "Fake Failure"

            val restaurant = Restaurant(id = "1")

            `when`(getRestaurantOpenStatusUseCase(restaurant.id)).thenReturn(
                flowOf(
                    ResponseState.Loading(),
                    ResponseState.Error(Throwable(fakeFailureMsg))
                )
            )

            viewModel.getRestaurantOpenStatus(restaurant)

            viewModel.viewData.test {
                var emittedItem = awaitItem()
                assertEquals(UiState.LOADING, emittedItem.state)

                emittedItem = awaitItem()
                assertEquals(UiState.ERROR, emittedItem.state)

                assertEquals(fakeFailureMsg, emittedItem.errorMessage)
                cancelAndIgnoreRemainingEvents()
            }

        }
    }


    private fun getFakeOpenStatus(): RestaurantOpenStatus {
        return RestaurantOpenStatus(
            isCurrentlyOpen = false,
            restaurantId = "1"
        )
    }

    @OptIn(DelicateCoroutinesApi::class)
    @After
    fun close() {
        Dispatchers.shutdown()
    }
}