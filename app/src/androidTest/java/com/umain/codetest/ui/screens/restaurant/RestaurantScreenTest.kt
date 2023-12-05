package com.umain.codetest.ui.screens.restaurant

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.platform.app.InstrumentationRegistry
import com.umain.codetest.R
import com.umain.codetest.ui.theme.AppTheme
import com.umain.codetest.utils.UiState
import org.junit.Rule
import org.junit.Test

class RestaurantScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    private fun initView(fakeViewData: RestaurantViewModel.ViewData) {
        composeTestRule.setContent {
            AppTheme {
                RestaurantScreen(
                    viewData = fakeViewData,
                    onEvent = {},
                    onRestaurantSelect = {}
                )
            }
        }
    }

    @Test
    fun showLoaderWhenFetchingRestaurants() {
        initView(RestaurantViewModel.ViewData(state = UiState.LOADING))
        composeTestRule.onNodeWithTag("viewLoader").assertIsDisplayed()
        composeTestRule.onNodeWithTag("successView").assertDoesNotExist()
        composeTestRule.onNodeWithTag("errorView").assertDoesNotExist()
    }

    @Test
    fun showSuccessViewAfterFetchingRestaurants() {
        initView(RestaurantViewModel.ViewData(state = UiState.LOADED))
        composeTestRule.onNodeWithTag("viewLoader").assertDoesNotExist()
        composeTestRule.onNodeWithTag("successView").assertIsDisplayed()
        composeTestRule.onNodeWithTag("errorView").assertDoesNotExist()
    }

    @Test
    fun showErrorViewAfterFetchingRestaurantsFailed() {
        initView(RestaurantViewModel.ViewData(state = UiState.ERROR))
        composeTestRule.onNodeWithTag("viewLoader").assertDoesNotExist()
        composeTestRule.onNodeWithTag("successView").assertDoesNotExist()
        composeTestRule.onNodeWithTag("errorView").assertIsDisplayed()
        composeTestRule.onNodeWithTag("errorView")
            .assertTextEquals(context.getString(R.string.something_went_wrong))
    }

    @Test
    fun showEmptyRestaurantViewWhenThereIsNoRestaurants() {
        initView(RestaurantViewModel.ViewData(state = UiState.LOADED))
        composeTestRule.onNodeWithTag("noRestaurant").assertIsDisplayed()
        composeTestRule.onNodeWithTag("noRestaurant")
            .assertTextEquals(context.getString(R.string.no_restaurants_found))
    }
}