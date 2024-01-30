package com.food.delivery.ui.screens.restaurant

import app.cash.turbine.test
import com.food.delivery.utils.UiState
import com.food.domain.ResponseState
import com.food.domain.entities.FilterInfo
import com.food.domain.entities.Restaurant
import com.food.domain.entities.RestaurantInfo
import com.food.domain.usacase.GetRestaurantsWithFiltersUseCase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class RestaurantViewModelTest {

    @Mock
    lateinit var getRestaurantsWithFiltersUseCase: GetRestaurantsWithFiltersUseCase

    private lateinit var viewModel: RestaurantViewModel

    private val testDispatchers = StandardTestDispatcher()

    private val viewData = RestaurantViewModel.ViewData()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatchers)
        viewModel = RestaurantViewModel(getRestaurantsWithFiltersUseCase)
    }


    @Test
    fun testViewModelWithDefaultData() {
        Assert.assertEquals(viewData.state, viewModel.viewData.value.state)
        Assert.assertEquals(viewData.errorMessage, viewModel.viewData.value.errorMessage)
        Assert.assertEquals(viewData.filters.size, viewModel.viewData.value.filters.size)
        Assert.assertEquals(viewData.restaurants.size, viewModel.viewData.value.restaurants.size)
    }

    @Test
    fun `when getRestaurant success, then populate viewData and allRestaurants`() {
        runTest {
            val fakeData = getFakeRestaurantInfo()
            `when`(getRestaurantsWithFiltersUseCase()).thenReturn(
                flowOf(
                    ResponseState.Loading(),
                    ResponseState.Success(fakeData)
                )
            )

            viewModel.viewData.test {
                var emittedItem = awaitItem()
                Assert.assertEquals(UiState.LOADING, emittedItem.state)

                emittedItem = awaitItem()
                Assert.assertEquals(UiState.LOADED, emittedItem.state)
                Assert.assertEquals(fakeData.restaurants.size, emittedItem.restaurants.size)
                Assert.assertEquals(fakeData.filter.size, emittedItem.filters.size)
                Assert.assertEquals(viewModel.allRestaurants.size, emittedItem.restaurants.size)
                //it will not wait for other event and exit the testcase.
                cancelAndIgnoreRemainingEvents()
            }
        }
    }


    @Test
    fun `when getRestaurant fails, then populate viewData with Error state`() {
        runTest {
            val fakeFailureMsg = "Fake Failure"
            `when`(getRestaurantsWithFiltersUseCase()).thenReturn(
                flowOf(
                    ResponseState.Loading(),
                    ResponseState.Error(Throwable(fakeFailureMsg))
                )
            )

            viewModel.viewData.test {
                var emittedItem = awaitItem()
                Assert.assertEquals(UiState.LOADING, emittedItem.state)

                emittedItem = awaitItem()
                Assert.assertEquals(UiState.ERROR, emittedItem.state)
                Assert.assertEquals(fakeFailureMsg, emittedItem.errorMessage)

                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `when FilterCLick, then populate viewData with filtered Restaurants`() {
        runTest {
            val fakeData = getFakeRestaurantInfo()
            `when`(getRestaurantsWithFiltersUseCase()).thenReturn(
                flowOf(
                    ResponseState.Loading(),
                    ResponseState.Success(fakeData)
                )
            )

            viewModel.viewData.test {

                var emittedItem = awaitItem()
                Assert.assertEquals(UiState.LOADING, emittedItem.state)
                emittedItem = awaitItem()
                Assert.assertEquals(UiState.LOADED, emittedItem.state)
                Assert.assertEquals(fakeData.restaurants.size, emittedItem.restaurants.size)
                Assert.assertEquals(fakeData.filter.size, emittedItem.filters.size)
                Assert.assertEquals(viewModel.allRestaurants.size, emittedItem.restaurants.size)

                viewModel.onEvent(RestaurantViewModel.Event.FilterClicked("1"))

                emittedItem = awaitItem()
                val clickedFilter = emittedItem.filters.firstOrNull { it.id == "1" }
                Assert.assertEquals(true, clickedFilter?.isSelected)
                Assert.assertEquals(4, emittedItem.restaurants.size)

                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `when no selected filters, then populate viewData with all Restaurants`() {
        runTest {
            val fakeData = getFakeRestaurantInfo()
            fakeData.filter[0].isSelected = true

            `when`(getRestaurantsWithFiltersUseCase()).thenReturn(
                flowOf(
                    ResponseState.Loading(),
                    ResponseState.Success(fakeData)
                )
            )

            viewModel.viewData.test {

                var emittedItem = awaitItem()
                Assert.assertEquals(UiState.LOADING, emittedItem.state)
                emittedItem = awaitItem()
                Assert.assertEquals(UiState.LOADED, emittedItem.state)
                Assert.assertEquals(fakeData.restaurants.size, emittedItem.restaurants.size)
                Assert.assertEquals(fakeData.filter.size, emittedItem.filters.size)
                Assert.assertEquals(viewModel.allRestaurants.size, emittedItem.restaurants.size)

                //Deselecting selected filter to trigger update in viewData
                viewModel.onEvent(RestaurantViewModel.Event.FilterClicked("1"))

                emittedItem = awaitItem()
                Assert.assertEquals(fakeData.restaurants.size, emittedItem.restaurants.size)

                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `when selected only 1 filters, then populate viewData with restaurant having specific filter`() {
        runTest {
            val fakeData = getFakeRestaurantInfo()

            `when`(getRestaurantsWithFiltersUseCase()).thenReturn(
                flowOf(
                    ResponseState.Loading(),
                    ResponseState.Success(fakeData)
                )
            )

            viewModel.viewData.test {

                var emittedItem = awaitItem()
                Assert.assertEquals(UiState.LOADING, emittedItem.state)
                emittedItem = awaitItem()
                Assert.assertEquals(UiState.LOADED, emittedItem.state)
                Assert.assertEquals(fakeData.restaurants.size, emittedItem.restaurants.size)
                Assert.assertEquals(fakeData.filter.size, emittedItem.filters.size)
                Assert.assertEquals(viewModel.allRestaurants.size, emittedItem.restaurants.size)

                viewModel.onEvent(RestaurantViewModel.Event.FilterClicked("4"))

                emittedItem = awaitItem()
                //it will return only one filtered restaurant base on fake data
                Assert.assertEquals(1, emittedItem.restaurants.size)

                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    private fun getFakeRestaurantInfo(): RestaurantInfo {

        val filters = listOf(
            FilterInfo("1"),
            FilterInfo("2"),
            FilterInfo("3"),
            FilterInfo("4"),
        )

        val restaurants = listOf(
            Restaurant(id = "1", filterIds = listOf("1", "2", "3")),
            Restaurant(id = "2", filterIds = listOf("1", "3")),
            Restaurant(id = "3", filterIds = listOf("4")),
            Restaurant(id = "4", filterIds = listOf("1", "2", "3")),
            Restaurant(id = "5", filterIds = listOf("1", "2")),
            Restaurant(id = "6", filterIds = listOf()),
        )

        return RestaurantInfo(filters, restaurants)
    }


    @OptIn(DelicateCoroutinesApi::class)
    @After
    fun close() {
        Dispatchers.shutdown()
    }
}