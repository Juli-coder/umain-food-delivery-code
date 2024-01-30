package com.food.delivery.ui.screens.restaurant

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.food.delivery.utils.UiState
import com.food.domain.ResponseState
import com.food.domain.entities.FilterInfo
import com.food.domain.entities.Restaurant
import com.food.domain.usacase.GetRestaurantsWithFiltersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class RestaurantViewModel @Inject constructor(
    private val getRestaurantsWithFiltersUseCase: GetRestaurantsWithFiltersUseCase,
) : ViewModel() {

    var allRestaurants = listOf<Restaurant>()

    private val _viewData = MutableStateFlow(ViewData())
    val viewData: StateFlow<ViewData> get() = _viewData.asStateFlow()

    init {
        getRestaurant()
    }

    fun onEvent(event: Event) {
        when (event) {
            is Event.FilterClicked -> {
                updateFilterSelection(event.clickedFilterId)
                updateRestaurantsByFilters()
            }
        }
    }

    private fun updateFilterSelection(clickedFilterId: String) {
        val tempFilters = viewData.value.filters
//I will matched clicked id with filterlist then assign isselected true else false.
        tempFilters.firstOrNull { it.id == clickedFilterId }?.apply {
            isSelected = isSelected.not()
        }

        _viewData.value = viewData.value.copy(filters = tempFilters)
    }

    private fun updateRestaurantsByFilters() {
        val selectedFilters = viewData.value.filters.filter { it.isSelected }.map { it.id }

        val filteredRestaurants = if (selectedFilters.isEmpty()) {
            allRestaurants
        } else {
            // if at least one filter id contains in restaurant's filter ids
            // then consider it in selected filtered restaurant list
            allRestaurants.filter { restaurantItem ->
                selectedFilters.any { restaurantItem.filterIds.contains(it) }
            }
        }

        _viewData.value = viewData.value.copy(
            restaurants = filteredRestaurants.toMutableStateList()
        )
    }

    private fun getRestaurant() {
        viewModelScope.launch {
            // Coroutine that will be canceled when the ViewModel is cleared.
            getRestaurantsWithFiltersUseCase().collect { response ->
                when (response) {
                    is ResponseState.Success -> {
                        allRestaurants = response.data.restaurants
                        _viewData.value = viewData.value.copy(
                            state = UiState.LOADED,
                            restaurants = response.data.restaurants.toMutableStateList(),
                            filters = response.data.filter.toMutableStateList()
                        )
                    }

                    is ResponseState.Loading -> {
                        _viewData.value = viewData.value.copy(
                            state = UiState.LOADING
                        )
                    }

                    is ResponseState.Error -> {
                        _viewData.value = viewData.value.copy(
                            state = UiState.ERROR,
                            errorMessage = response.throwable.message.orEmpty()
                        )
                    }
                }
            }
        }
    }

    data class ViewData(
        val state: UiState = UiState.LOADING,
        val restaurants: SnapshotStateList<Restaurant> = mutableStateListOf(),
        val filters: SnapshotStateList<FilterInfo> = mutableStateListOf(),
        val errorMessage: String = ""
    )

    sealed interface Event {
        data class FilterClicked(val clickedFilterId: String) : Event
    }
}