package com.food.delivery.ui.screens.restaurantDetail

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.food.delivery.R
import com.food.delivery.ui.theme.AppTheme
import com.food.delivery.utils.UiState
import com.food.domain.entities.Restaurant
import com.food.domain.entities.RestaurantOpenStatus
import org.junit.Rule
import org.junit.Test

class RestaurantDetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext


    private fun initView(fakeViewData: RestaurantDetailViewModel.ViewData) {
        val fakeRestaurant = Restaurant()
        composeTestRule.setContent {
            AppTheme {
                RestaurantDetailComponent(fakeRestaurant, fakeViewData, onBackPress = {})
            }
        }
    }

    @Test
    fun showLoaderWhenFetchingRestaurantOpenStatus_RestaurantComponent() {
        initView(RestaurantDetailViewModel.ViewData(state = UiState.LOADING))

        composeTestRule.onNodeWithTag("openStatusLoader").assertIsDisplayed()
        composeTestRule.onNodeWithTag("restaurantOpenStatus").assertDoesNotExist()
    }

    @Test
    fun showRestaurantOpenStatus() {
        initView(
            RestaurantDetailViewModel.ViewData(
                state = UiState.LOADED,
                openStatus = RestaurantOpenStatus(true, "")
            )
        )
        composeTestRule.onNodeWithTag("openStatusLoader").assertDoesNotExist()
        composeTestRule.onNodeWithTag("restaurantOpenStatus").assertIsDisplayed()
        composeTestRule.onNodeWithTag("restaurantOpenStatus")
            .assertTextEquals(context.getString(R.string.restaurant_status_open))

    }

    @Test
    fun showRestaurantCloseStatus() {
        initView(
            RestaurantDetailViewModel.ViewData(
                state = UiState.LOADED,
                openStatus = RestaurantOpenStatus(false, "")
            )
        )

        composeTestRule.onNodeWithTag("openStatusLoader").assertDoesNotExist()
        composeTestRule.onNodeWithTag("restaurantOpenStatus").assertIsDisplayed()
        composeTestRule.onNodeWithTag("restaurantOpenStatus")
            .assertTextEquals(context.getString(R.string.restaurant_status_close))

    }

    @Test
    fun showRestaurantFailStatus() {
        initView(RestaurantDetailViewModel.ViewData(state = UiState.ERROR))

        composeTestRule.onNodeWithTag("openStatusLoader").assertDoesNotExist()
        composeTestRule.onNodeWithTag("restaurantFailStatus").assertIsDisplayed()
        composeTestRule.onNodeWithTag("restaurantFailStatus")
            .assertTextEquals(context.getString(R.string.failed_to_load_status))

    }

    @Test
    fun performBackClickShouldTrigger() {
        val fakeRestaurant = Restaurant()
        val fakeViewData = RestaurantDetailViewModel.ViewData(state = UiState.ERROR)
        var onBackPressTrigger = false
        composeTestRule.setContent {
            AppTheme {
                RestaurantDetailComponent(
                    fakeRestaurant,
                    fakeViewData,
                    onBackPress = { onBackPressTrigger = true })
            }
        }

        composeTestRule.onNodeWithTag("backButton").performClick()
        assert(onBackPressTrigger)
    }
}